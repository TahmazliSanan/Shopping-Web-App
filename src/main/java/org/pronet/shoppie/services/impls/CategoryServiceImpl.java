package org.pronet.shoppie.services.impls;

import org.pronet.shoppie.entities.Category;
import org.pronet.shoppie.repositories.CategoryRepository;
import org.pronet.shoppie.services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category add(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public List<Category> getList() {
        return categoryRepository.findAll();
    }

    @Override
    public Category edit(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Boolean remove(Long id) {
        Category foundedCategory = categoryRepository
                .findById(id)
                .orElse(null);
        if (!ObjectUtils.isEmpty(foundedCategory)) {
            categoryRepository.delete(foundedCategory);
            return true;
        }
        return false;
    }

    @Override
    public Boolean existsCategoryByName(String name) {
        return categoryRepository.existsByName(name);
    }
}
