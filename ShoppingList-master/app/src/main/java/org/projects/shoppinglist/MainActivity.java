package org.projects.shoppinglist;

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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayAdapter<Product> adapter;
    ListView listView;
    ArrayList<Product> bag = new ArrayList<Product>();

    public ArrayAdapter getMyAdapter()
    {
        return adapter;
    }

    // ebnlebiefl

    //Product lastDeletedProduct;
    //int lastDeletedPosition;
    //public void saveCopy()
    //{
    //    lastDeletedPosition = listView.getCheckedItemPosition();
    //    lastDeletedProduct = bag.get(lastDeletedPosition);
    //}

    //SAVING STUFF
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText addItem = (EditText) findViewById(R.id.addItem);
        final EditText addAmount = (EditText) findViewById(R.id.addAmount);

        //getting our listiew - you can check the ID in the xml to see that it
        //is indeed specified as "list"
        listView = (ListView) findViewById(R.id.list);
        //here we create a new adapter linking the bag and the
        //listview
        adapter =  new ArrayAdapter<Product>(this,
                android.R.layout.simple_list_item_checked,bag );

        //setting the adapter on the listview
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        //THE ADD BUTTON

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = (Spinner) findViewById(R.id.unit);
                String selectedSpinner = spinner.getSelectedItem().toString();
                String newItem = addItem.getText().toString();
                String amount = addAmount.getText().toString();
                int quantity = Integer.parseInt(amount);


                bag.add(new Product(newItem,quantity,selectedSpinner));
                //The next line is needed in order to say to the ListView
                //that the data has changed - we have added stuff now!
                getMyAdapter().notifyDataSetChanged();
            }
        });

        //THE REMOVE BUTTON

        Button removeButton = (Button) findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int removed = 0;
                //int position = listView.getCheckedItemPosition(); for singles
                SparseBooleanArray multiplePositions = listView.getCheckedItemPositions();
                int largeNumber = listView.getCheckedItemPositions().size();

            // MAYBE SOMETHIGN HERE??
                /*public void saveCopy(){

                }*/
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

                final View parent = listView;
                Snackbar snackbar = Snackbar
                        .make(parent, "Item Deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String cool = new String("hello");
                                String cool2 = new String("world");
                                bag.add(new Product(cool,7,cool2));
                                //bag.add(new Product(cool2,7,cool));
                                getMyAdapter().notifyDataSetChanged();
                                Snackbar snackbar = Snackbar.make(parent, "Item restored!", Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        });

                snackbar.show();

                getMyAdapter().notifyDataSetChanged();
            }
        });


    }












    /*********************************************************************
     ** This is the dumb burger icon thing menu                         **
     *********************************************************************/

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
