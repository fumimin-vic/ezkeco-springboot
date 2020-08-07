package com.rio.ezkeco.service;

import com.rio.ezkeco.constant.SchemaConstant;
import com.rio.ezkeco.entity.RoleEntity;
import com.rio.ezkeco.entity.UserEntity;
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
public class RoleAccessService {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Map getSchema() {

        List roleList = getAllRoleSchema();

        Map returnMap = new HashMap();

        Map map = new HashMap();
        map.put(SchemaConstant.SCHEMA_KEY, "id");
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
     * 所有用户
     *
     * @return
     */
    public List getAllRoleSchema() {
        return jdbcTemplate.queryForList("select cast(id as varchar) as 'key' ,name value from acc_level order by name asc  ");
    }

    /**
     * 用户有权限的们
     *
     * @param schemaParam
     * @return
     */
    public List getUserAccessListByRole(RoleAccessSchema schemaParam) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT Row_number() ");
        varname1.append("         OVER(ORDER BY tab.badgenumber) AS item, ");
        varname1.append("       tab.* ");
        varname1.append("FROM   (SELECT DISTINCT tab_u.*, ");
        varname1.append("                        tab_dept.deptname deptname, ");
        varname1.append("                        tab_door.door_name ,tab_level.groupid ");
        varname1.append("        FROM   door_level_name2 tab_level ");
        varname1.append("               LEFT JOIN userinfo tab_u ");
        varname1.append("                 ON tab_level.userid_id = tab_u.userid ");
        varname1.append("       LEFT JOIN departments tab_dept ");
        varname1.append("         ON tab_u.defaultdeptid = tab_dept.deptid");
        varname1.append("               LEFT JOIN v_door_level_name tab_door ");
        varname1.append("                 ON tab_u.userid = tab_door.userid_id) tab");
        varname1.append("                where tab.groupid = ? ");
        varname1.append("                order by badgenumber asc ");



        return jdbcTemplate.queryForList(varname1.toString(), schemaParam.getId());
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public RoleEntity getRoleById(String id) {

        String sql = "select * from acc_level where id = "+id+" ";

        List<RoleEntity> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<RoleEntity>(RoleEntity.class));

        return list.get(0);

    }

    /**
     * 导出Excel
     *
     * @param schemaParam
     * @return
     */
    public Map exportExcel(RoleAccessSchema schemaParam) {

        List list = getUserAccessListByRole(schemaParam);

        RoleEntity roleEntity = getRoleById(String.valueOf(schemaParam.getId()));

        /***生成Excel***/
        /***1、生成标题***/
        HSSFWorkbook workbook = new HSSFWorkbook();
        //1、先创建标题
        String title = MessageFormat.format("浏览权限组: {0} opening personnel", roleEntity.getName());
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
        String name = "Name:" + roleEntity.getName();
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
        colMap.put("deptname", "Department Name");
        colMap.put("card", "Card Number(Internal)");
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
        returnMap.put("filename",roleEntity.getName().replace(' ','_'));

        return returnMap;

    }
}
