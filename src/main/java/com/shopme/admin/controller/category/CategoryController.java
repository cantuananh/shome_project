package com.shopme.admin.controller.category;

import com.shopme.admin.service.CategoryService;
import com.shopme.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopme/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("")
    public String getAllCategories(Model model) {
        model.addAttribute("listCategories", categoryService.listAll());

        return "/admin/category/categories";
    }
}
