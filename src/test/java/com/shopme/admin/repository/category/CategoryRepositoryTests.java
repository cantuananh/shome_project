package com.shopme.admin.repository.category;

import static org.assertj.core.api.Assertions.assertThat;

import com.shopme.admin.model.Category;
import com.shopme.admin.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateRootCategory() {
        Category category = new Category("Electronics");
        Category saveCategory = categoryRepository.save(category);

        assertThat(saveCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategory() {
        Category parent = new Category(4);
        Category computerComponent = new Category("Gaming Laptops", parent);
        Category saveCategory = categoryRepository.save(computerComponent);

        assertThat(saveCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetCategory() {
        Category category = categoryRepository.findById(1).get();
        System.out.println(category.getName());
        Set<Category> children = category.getChildren();

        for (Category subCategory : children) {
            System.out.println(subCategory.getName());
        }

        assertThat(children.size()).isGreaterThan(0);
    }

    @Test
    public void testPrintHierarchicalCategories() {
        Iterable<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            if (category.getParent() == null) {
                System.out.println(category.getName());
                printChildren(category, 1);
            }
        }
    }

    public void printChildren(Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            for (int i = 0; i < subLevel; i++) {
                System.out.print("--");
            }
            System.out.println(subCategory.getName());
            printChildren(subCategory, newSubLevel);
        }
    }

    @Test
    public void testListRootCategories() {
        List<Category> rootCategories = categoryRepository.findRootCategories(Sort.by("name").ascending());
        rootCategories.forEach(category-> System.out.println(category.getName()));
    }

    @Test
    public void testFindByName(){
        String name = "Computers";

        Category category = categoryRepository.findByName(name);

        assertThat(category.getId()).isGreaterThan(0);
        assertThat(category).isNotNull();
        assertThat(category.getName()).isEqualTo(name);
    }

    @Test
    public void testFindByAlias() {
        String alias = "Electronics";

        Category category = categoryRepository.findByAlias(alias);
        assertThat(category.getId()).isGreaterThan(0);
        assertThat(category).isNotNull();
        assertThat(category.getName()).isEqualTo(alias);
    }
}
