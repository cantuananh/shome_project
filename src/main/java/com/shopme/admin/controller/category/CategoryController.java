package com.shopme.admin.controller.category;

import com.shopme.admin.controller.FileUploadUtil;
import com.shopme.admin.model.Category;
import com.shopme.admin.service.CategoryService;
import com.shopme.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

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

    @RequestMapping("/new")
    public String newCategory(Model model) {
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();
        model.addAttribute("category", new Category());
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("pageTitle", "Create new Category");
        return "admin/category/category_form";
    }

    @PostMapping("/save")
    public String saveCategory(RedirectAttributes redirectAttributes,
                               Category category,
                               @RequestParam("fileImage")MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        category.setImage(fileName);

        Category saveCategory = categoryService.save(category);
        String uploadDir = "category-images/" + saveCategory.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        redirectAttributes.addFlashAttribute("message", "The category has been saved successfully");


        return "redirect:/shopme/admin/categories";
    }
}
