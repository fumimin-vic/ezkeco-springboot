package com.rio.ezkeco.utils;
/**
 *@author vic fu
 */
public class StringUtil {

    public static boolean isEmpty(Object obj){
        String str = getStr(obj);
        if (str != null && str.length() > 0)
            return false;
        return true;
    }

    public static String getStr(Object obj) {
        String ret = "";
        try {
            ret = obj != null ? obj.toString() : "";
        } catch (Exception e) {
            ret = "";
        }
        return ret;
    }
}
