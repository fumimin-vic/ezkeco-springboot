package com.rio.ezkeco.dto;/**
 * @author vic fu
 **/

/**
 *@author vic fu
 */
public class ResultMsg {
    private int code;
    private boolean success;
    private String message;
    private Object data;



    public ResultMsg(int code, boolean success, String message, Object data, int total) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
        this.total = total;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
