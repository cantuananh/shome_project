package com.shopme.admin.service;

import com.shopme.admin.model.Category;
import com.shopme.admin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> listAll() {
        return (List<Category>) categoryRepository.findAll();
    }
    public List<Category> listCategoriesUsedInForm() {
        List<Category> categoriesUseInForm = new ArrayList<>();
        Iterable<Category> categoriesInDB = categoryRepository.findAll();

        for (Category category : categoriesInDB) {
            if (category.getParent() == null) {
                categoriesUseInForm.add(Category.copyIdAndName(category.getId(), category.getName()));
                listChildren(categoriesUseInForm, category, 1);
            }
        }

        return categoriesUseInForm;
    }

    public void listChildren(List<Category> categoriesUseInForm, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < subLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();

            categoriesUseInForm.add(Category.copyIdAndName(subCategory.getId(), name));
            listChildren(categoriesUseInForm, subCategory, newSubLevel);
        }
    }


    public Category save(Category category) {
        return categoryRepository.save(category);
    }

}
