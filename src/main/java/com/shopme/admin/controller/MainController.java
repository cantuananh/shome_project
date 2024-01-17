package com.shopme.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopme/admin")
public class MainController {
    @RequestMapping("/home")
    public String showAdminPage() {
        return "/admin/home";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "admin/user/login";
    }
}
