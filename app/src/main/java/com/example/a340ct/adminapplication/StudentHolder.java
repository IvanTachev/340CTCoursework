package com.example.a340ct.adminapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by user on 14.12.2016 г..
 */

//Class populating the view with information taken from DB for student.
//Does the connection between data classes, database and the UI

public class StudentHolder extends RecyclerView.ViewHolder {
    private final TextView nameField;
    private final TextView iDField;
    private final TextView moduleField;
    public View itemVw;

    public StudentHolder(View itemView) {
        super(itemView);
        nameField = (TextView) itemView.findViewById(R.id.studentNameShow);
        iDField = (TextView) itemView.findViewById(R.id.studentIDShow);
        moduleField = (TextView) itemView.findViewById(R.id.studentModuleShow);
        this.itemVw = itemView;
    }

    public void setName(String name){
        nameField.setText(name);
    }
    public void setID(String text){iDField.setText(text);}
    public void setModule(String module){moduleField.setText(module);}


}
