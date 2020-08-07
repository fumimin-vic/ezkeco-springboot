package com.rio.ezkeco.controller;
import com.rio.ezkeco.dto.ResultMsg;
import com.rio.ezkeco.schema.UserAccessSchema;
import com.rio.ezkeco.service.UserAccessService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *@author vic fu
 */
@RestController
@RequestMapping("/api/userAccess")
public class UserAccessController extends ExportExcelController{

    @Autowired
    UserAccessService service;

    /**
     * 获取查询条件和表体列表
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/schema")
    public ResultMsg getSchema(HttpServletResponse response) throws IOException {

//        System.out.println("aaaa");

        Map returnMap = service.getSchema();

        return new ResultMsg(0,true,"message",returnMap,0);
    }

    /**
     * 查询
     * @param response
     * @param schemaParam
     * @return
     * @throws IOException
     */
    @RequestMapping("/select")
    public ResultMsg selectUserAccess(HttpServletResponse response, @RequestBody UserAccessSchema schemaParam) throws IOException {
//        System.out.println(schemaParam.getId());

        List list =  service.getUserAccessList(schemaParam);

        return new ResultMsg(0,true,"message",list ,list.size());
    }
    /**
     * 导出Excel
     * @param response
     * @param schemaParam
     * @return
     * @throws IOException
     */
    @RequestMapping("/export")
    public ResultMsg exportExcel(HttpServletResponse response, HttpServletRequest request, @RequestBody UserAccessSchema schemaParam)throws IOException {

//        System.out.println(request.getHeader("Access-Token"));

        Map retrunMap = service.exportExcel(schemaParam);

        HSSFWorkbook workbook = (HSSFWorkbook) retrunMap.get("objects");

        int total = (int) retrunMap.get("total");

        exportExcel(response, workbook, total,"UserAccess_"+retrunMap.get("filename"));

        return new ResultMsg(0,true,"message","username1",total);
    }

}
