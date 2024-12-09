package data.ecommerce.api.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
public class Restriction {
    @Id
    private String ruleId;
    @Indexed
    private String userId;
    private String userName;
    private RestrictionCriteria criteria;
    private String productId;
    private String productName;
    private String sellerId;
    private String sellerName;
    private long createdDate;

    public enum RestrictionCriteria {
        BY_SELLER, BY_PRODUCT;
    }
}