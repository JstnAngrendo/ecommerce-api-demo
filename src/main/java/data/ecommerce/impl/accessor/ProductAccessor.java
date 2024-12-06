package data.ecommerce.impl.accessor;

import data.ecommerce.api.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAccessor {
    private static final String COLLECTION_NAME = "product";

    @Autowired
    private MongoTemplate mongoTemplate;

    public Product saveProduct(Product product) {
        return mongoTemplate.save(product, COLLECTION_NAME);
    }

    public List<Product> getAllProducts() {
        return mongoTemplate.findAll(Product.class, COLLECTION_NAME);
    }

    public Product getProductById(String id) {
        return mongoTemplate.findById(id, Product.class, COLLECTION_NAME);
    }

    public void deleteProductById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, COLLECTION_NAME);
    }
}
