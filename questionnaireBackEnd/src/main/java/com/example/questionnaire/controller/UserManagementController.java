package com.example.questionnaire.controller;

import com.example.questionnaire.service.LoginService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserManagementController {
    final
    LoginService loginService;

    public UserManagementController(LoginService loginService) {
        this.loginService = loginService;
    }


//
//    @PostMapping("/login")
//    public String login(@Param("username")String username,@Param("password")String password){
//        return loginService.login(username,password);
//    }

    @PostMapping("/api/register")
    public String register(@Param("username") String username, @Param("password") String password) {
        return loginService.reg(username, password);
    }
}
