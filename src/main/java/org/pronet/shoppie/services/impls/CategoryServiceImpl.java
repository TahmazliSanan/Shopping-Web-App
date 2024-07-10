package org.pronet.shoppie.services.impls;

import org.pronet.shoppie.entities.Category;
import org.pronet.shoppie.repositories.CategoryRepository;
import org.pronet.shoppie.services.CategoryService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category add(Category category, MultipartFile file) throws IOException {
        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
        category.setImageName(imageName);
        Category savedCategory = categoryRepository.save(category);
        if (!ObjectUtils.isEmpty(savedCategory)) {
            if (!Objects.requireNonNull(file).isEmpty()) {
                File savedFile = new ClassPathResource("static/").getFile();
                Path path = Paths.get(
                        savedFile.getAbsolutePath() +
                                File.separator +
                                "category-images" +
                                File.separator +
                                file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            return category;
        }
        return null;
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
    public List<Category> getActiveCategoryList() {
        return categoryRepository.findByIsActiveTrue();
    }

    @Override
    public List<Category> getActiveTopCategoryList() {
        return categoryRepository.takeActiveTopCategories();
    }

    @Override
    public Category edit(Category category, MultipartFile file) throws IOException {
        Category dbCategory = getById(category.getId());
        String imageName = file.isEmpty() ? dbCategory.getImageName() : file.getOriginalFilename();
        dbCategory.setName(category.getName());
        dbCategory.setImageName(imageName);
        dbCategory.setIsActive(category.getIsActive());
        Category updatedCategory = categoryRepository.save(dbCategory);
        if (!ObjectUtils.isEmpty(updatedCategory)) {
            if (!file.isEmpty()) {
                File savedFile = new ClassPathResource("static/").getFile();
                Path path = Paths.get(
                        savedFile.getAbsolutePath() +
                                File.separator +
                                "category-images" +
                                File.separator +
                                file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            return category;
        }
        return null;
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
