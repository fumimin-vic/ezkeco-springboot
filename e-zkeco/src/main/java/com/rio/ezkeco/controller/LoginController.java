package com.rio.ezkeco.controller;/**
 * @author vic fu
 **/

import com.rio.ezkeco.dto.ResultMsg;
import org.springframework.web.bind.annotation.*;

/**
 *@author vic fu
 */
@RestController
@RequestMapping("/api")
public class LoginController {


    @PostMapping(value = "/login")
    public ResultMsg login(@RequestParam String username, @RequestParam String password) {
//        System.out.println(username);
        boolean success = true;
        String message = "";

        if(!"admin".equals(username)){
            success = false;
            message ="用戶名錯誤!";
        }
        if(!"1".equals(password)){
            success = false;
            message ="密碼錯誤!";
        }

        return new ResultMsg(100,success,message,username,0);
    }
    @GetMapping(value = "/api/logout")
    public ResultMsg logout() {

        return null;

    }
}
