package com.example.a340ct.adminapplication;

/**
 * Created by user on 14.12.2016 г..
 */

//Staff data class. Defines main variable for staff,
//        so they can be saved to the Database

public class Staff {
    String name;
    String id;
    public Staff(){

    }

    public Staff(String name, String id){
        this.name = name;
        this.id = id;
    }

    //Getter and setter methods for staff


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
}
