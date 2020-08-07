package com.rio.ezkeco.entity;

import com.rio.ezkeco.dto.Dto;

/**
 *@author vic fu
 */
public class DoorEntity implements Dto {

    private String door_name;

    private int id;



    public String getDoor_name() {
        return door_name;
    }

    public void setDoor_name(String door_name) {
        this.door_name = door_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
