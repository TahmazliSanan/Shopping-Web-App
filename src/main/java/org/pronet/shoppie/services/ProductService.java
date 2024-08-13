package org.pronet.shoppie.services;

import org.pronet.shoppie.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product add(Product product, MultipartFile file) throws IOException;
    Product getById(Long id);
    Page<Product> getList(Integer pageNumber, Integer pageSize);
    List<Product> getActiveTopProductList();
    List<Product> getActiveProductList(String category);
    Page<Product> getActiveProductList(Integer pageNumber, Integer pageSize, String category);
    Page<Product> searchActiveProduct(Integer pageNumber, Integer pageSize, String character);
    Page<Product> searchProduct(Integer pageNumber, Integer pageSize, String character);
    Product edit(Product product, MultipartFile file) throws IOException;
    Boolean remove(Long id);
    Boolean existsProductByName(String name);
}
