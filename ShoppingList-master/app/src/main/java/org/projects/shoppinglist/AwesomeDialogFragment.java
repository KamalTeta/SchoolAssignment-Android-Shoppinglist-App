package org.projects.shoppinglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Kamal on 31/05/16.
 */
public class AwesomeDialogFragment extends DialogFragment{

    public AwesomeDialogFragment() {


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alert = new AlertDialog.Builder(
                getActivity()
        );
        alert.setTitle("Confirm destruction");
        alert.setMessage("Do you really want to destroy the list?");
        alert.setPositiveButton("Yah", pListener);
        alert.setNegativeButton("Nah",nListener);
        return alert.create();
    }

    //Listener for positive response
    DialogInterface.OnClickListener pListener = new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface arg0, int arg2){
            positiveClick();
        }
    };

    //Listener for negative response
    DialogInterface.OnClickListener nListener = new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface arg0, int arg2){
            negativeClick();
        }
    };

    protected void positiveClick(){};
    protected void negativeClick(){};

}
