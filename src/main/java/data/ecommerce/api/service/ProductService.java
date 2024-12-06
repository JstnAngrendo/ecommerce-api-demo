package data.ecommerce.api.service;

import data.ecommerce.api.model.Product;
import java.util.List;
public interface ProductService {
//    Product saveProduct(Product product, String userId);
    Product saveProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(String id);
    void deleteProductById(String id);
}
