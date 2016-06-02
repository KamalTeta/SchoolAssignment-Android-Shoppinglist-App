package org.projects.shoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //This is for the clear all button
    AwesomeDialogFragment dialog = new AwesomeDialogFragment(){
        @Override
        protected void positiveClick(){
            userItemsRef.setValue(null);
            getMyAdapter().notifyDataSetChanged();
        }

        @Override
        protected void negativeClick(){
            Toast toast = Toast.makeText(getApplicationContext(), "You spared the lists life", Toast.LENGTH_LONG);
            toast.show();
        }
    };

    //This is for connecting to the database
    Firebase userItemsRef;
    FirebaseListAdapter<Product> fireAdapter;
    Query queryRef;
    ListView listView;
    public FirebaseListAdapter<Product> getMyAdapter() { return fireAdapter; }
    public Product getItem(int index){
        return getMyAdapter().getItem(index);
    }

    //This is declaring elements to save a copy of the selected item
    Product lastDeletedProduct;
    int lastDeletedPosition;
    public void saveCopy(){

        lastDeletedPosition = listView.getCheckedItemPosition();

        if(lastDeletedPosition != ListView.INVALID_POSITION ){
            lastDeletedProduct = getItem(lastDeletedPosition);
        } else {
            Toast.makeText(
                    this,
                    "Please select an item to delete!", Toast.LENGTH_SHORT).show();
        }

    }

    ArrayAdapter<Product> adapter;
    ArrayList<Product> bag = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int position = -1;
        if(savedInstanceState != null){
            position = savedInstanceState.getInt("position");
        }

        //These are the input fields
        final EditText addItem = (EditText) findViewById(R.id.addItem);
        final EditText addAmount = (EditText) findViewById(R.id.addAmount);
        final Spinner spinner = (Spinner) findViewById(R.id.unit);

        listView = (ListView) findViewById(R.id.list);

        //We don't use the bag anymore, instead we refer to Firebase
        userItemsRef = new Firebase("https://shoppingwithstekam.firebaseio.com/products");
        fireAdapter = new FirebaseListAdapter<Product>(this, Product.class, android.R.layout.simple_list_item_checked, userItemsRef) {
            @Override
            protected void populateView(View view, Product product, int i) {
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setText(product.toString());
            }
        };

        adapter =  new ArrayAdapter<Product>(this,
                android.R.layout.simple_list_item_checked,bag );

        //Here we set the adapter on the ListView
        listView.setAdapter(fireAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        if(position != -1){
            listView.setSelection(position);
        }

        getPreferences();
        //SharedPreferences pref = getSharedPreferences("my_prefs", MODE_PRIVATE);

        //This is the add button
        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedSpinner = spinner.getSelectedItem().toString();
                String newItem = addItem.getText().toString();
                //String amount = addAmount.getText().toString();
                int quantity = Integer.valueOf(addAmount.getText().toString());

                //WE COMMENT THE BAG OUT TO IMPLEMENT FIREBASE
                //bag.add(new Product(newItem, quantity, selectedSpinner));
                Product newProduct = new Product(newItem, quantity, selectedSpinner);
                userItemsRef.push().setValue(newProduct);
                getMyAdapter().notifyDataSetChanged();
            }
        });
    }


    //Save the info before it gets destroyed when the screen gets rotated
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("position", listView.getCheckedItemPosition());
    }


    //This is the remove button
    public void removeOneItem(View removeVision){
        saveCopy();
        int index = listView.getCheckedItemPosition();

        if (index != -1){
            getMyAdapter().getRef(index).setValue(null);
        }

        //Here we implement the snackbar
        Snackbar snackbar = Snackbar
                .make(listView, "Item Deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userItemsRef.push().setValue(lastDeletedProduct);
                        getMyAdapter().notifyDataSetChanged();
                        Snackbar snackbar = Snackbar.make(listView, "Item restored!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                });

        snackbar.show();
    }

    public void setPreferences(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, 1);
    }

    //Here we update the data after exiting the preferences
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //This is constructs the welcome message from the name we define in preferences
    public void getPreferences(){
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        String username = prefs.getString("username", "");
        Toast.makeText(
                this,
                "Welcome back, " + username + "!", Toast.LENGTH_SHORT).show();
    }


    // THIS WAS THE OLD REMOVE BUTTON WHERE WE REMOVED MULTIPLE ITEMS FROM THE BAG
    //Button removeButton = (Button) findViewById(R.id.removeButton);
    /*removeButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int removed = 0;
            //int position = listView.getCheckedItemPosition(); for singles
            SparseBooleanArray multiplePositions = listView.getCheckedItemPositions();
            int largeNumber = listView.getCheckedItemPositions().size();

            for (int i = 0; i < largeNumber; i++) {
                boolean value = multiplePositions.valueAt(i);
                int key = multiplePositions.keyAt(i);

                int removeIndex = key - removed;
                if (value && bag.size() > 0) {
                    bag.remove(removeIndex);
                    //bag.remove(lastDeletedPosition);
                    removed++;
                }
            }
            //bag.remove(position);
            //getMyAdapter().notifyDataSetChanged();
        }
    });*/


    //This is where we export the list to a string
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public String convertListToString(){
        String result = "";
        for (int i = 0; i < fireAdapter.getCount(); i++){
            Product product = fireAdapter.getItem(i);
            result += product.getQuantity() + " " + product.getUnit() + " " + product.getName() + "\n";
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId()){
            ////On click clear all
            case R.id.clear_all:
                dialog.show(getFragmentManager(), "AwesomeDialogFragment");
                return true;

            //On click send message
            case R.id.share_list:
                String intoTheBagOfHolding = convertListToString();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, intoTheBagOfHolding);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;

            //On click set name
            case R.id.action_settings:
                setPreferences();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
