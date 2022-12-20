package com.example.demo.controller;

import com.example.demo.cnfiguration.dto.LoginDto;
import com.example.demo.cnfiguration.dto.RegisterDto;
import com.example.demo.service.MyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final MyUserService myUserService;

    @PostMapping("/register/user")
    public HttpEntity<?>register(@RequestBody RegisterDto registerDto) throws FileNotFoundException {

       return myUserService.register(registerDto);

    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
        return myUserService.login(loginDto);
    }

}
