package com.rio.ezkeco.service;

import com.rio.ezkeco.constant.SchemaConstant;
import com.rio.ezkeco.entity.DoorEntity;
import com.rio.ezkeco.entity.UserEntity;
import com.rio.ezkeco.schema.DoorAccessSchema;
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
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;

/**
 *@author vic fu
 */
@Service
public class DoorAccessService {



    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Map getSchema(){

        List doorlist = getAllDoorSchema();

        Map returnMap =  new HashMap();

        Map map = new HashMap();
        map.put(SchemaConstant.SCHEMA_KEY,"id");
        map.put(SchemaConstant.SCHEMA_OPTIONS,doorlist);
        List schemaList = new ArrayList();

        schemaList.add(map);
        /*List dataSchemaList = new ArrayList();
        Map dataMap = new HashMap();
        dataMap.put("key","userid");
        dataMap.put("title","test");
        dataSchemaList.add(dataMap);*/
        returnMap.put(SchemaConstant.QUERYSCHEMA,schemaList);
//        returnMap.put("dataSchema",dataSchemaList);

        return returnMap;
    }


    /**
     * 所有门
     * @return
     */
    public List getAllDoorSchema(){
        return jdbcTemplate.queryForList("select convert(varchar(20),id) 'key' ,door_name value from acc_door ");
    }

    /**
     * 用户有权限的们
     *
     * @param schemaParam
     * @return
     */
    public List getDoorAccessList(DoorAccessSchema schemaParam) {

        StringBuffer strBuffer = new StringBuffer();

        strBuffer.append(" select row_number()over(order by userinfo.badgenumber )as item,userinfo.*,tab_dept.deptname deptname,tab_level.groupname  from door_level_name2 tab_level INNER join userinfo ");

        strBuffer.append(" on tab_level.userid_id = userinfo.userid  ");

        strBuffer.append("   LEFT JOIN departments tab_dept ");

        strBuffer.append("    ON userinfo.defaultdeptid = tab_dept.deptid ");

        strBuffer.append(" where tab_level.door_id  = ?");

        strBuffer.append(" order by badgenumber asc ");


        return jdbcTemplate.queryForList(strBuffer.toString(), schemaParam.getId());
    }

    /**
     * 根据主键查询
     *
     * @param door_id
     * @return
     */
    public DoorEntity getDoorByUserId(int door_id) {

        String sql = "select * from acc_door where id = "+door_id;

        List<DoorEntity> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<DoorEntity>(DoorEntity.class));

        return list.get(0);

    }

    /**
     * 导出Excel
     *
     * @param schemaParam
     * @return
     */
    public Map exportExcel(DoorAccessSchema schemaParam) throws Exception {


        List list = getDoorAccessList(schemaParam);

        DoorEntity doorEntity = getDoorByUserId(schemaParam.getId());

        /***生成Excel***/
        /***1、生成标题***/
        HSSFWorkbook workbook = new HSSFWorkbook();
        //1、先创建标题
        String title = MessageFormat.format("Browse {0} opening personnel", doorEntity.getDoor_name());
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
       /* ++rownum;
        String name = "Name:" + userEntity.getName();
        row = sheet.createRow(rownum);
        cell = row.createCell(0);
        cell.setCellValue(name);
        cell.setCellStyle(ExcelUtil.getStrCellStyle(workbook, false, 0, false, 12, false, false, false, false));*/
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
        colMap.put("badgenumber", "Personnel No.");
        colMap.put("name", "User Name");
        colMap.put("deptname", "Deartment Name");
        colMap.put("groupname", "Group Level Name");
        colMap.put("card", "Card Number(Internal)");
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
        returnMap.put("filename",doorEntity.getDoor_name().replace(' ','_'));

        return returnMap;

    }



}
