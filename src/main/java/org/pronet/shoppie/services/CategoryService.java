package org.pronet.shoppie.services;

import org.pronet.shoppie.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    Category add(Category category, MultipartFile file) throws IOException;
    Category getById(Long id);
    List<Category> getList();
    Page<Category> getList(Integer pageNumber, Integer pageSize);
    List<Category> getActiveCategoryList();
    List<Category> getActiveTopCategoryList();
    Category edit(Category category, MultipartFile file) throws IOException;
    Boolean remove(Long id);
    Boolean existsCategoryByName(String name);
}
