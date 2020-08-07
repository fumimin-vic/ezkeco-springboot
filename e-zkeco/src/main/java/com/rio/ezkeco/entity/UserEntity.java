package com.rio.ezkeco.entity;

import com.rio.ezkeco.dto.Dto;

import java.io.Serializable;

/**
 *@author vic fu
 */

public class UserEntity implements Dto {

    private String name;
    private String badgenumber;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBadgenumber() {
        return badgenumber;
    }

    public void setBadgenumber(String badgenumber) {
        this.badgenumber = badgenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
