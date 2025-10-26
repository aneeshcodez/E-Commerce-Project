package com.example.E_com.proj.Service;

import com.example.E_com.proj.Exception.NoSuchProductExistsException;
import com.example.E_com.proj.Exception.ProductAlreadyExistsException;
import com.example.E_com.proj.Model.Product;
import com.example.E_com.proj.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProduct(int id) {
        return repo.findById(id).orElseThrow(()-> new NoSuchProductExistsException("Not found Product with id = " + id));
        //return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        if(repo.existsByName(product.getName())){
            throw new ProductAlreadyExistsException("Product already exists: " + product.getName());

        }
        //Saving the product
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        return repo.save(product);

    }
    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        // Check product exists by id
        Product existingProduct = repo.findById(id)
                .orElseThrow(() -> new NoSuchProductExistsException("No product found with id " + id));

        // Update fields
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setPrice(product.getPrice());

        // Update image
        existingProduct.setImageName(imageFile.getOriginalFilename());
        existingProduct.setImageType(imageFile.getContentType());
        existingProduct.setImageData(imageFile.getBytes());

        return repo.save(existingProduct);
    }



    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
