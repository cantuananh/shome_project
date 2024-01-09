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
        List<Category> rootCategories = categoryRepository.findRootCategories();

        return listHierarchicalCategories(rootCategories);
    }

    public List<Category> listHierarchicalCategories(List<Category> rootCategories) {
        List<Category> hierarchicalCategories = new ArrayList<>();

        for (Category rootCategory: rootCategories) {
            hierarchicalCategories.add(Category.copyFull(rootCategory));

            Set<Category> children = rootCategory.getChildren();
            for (Category subCategory: children) {
                String name = "--" + subCategory.getName();
                hierarchicalCategories.add(Category.copyFull(subCategory, name));
                listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1);
            }
        }

        return hierarchicalCategories;
    }

    public void listSubHierarchicalCategories(List<Category> hierarchicalCategories, Category parent, int subLevel) {
        Set<Category> children = parent.getChildren();
        int newSubLevel = subLevel + 1;

        for (Category subCategory: children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }

            name += subCategory.getName();

            hierarchicalCategories.add(Category.copyFull(subCategory, name));

            listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel);
        }
    }

    public List<Category> listCategoriesUsedInForm() {
        List<Category> categoriesUseInForm = new ArrayList<>();
        Iterable<Category> categoriesInDB = categoryRepository.findAll();

        for (Category category : categoriesInDB) {
            if (category.getParent() == null) {
                categoriesUseInForm.add(Category.copyIdAndName(category.getId(), category.getName()));
                listSubCategoriesUsedInForm(categoriesUseInForm, category, 1);
            }
        }

        return categoriesUseInForm;
    }

    public void listSubCategoriesUsedInForm(List<Category> categoriesUseInForm, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < subLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();

            categoriesUseInForm.add(Category.copyIdAndName(subCategory.getId(), name));
            listSubCategoriesUsedInForm(categoriesUseInForm, subCategory, newSubLevel);
        }
    }


    public Category save(Category category) {
        return categoryRepository.save(category);
    }

}
