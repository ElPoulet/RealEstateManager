package com.openclassrooms.realestatemanager.fragment.list;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Agent {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nameAgent;

    public Agent(String nameAgent){
        this.nameAgent = nameAgent;
    }

    public int getId() {
        return id;
    }

    public String getNameAgent() {
        return nameAgent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameAgent(String nameAgent) {
        this.nameAgent = nameAgent;
    }
}
