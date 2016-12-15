package com.example.a340ct.adminapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by user on 15.12.2016 г..
 */

//Class populating the view with information taken from DB for staff.
    //Does the connection between data classes, database and the UI

public class StaffHolder extends RecyclerView.ViewHolder {
    private final TextView nameField;
    private final TextView iDField;
    public View itemVw;

    public StaffHolder(View itemView) {
        super(itemView);
        nameField = (TextView) itemView.findViewById(R.id.staffNameShow);
        iDField = (TextView) itemView.findViewById(R.id.staffIDShow);
        this.itemVw = itemView;
    }

    public void setName(String name){
        nameField.setText(name);
    }
    public void setID(String text){
        iDField.setText(text);
    }


}
