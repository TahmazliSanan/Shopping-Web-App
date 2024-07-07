package org.pronet.shoppie.services;

import org.pronet.shoppie.entities.Category;

import java.util.List;

public interface CategoryService {
    Category add(Category category);
    Category getById(Long id);
    List<Category> getList();
    Category edit(Category category);
    Boolean remove(Long id);
    Boolean existsCategoryByName(String name);
}
