package com.shopme.admin.controller.category.exportCSV;

import com.shopme.admin.export.AbstractExporter;
import com.shopme.admin.model.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;

public class CategoryCSVExporter extends AbstractExporter {
    public void export(List<Category> listCategories, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "text/csv", ".csv", "categories_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"Category ID", "Category Name"};
        String[] fieldMapping = {"id", "name"};

        csvBeanWriter.writeHeader(csvHeader);

        if (!listCategories.isEmpty()) {
            for (Category category : listCategories) {
                category.setName(category.getName().replace("--", " "));
                csvBeanWriter.write(category, fieldMapping);
            }
        }

        csvBeanWriter.close();
    }
}

