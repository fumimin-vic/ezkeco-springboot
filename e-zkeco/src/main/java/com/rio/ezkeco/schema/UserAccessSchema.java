package com.rio.ezkeco.schema;/**
 * @author vic fu
 **/

import com.rio.ezkeco.dto.PageableDto;

/**
 *@author vic fu
 */
public class UserAccessSchema extends PageableDto {


    private int userid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

}
