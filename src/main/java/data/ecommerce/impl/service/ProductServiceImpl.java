package data.ecommerce.impl.service;

import data.ecommerce.api.model.Product;
import data.ecommerce.api.model.UserData;
import data.ecommerce.api.model.UserRole;
import data.ecommerce.api.service.ProductService;
import data.ecommerce.impl.accessor.ProductAccessor;
import data.ecommerce.impl.accessor.UserAccessor;
import data.general.model.BaseResponse;
import data.general.util.ResponseUtil;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductAccessor productAccessor;

    @Autowired
    private UserAccessor userAccessor;

    @Override
    @PostMapping("/insert")
    public BaseResponse saveProduct(@Valid @RequestBody Product product) {
        try {
            if (product.getName() == null || product.getName().isEmpty() ||
                    product.getPrice() <= 0 || product.getUserId() == null || product.getUserId().isEmpty()) {

                Map<String, String> errors = new HashMap<>();
                if (product.getName() == null || product.getName().isEmpty()) {
                    errors.put("name", "Name cannot be null or empty");
                }
                if (product.getPrice() <= 0) {
                    errors.put("price", "Price must be greater than 0");
                }
                if (product.getUserId() == null || product.getUserId().isEmpty()) {
                    errors.put("userId", "User ID cannot be null or empty");
                }
                return ResponseUtil.generateErrorResponse(HttpStatus.BAD_REQUEST, errors);
            }

            UserData user = getUserById(product.getUserId());
            if (user == null) {
                return ResponseUtil.generateErrorResponse(HttpStatus.NOT_FOUND, "User not found");
            }

            if (user.getRole() != UserRole.SELLER) {
                return ResponseUtil.generateErrorResponse(HttpStatus.FORBIDDEN, "Only sellers can create products");
            }

            product.setId(UUID.randomUUID().toString());
            product.setCreatedDate(System.currentTimeMillis());
            Product savedProduct = productAccessor.saveProduct(product);

            return ResponseUtil.generateSuccessResponse(savedProduct, HttpStatus.CREATED);

        } catch (Exception ex) {
            Map<String, String> errors = Map.of("error", "An unexpected error occurred: " + ex.getMessage());
            return ResponseUtil.generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errors);
        }
    }

    private UserData getUserById(String userId) {
        return userAccessor.getUserData(userId);
    }

    @Override
    @GetMapping("/get-all")
    public BaseResponse getAllProducts() {
        try {
            List<Product> products = productAccessor.getAllProducts();
            if (products.isEmpty()) {
                return ResponseUtil.generateErrorResponse(HttpStatus.NOT_FOUND, "No products found");
            }
            return ResponseUtil.generateSuccessResponse(products, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = Map.of("error", "An unexpected error occurred: " + ex.getMessage());
            return ResponseUtil.generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errors);
        }
    }

    @Override
    @GetMapping("/{id}")
    public BaseResponse getProductById(@PathVariable String id) {
        try {
            Product product = productAccessor.getProductById(id);
            if (product == null) {
                return ResponseUtil.generateErrorResponse(HttpStatus.NOT_FOUND, "Product not found");
            }
            return ResponseUtil.generateSuccessResponse(product, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = Map.of("error", "An unexpected error occurred: " + ex.getMessage());
            return ResponseUtil.generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errors);
        }
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public BaseResponse deleteProductById(@PathVariable String id) {
        try {
            Product product = productAccessor.getProductById(id);
            if (product == null) {
                return ResponseUtil.generateErrorResponse(HttpStatus.NOT_FOUND, "Product not found");
            }
            productAccessor.deleteProductById(id);
            return ResponseUtil.generateSuccessResponse(null, HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            Map<String, String> errors = Map.of("error", "An unexpected error occurred: " + ex.getMessage());
            return ResponseUtil.generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errors);
        }
    }

    @Override
    @PutMapping("/update/{id}")
    public BaseResponse updateProduct(String id, Product product) {
        try {
            Product existingProduct = productAccessor.getProductById(id);
            if (existingProduct == null) {
                return ResponseUtil.generateErrorResponse(HttpStatus.NOT_FOUND, "Product not found");
            }

            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setImageUrl(product.getImageUrl());

            Product updatedProduct = productAccessor.saveProduct(existingProduct);
            return ResponseUtil.generateSuccessResponse(updatedProduct, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = Map.of("error", "An unexpected error occurred: " + ex.getMessage());
            return ResponseUtil.generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errors);
        }
    }
}
