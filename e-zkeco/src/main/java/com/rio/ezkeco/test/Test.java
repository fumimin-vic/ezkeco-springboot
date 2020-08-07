package com.rio.ezkeco.test;/**
 * @author vic fu
 **/

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *@author vic fu
 */
public class Test {
    public static void main(String[] args) {
        HashMap<String, String> tableName  = new LinkedHashMap<>();

        tableName.put("ap_paybill", "供应商付款单");
        tableName.put("ap_payitem", "供应商付款单行");
        tableName.put("ar_recbill", "客户应收单");
        tableName.put("ar_recitem", "客户应收单行");
        tableName.put("ar_gatherbill", "客户收款单");
        tableName.put("ar_gatheritem", "客户收款单行");
        tableName.put("ap_payablebill", "供应商应付单");
        tableName.put("ap_payableitem", "供应商应付单行");
        tableName.put("ar_estirecbill", "未确认应收单");
        tableName.put("ar_estirecitem", "未确认应收单");
        tableName.put("ap_estipayableitem", "暂估应付单行");
        tableName.put("ap_estipayablebill", "暂估应付单");


        for (String key : tableName.keySet()) {
            System.out.println("--"+tableName.get(key));
            for (int i = 31; i <= 60; i++) {
                String defCode = "def"+i;
                System.out.println("alter table "+key+"  add "+defCode+"   VARCHAR2(101);");
            }
        }
    }
}
