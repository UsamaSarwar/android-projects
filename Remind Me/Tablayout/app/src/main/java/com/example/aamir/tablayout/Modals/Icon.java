package com.example.aamir.tablayout.Modals;

public class Icon {

    private int id;
    private String name;

    public Icon(int id, String name){
        setId(id);
        setName(name);

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}