package data.ecommerce.api.service;

import data.ecommerce.api.model.Product;
import data.general.model.BaseResponse;
import java.util.List;
public interface ProductService {
//    Product saveProduct(Product product, String userId);
    BaseResponse saveProduct(Product product);
    BaseResponse getAllProducts();
    BaseResponse getProductById(String id);
    BaseResponse deleteProductById(String id);
    BaseResponse updateProduct(String id, Product product);
}
