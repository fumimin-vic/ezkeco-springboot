package com.rio.ezkeco.service;

import com.rio.ezkeco.constant.SchemaConstant;
import com.rio.ezkeco.entity.RoleEntity;
import com.rio.ezkeco.schema.RoleAccessSchema;
import com.rio.ezkeco.schema.UserAccessSchema;
import com.rio.ezkeco.utils.DateUtil;
import com.rio.ezkeco.utils.ExcelUtil;
import com.rio.ezkeco.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author vic fu
 */
@Service
public class PersonnelListService {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserAccessService userAccessService;


    public Map getSchema() {

        List roleList = userAccessService.getAllUserSchema();

        Map returnMap = new HashMap();

        Map map = new HashMap();
        map.put(SchemaConstant.SCHEMA_KEY, "userid");
        map.put(SchemaConstant.SCHEMA_OPTIONS, roleList);
        List schemaList = new ArrayList();
        schemaList.add(map);


        List dataSchemaList = new ArrayList();
        /*Map dataMap = new HashMap();
        dataMap.put("key","userid");
        dataMap.put("title","test");
        dataSchemaList.add(dataMap);*/

        returnMap.put(SchemaConstant.QUERYSCHEMA, schemaList);
//        returnMap.put("dataSchema",dataSchemaList);

        return returnMap;
    }


    /**
     * 用户有权限的们
     *
     * @param schemaParam
     * @return
     */
    public List getPersonnelListAccess(UserAccessSchema schemaParam) {

        StringBuffer strBuffer = new StringBuffer();


        strBuffer.append("SELECT tab_u.*, ");
        strBuffer.append("       Row_number() ");
        strBuffer.append("         OVER(ORDER BY tab_u.badgenumber) AS item, ");
        strBuffer.append("       tab_dept.deptname deptname, ");
        strBuffer.append("       tab_h.door_name, ");
        strBuffer.append("       tab_h.NAME groupname ");
        strBuffer.append("FROM   userinfo tab_u ");
        strBuffer.append("       LEFT JOIN v_door_level_name tab_h ");
        strBuffer.append("         ON tab_u.userid = tab_h.userid_id ");
        strBuffer.append("       LEFT JOIN departments tab_dept ");
        strBuffer.append("         ON tab_u.defaultdeptid = tab_dept.deptid");



        if(schemaParam.getUserid() != 0){
            strBuffer.append(" where tab_u.userid = ? ");
            return jdbcTemplate.queryForList(strBuffer.toString(), schemaParam.getUserid());
        }

        strBuffer.append("         order by badgenumber asc ");

        return jdbcTemplate.queryForList(strBuffer.toString());
    }

    /**
     * 导出Excel
     *
     * @param schemaParam
     * @return
     */
    public Map exportExcel(UserAccessSchema schemaParam) {

        List list = getPersonnelListAccess(schemaParam);

        /***生成Excel***/
        /***1、生成标题***/
        HSSFWorkbook workbook = new HSSFWorkbook();
        //1、先创建标题
        String title = "Personel List For Access Level Detail Report";
        HSSFSheet sheet = workbook.createSheet("All Records");
        sheet.setDefaultColumnWidth(20);

        int rownum = 0;
        HSSFRow row = sheet.createRow(rownum);
        //创建一个单元格
        HSSFCell cell = row.createCell(0);
        //将内容对象的文字内容写入到单元格中
        cell.setCellValue(title);
        cell.setCellStyle(ExcelUtil.getStrCellStyle(workbook, false, 0, true, 15, false, false, false, false));

        /**name**/
        ++rownum;
        String name = "";
        row = sheet.createRow(rownum);
        cell = row.createCell(0);
        cell.setCellValue(name);
        cell.setCellStyle(ExcelUtil.getStrCellStyle(workbook, false, 0, false, 12, false, false, false, false));
        /**time**/
        String time = DateUtil.getCreateTime();
        ++rownum;
        row = sheet.createRow(rownum);
        cell = row.createCell(0);
        cell.setCellValue(time);
        cell.setCellStyle(ExcelUtil.getStrCellStyle(workbook, false, 0, false, 12, false, false, false, false));
        ++rownum;
        row = sheet.createRow(rownum);
        HashMap<String, String> colMap = new LinkedHashMap<>();
        colMap.put("item", "Item");
        colMap.put("badgenumber", "Personnel No.");
        colMap.put("name", "English Name");
        colMap.put("gender", "Gender");
        colMap.put("card", "Card Number(Internal)");
        colMap.put("deptname", "Department Name");
        colMap.put("title", "Position");
        colMap.put("offduty", "Static");
        colMap.put("groupname", "Door Access Group Level");
        colMap.put("door_name", "Door Access Level Detail");
        ++rownum;
        row = sheet.createRow(rownum);
        int column = 0;
        for (String key : colMap.keySet()) {
            cell = row.createCell(column++);
            cell.setCellValue(colMap.get(key));
            cell.setCellStyle(ExcelUtil.getStrCellStyle(workbook, true, HSSFColor.GREY_25_PERCENT.index, true, 12, true, true, true, true));
        }
        //导出内容
        for (Object dataObj : list) {
            ++rownum;
            row = sheet.createRow(rownum);
            column = 0;
            for (String key : colMap.keySet()) {
                Map dataMap = (Map) dataObj;
                cell = row.createCell(column++);
                cell.setCellValue(StringUtil.getStr(dataMap.get(key)));
                cell.setCellStyle(ExcelUtil.getStrCellStyle(workbook, false, 0, false, 12, true, true, true, true));
            }
        }
        Map returnMap = new HashMap();

        returnMap.put("total", list.size());
        returnMap.put("objects", workbook);
        returnMap.put("filename","PersonnelList");

        return returnMap;

    }
}
