package data.ecommerce.impl.accessor;

import data.ecommerce.api.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccessor {
    private static final String COLLECTION_NAME = "user";
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UserData> getAllUserData() {
        return mongoTemplate.findAll(UserData.class, COLLECTION_NAME);
    }

    public UserData saveUser(UserData userData) {
        return mongoTemplate.save(userData, COLLECTION_NAME);
    }

    public void deleteUser(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, COLLECTION_NAME);
    }

    public void deleteUserbyEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        mongoTemplate.remove(query, COLLECTION_NAME);
    }

    public UserData getUserData (String id) {
        return mongoTemplate.findById(id, UserData.class, COLLECTION_NAME);
    }

    public UserData getByFilter (String key, String value) {
        Query query = new Query(Criteria.where(key).is(value));
        return mongoTemplate.findOne(query, UserData.class, COLLECTION_NAME);
    }

}
