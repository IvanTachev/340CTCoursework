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
public class StudentsFragment extends Fragment implements View.OnClickListener {

    private FirebaseRecyclerAdapter<Student, StudentHolder> myAdapter;
    DatabaseReference myRef;
    public String name;
    public String id;
    private Button addStudent;
    private Button deleteStudent;

    public StudentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_students, container, false);

        RecyclerView studentList = (RecyclerView) v.findViewById(R.id.listStudents);
        myRef = FirebaseDatabase.getInstance().getReference("Students");
        studentList.setHasFixedSize(true);
        studentList.setLayoutManager(new LinearLayoutManager(getContext()));

        //Initializing the adapter
        myAdapter = new FirebaseRecyclerAdapter<Student, StudentHolder>(
                Student.class,
                R.layout.student_row,
                StudentHolder.class,
                myRef) {
            @Override

            //Populating the Recycler view
            protected void populateViewHolder(StudentHolder viewHolder, final Student model, final int position) {
                viewHolder.setName(model.getName());
                viewHolder.setID(model.getId());
                viewHolder.setModule(model.getModule());
                viewHolder.itemVw.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Log.i("debug", "onItemClickGeneral: position " + position + " " + model.getName() + " " + model.getId());
                        String currentName = model.getName();
                        String currentId = model.getId();
                        String currentModule = model.getModule();
                        DatabaseReference currentReference = FirebaseDatabase.getInstance().getReference("Students/" + currentName);
                        Log.i("debug", "onItemClickGeneral: position " + position + " " + model.getName() + " " + model.getId() + " " + currentReference);

                        studentEdit(currentName, currentId, currentModule, currentReference);
                        return false;
                    }
                });
            }
        };


        addStudent = (Button)v.findViewById(R.id.addStudent);
        deleteStudent = (Button)v.findViewById(R.id.deleteStudent);
        addStudent.setOnClickListener(this);
        deleteStudent.setOnClickListener(this);
        studentList.setAdapter(myAdapter);
        return v;
    }


    //Method adding a new student to the database
    public void newStudentAdd(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_student_popup);
        final EditText newStudentName = (EditText)dialog.findViewById(R.id.newStudentName);
        final EditText newStudentId = (EditText)dialog.findViewById(R.id.newStudentId);
        final EditText newStudentModule = (EditText)dialog.findViewById(R.id.newStudentModule);
        Button submitStudent = (Button)dialog.findViewById(R.id.submitBtn);
        Button cancelBtn = (Button)dialog.findViewById(R.id.cancelBtn);
        dialog.show();

        submitStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = newStudentName.getText().toString();
                String id = newStudentId.getText().toString();
                String module = newStudentModule.getText().toString();
                Student newStudent = new Student(name, id, module);
                myRef.child(newStudent.name).setValue(newStudent);
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

    //Method assigned to longPress to edit selected student
    public void studentEdit(final String name, String id, String module, final DatabaseReference currentRef){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.student_pop_up_window);
        final EditText newStudentName = (EditText) dialog.findViewById(R.id.newStudentName2);
        final EditText newStudentID = (EditText) dialog.findViewById(R.id.newStudentId2);
        final EditText newStudentModule = (EditText) dialog.findViewById(R.id.newStudentModule2);

        Button editStudent = (Button)dialog.findViewById(R.id.editBtn);
        Button deleteStudent = (Button) dialog.findViewById(R.id.deleteBtn);
        Button cancelBtn2 = (Button)dialog.findViewById(R.id.cancelBtn2);
        dialog.show();


        //Editing the student function
        editStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = newStudentName.getText().toString();
                String newId = newStudentID.getText().toString();
                String newModule = newStudentModule.getText().toString();
                currentRef.removeValue();
                Student student = new Student(newName, newId, newModule);
                myRef.child(student.name).setValue(student);
                Toast.makeText(getContext(), "Student edit successful", Toast.LENGTH_SHORT).show();

            }
        });
        //Deleting the student function
        deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRef.removeValue();
                Toast.makeText(getContext(), "Student deleted", Toast.LENGTH_SHORT).show();
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
            case R.id.addStudent:
                newStudentAdd();
                break;
            case R.id.deleteStudent:
                break;
        }
    }
}
