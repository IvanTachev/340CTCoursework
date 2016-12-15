package com.example.a340ct.adminapplication;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class StaffFragment extends Fragment implements View.OnClickListener {

    private FirebaseRecyclerAdapter<Staff, StaffHolder> myAdapter;
    DatabaseReference myRef;
    public String name;
    public String id;
    private Button addStaff;
    private Button deleteStaff;

    public StaffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_staff, container, false);

        RecyclerView staffList = (RecyclerView) v.findViewById(R.id.listStaff);
        myRef = FirebaseDatabase.getInstance().getReference("Staff");
        staffList.setHasFixedSize(true);
        staffList.setLayoutManager(new LinearLayoutManager(getContext()));

        //Initializing the adapter
        myAdapter = new FirebaseRecyclerAdapter<Staff, StaffHolder>(
                Staff.class,
                R.layout.staff_row,
                StaffHolder.class,
                myRef) {
            @Override
            //Populating the Recycler view
            protected void populateViewHolder(StaffHolder viewHolder, final Staff model, final int position) {
                viewHolder.setName(model.getName());
                viewHolder.setID(model.getId());
                viewHolder.itemVw.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Log.i("debug", "onItemClickGeneral: position " + position + " " + model.getName() + " " + model.getId());

                        //gets the name and id for the longpressed item from the DB
                        String currentName = model.getName();
                        String currentId = model.getId();
                        DatabaseReference currentReference = FirebaseDatabase.getInstance().getReference("Staff/" + currentName);
                        Log.i("debug", "onItemClickGeneral: position " + position + " " + model.getName() + " " + model.getId() + " " + currentReference);

                        staffEdit(currentName, currentId, currentReference);
                        return false;
                    }
                });
            }
        };


        addStaff = (Button)v.findViewById(R.id.addStaff);
        deleteStaff = (Button)v.findViewById(R.id.deleteStaff);
        addStaff.setOnClickListener(this);
        deleteStaff.setOnClickListener(this);
        staffList.setAdapter(myAdapter);
        return v;
    }

    //Method adding a new staff to the database
    public void newStaffAdd(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_staff_popup);
        final EditText newStaffName = (EditText)dialog.findViewById(R.id.newStaffName);
        final EditText newStaffId = (EditText)dialog.findViewById(R.id.newStaffId);
        Button submitStaff = (Button)dialog.findViewById(R.id.submitBtn);
        Button cancelBtn = (Button)dialog.findViewById(R.id.cancelBtn);
        dialog.show();

        submitStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = newStaffName.getText().toString();
                String id = newStaffId.getText().toString();
                Staff newStaff = new Staff(name, id);
                myRef.child(newStaff.name).setValue(newStaff);
                dialog.cancel();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }

    //Method assigned to longPress to edit selected staff

    public void staffEdit(final String name, String id, final DatabaseReference currentRef){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.staff_pop_up_window);
        final EditText newStaffName = (EditText) dialog.findViewById(R.id.newStaffName2);
        final EditText newStaffID = (EditText) dialog.findViewById(R.id.newStaffId2);
        Button editStaff = (Button)dialog.findViewById(R.id.editBtn2);
        Button deleteStaff = (Button) dialog.findViewById(R.id.deleteBtn2);
        Button cancelBtn2 = (Button)dialog.findViewById(R.id.cancelBtn2);
        dialog.show();


        //function editing the info for staff
        editStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = newStaffName.getText().toString();
                String newId = newStaffID.getText().toString();
                currentRef.removeValue();
                Staff staff = new Staff(newName, newId);
                myRef.child(staff.name).setValue(staff);
                Toast.makeText(getContext(), "Staff edit successful", Toast.LENGTH_SHORT).show();

            }
        });

        //deleting staff
        deleteStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRef.removeValue();
                Toast.makeText(getContext(), "Staff deleted", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        cancelBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addStaff:
                newStaffAdd();
        }
    }
}
