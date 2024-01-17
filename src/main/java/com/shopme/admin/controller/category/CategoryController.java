package com.shopme.admin.controller.category;

import com.shopme.admin.controller.FileUploadUtil;
import com.shopme.admin.controller.category.exportCSV.CategoryCSVExporter;
import com.shopme.admin.model.Category;
import com.shopme.admin.service.category.CategoryNoFoundException;
import com.shopme.admin.service.category.CategoryPageInfo;
import com.shopme.admin.service.category.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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
    public String listFirstPage(Model model, @Param("sortDir") String sortDir) {
        return listByPage(1, model, sortDir, null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum,
                             Model model,
                             @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword) {
        if (sortDir == null || sortDir.isEmpty()) {
            sortDir = "asc";
        }

        CategoryPageInfo pageInfo = new CategoryPageInfo();
        List<Category> listCategories = categoryService.listByPage(pageInfo, pageNum, sortDir, keyword);

        String revertSortDir = sortDir.equals("asc") ? "desc" : "asc";
        System.out.println("total: " + pageInfo.getTotalPages());



        long startCount = (pageNum - 1) * CategoryService.ROOT_CATEGORIES_PER_PAGE + 1;
        long endCount = startCount + CategoryService.ROOT_CATEGORIES_PER_PAGE - 1;
        if (endCount > pageInfo.getTotalElements()) {
            endCount = pageInfo.getTotalElements();
        }

        model.addAttribute("totalPages", pageInfo.getTotalPages());
        model.addAttribute("totalItems", pageInfo.getTotalElements());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("sortField", "name");
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("keyword", keyword);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("revertSortDir", revertSortDir);

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

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable(name = "id") Integer id,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            Category category = categoryService.get(id);
            List<Category> listCategories = categoryService.listCategoriesUsedInForm();

            model.addAttribute("category", category);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("message", "Edit category (ID: " + id + ")");

            return "admin/category/category_form";

        } catch (CategoryNoFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/shopme/admin/categories";
        }


    }

    @PostMapping("/save")
    public String saveCategory(RedirectAttributes redirectAttributes,
                               Category category,
                               @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            category.setImage(fileName);

            Category saveCategory = categoryService.save(category);
            String uploadDir = "category-images/" + saveCategory.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            categoryService.save(category);
        }

        redirectAttributes.addFlashAttribute("message", "The category has been saved successfully");

        return "redirect:/shopme/admin/categories";
    }

    @GetMapping("/{id}/enabled/{status}")
    public String updateCategoryEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
                                              RedirectAttributes redirectAttributes) {

        System.out.println(id + " - " + enabled);
        categoryService.updateCategoryEnabledStatus(id, enabled);

        String status = enabled ? "enabled" : "disabled";

        String message = "The category ID " + id + " has been " + status;

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/shopme/admin/categories";
    }

    @GetMapping("/export/csv")
    public void exportCategoryCSV(HttpServletResponse response) throws IOException {
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        CategoryCSVExporter categoryCSVExporter = new CategoryCSVExporter();
        categoryCSVExporter.export(listCategories, response);
    }
}
