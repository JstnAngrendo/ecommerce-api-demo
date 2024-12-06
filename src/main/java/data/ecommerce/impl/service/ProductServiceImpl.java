package data.ecommerce.impl.service;

import data.ecommerce.api.model.Product;
import data.ecommerce.api.model.UserData;
import data.ecommerce.api.model.UserRole;
import data.ecommerce.api.service.ProductService;
import data.ecommerce.impl.accessor.ProductAccessor;
import data.ecommerce.impl.accessor.UserAccessor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Product saveProduct(@RequestBody Product product) {
        String userId = product.getUserId();

        UserData user = getUserById(userId);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (user.getRole() != UserRole.SELLER) {
            throw new RuntimeException("Only sellers can create products");
        }

        product.setUser(user);
        return productAccessor.saveProduct(product);
    }
//    @PostMapping("/insert")
//    public Product saveProduct(@RequestBody Product product, @RequestParam String userId) {
//        UserData user = getUserById(userId);
//
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        }
//
//        if (user.getRole() != UserRole.SELLER) {
//            throw new RuntimeException("Only sellers can create products");
//        }
//
//        product.setUser(user);
//        return productAccessor.saveProduct(product);
//    }

    private UserData getUserById(String userId) {
        return userAccessor.getUserData(userId);
    }



    @Override
    @GetMapping("/get-all")
    public List<Product> getAllProducts() {
        return productAccessor.getAllProducts();
    }

    @Override
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productAccessor.getProductById(id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable String id) {
        productAccessor.deleteProductById(id);
    }
}
