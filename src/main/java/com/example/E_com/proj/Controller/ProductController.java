package com.example.E_com.proj.Controller;
import com.example.E_com.proj.Dto.AuthReq;
import com.example.E_com.proj.Dto.ProductListDTO;
import com.example.E_com.proj.Entity.UserInfo;
import com.example.E_com.proj.Model.Product;
import com.example.E_com.proj.Service.JwtService;
import com.example.E_com.proj.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService service;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;


    @GetMapping("/products")
    public ResponseEntity<List<ProductListDTO>> getAllProducts() {

        List<Product> products = service.getAllProducts();

        List<ProductListDTO> dtoList = products.stream().map(product -> {
            ProductListDTO dto = new ProductListDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());

            // Image conversion to base64
            if (product.getImageData() != null) {
                String base64 = Base64.getEncoder().encodeToString(product.getImageData());
                dto.setImageUrl("data:" + product.getImageType() + ";base64," + base64);
            }

            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") int id) {
        Product product = service.getProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/product")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) throws IOException {
        Product product1 = service.addProduct(product, imageFile);
        return new ResponseEntity<>(product1, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByid(@PathVariable int productId) {
        Product product = service.getProduct(productId);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }


    @PutMapping("/product/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile) throws IOException {
        service.updateProduct(id, product, imageFile);
        return new ResponseEntity<>("updated", HttpStatus.OK);
    }


    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {
        Product product = service.getProduct(id);
        if (product != null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/products/search")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addNewUser(userInfo);
    }


    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody AuthReq authReq) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword()));

        if (authentication.isAuthenticated()) {

            String token = jwtService.generateToken(authReq.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        } else {
            throw new UsernameNotFoundException("Invalid user details");
        }
    }

}

