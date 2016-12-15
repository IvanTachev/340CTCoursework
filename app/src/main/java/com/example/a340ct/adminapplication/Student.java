package com.example.a340ct.adminapplication;

/**
 * Created by user on 14.12.2016 г..
 */

//Student data class. Defines main variable for student,
//        so they can be saved to the Database


public class Student {
    String name;
    String id;
    String module;
    public Student(){

    }

    public Student(String name, String id, String module){
        this.name = name;
        this.id = id;
        this.module = module;
    }


    //Getter and setter methods for student
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
