package com.rio.ezkeco.controller;/**
 * @author vic fu
 **/


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *@author vic fu
 */
public class ExportExcelController  {


    /**
     *
     * @param response
     * @param workbook
     * @param total
     * @param filename
     * @throws IOException
     */
    protected  void exportExcel(HttpServletResponse response, HSSFWorkbook workbook, int total,String filename) throws IOException {


        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream;filename="+filename+".xls");

        //这后面可以设置导出Excel的名称，此例中名为student.xls
        response.setHeader("Content-disposition", "filename=file.xls;total="+(total == 0 ? "false" : "true"));

        //是否有数据
        response.setHeader("Access-Control-expose-Headers","Content-Type,token2");

      // return  ResponseEntity.ok().header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,"XXX").body(workbook);

        //刷新缓冲
        response.flushBuffer();

        //workbook将Excel写入到response的输出流中，供页面下载
//        if (total > 0) {
            workbook.write(response.getOutputStream());
//        }
    }

}
