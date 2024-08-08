package org.pronet.shoppie.services.impls;

import org.pronet.shoppie.entities.Product;
import org.pronet.shoppie.repositories.ProductRepository;
import org.pronet.shoppie.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product add(Product product, MultipartFile file) throws IOException {
        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
        product.setImageName(imageName);
        product.setDiscount(0);
        product.setDiscountPrice(product.getPrice());
        Product savedProduct = productRepository.save(product);
        if (!ObjectUtils.isEmpty(savedProduct)) {
            if (!Objects.requireNonNull(file).isEmpty()) {
                File savedFile = new ClassPathResource("static/").getFile();
                Path path = Paths.get(
                        savedFile.getAbsolutePath() +
                                File.separator +
                                "product-images" +
                                File.separator +
                                file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            return product;
        }
        return null;
    }

    @Override
    public Product getById(Long id) {
        return productRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public Page<Product> getList(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getActiveProductList(Integer pageNumber, Integer pageSize, String category) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> pageProduct;
        if (ObjectUtils.isEmpty(category)) {
            pageProduct = productRepository.findByIsActiveTrue(pageable);
        } else {
            pageProduct = productRepository.findByCategory(pageable, category);
        }
        return pageProduct;
    }

    @Override
    public Page<Product> searchActiveProduct(Integer pageNumber, Integer pageSize, String category, String character) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByIsActiveTrueAndNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(character, character, pageable);
    }

    @Override
    public List<Product> getActiveTopProductList() {
        return productRepository.takeActiveTopProducts();
    }

    @Override
    public Page<Product> searchProduct(Integer pageNumber, Integer pageSize, String character) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(character, character, pageable);
    }

    @Override
    public Product edit(Product product, MultipartFile file) throws IOException {
        Product dbProduct = getById(product.getId());
        String imageName = file.isEmpty() ? dbProduct.getImageName() : file.getOriginalFilename();
        dbProduct.setName(product.getName());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setDiscount(product.getDiscount());
        dbProduct.setCategory(product.getCategory());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setStock(product.getStock());
        dbProduct.setImageName(imageName);
        dbProduct.setIsActive(product.getIsActive());
        Double discount = product.getPrice() * (product.getDiscount() / 100.0);
        Double discountPrice = product.getPrice() - discount;
        dbProduct.setDiscountPrice(discountPrice);
        Product updatedProduct = productRepository.save(dbProduct);
        if (!ObjectUtils.isEmpty(updatedProduct)) {
            if (!file.isEmpty()) {
                    File savedFile = new ClassPathResource("static/").getFile();
                    Path path = Paths.get(
                            savedFile.getAbsolutePath() +
                                    File.separator +
                                    "product-images" +
                                    File.separator +
                                    file.getOriginalFilename());
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            return product;
        }
        return null;
    }

    @Override
    public Boolean remove(Long id) {
        Product foundedProduct = productRepository
                .findById(id)
                .orElse(null);
        if (!ObjectUtils.isEmpty(foundedProduct)) {
            productRepository.delete(foundedProduct);
            return true;
        }
        return false;
    }

    @Override
    public Boolean existsProductByName(String name) {
        return productRepository.existsByName(name);
    }
}
