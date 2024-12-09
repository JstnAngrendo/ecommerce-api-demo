package data.ecommerce.impl.accessor;

import data.ecommerce.api.model.Product;
import data.ecommerce.api.model.Restriction;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Service
public class RestrictionAccessor {

    private static final String COLLECTION_NAME = "restriction";

    @Autowired
    private MongoTemplate mongoTemplate;

    public Restriction saveRestriction(Restriction restriction) {
        return mongoTemplate.save(restriction, COLLECTION_NAME);
    }

    public List<Restriction> getAllRestrictions() {
        return mongoTemplate.findAll(Restriction.class, COLLECTION_NAME);
    }

    public Restriction getRestrictionById(String id) {
        return mongoTemplate.findById(id, Restriction.class, COLLECTION_NAME);
    }

    public void deleteRestrictionById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, COLLECTION_NAME);
    }
}