package com.rio.ezkeco.schema;/**
 * @author vic fu
 **/

import com.rio.ezkeco.dto.PageableDto;

/**
 *@author vic fu
 */
public class RoleAccessSchema extends PageableDto {


    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
