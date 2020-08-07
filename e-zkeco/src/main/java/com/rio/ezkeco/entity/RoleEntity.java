package com.rio.ezkeco.entity;

import com.rio.ezkeco.dto.Dto;

/**
 *@author vic fu
 */

public class RoleEntity implements Dto {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;



}
