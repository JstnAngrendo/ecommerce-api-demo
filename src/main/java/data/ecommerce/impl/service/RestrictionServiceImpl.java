package data.ecommerce.impl.service;

import data.ecommerce.api.model.Product;
import data.ecommerce.api.model.Restriction;
import data.ecommerce.api.model.UserData;
import data.ecommerce.api.model.UserRole;
import data.ecommerce.api.service.RestrictionService;
import data.ecommerce.impl.accessor.ProductAccessor;
import data.ecommerce.impl.accessor.RestrictionAccessor;
import data.ecommerce.impl.accessor.UserAccessor;
import data.general.model.BaseResponse;
import data.general.util.ResponseUtil;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/restrictions")
public class RestrictionServiceImpl implements RestrictionService {

    @Autowired
    private RestrictionAccessor restrictionAccessor;

    @Autowired
    private UserAccessor userAccessor;

    @Autowired
    private ProductAccessor productAccessor;
    @Override
    @PostMapping("/create")
    public BaseResponse createRestriction(Restriction restriction) {
        String userId = restriction.getUserId();
        UserData user = getUserById(userId);

        if (user == null || user.getRole() != UserRole.ADMIN) {
            return ResponseUtil.generateErrorResponse(HttpStatus.FORBIDDEN, "Only ADMIN can create restrictions");
        }

        Restriction.RestrictionCriteria criteria = restriction.getCriteria();
        String productId = restriction.getProductId();
        String sellerId = restriction.getSellerId();

        if (criteria == Restriction.RestrictionCriteria.BY_PRODUCT) {
            if (productId == null || productId.isEmpty()) {
                return ResponseUtil.generateErrorResponse(HttpStatus.BAD_REQUEST, "Product ID is required for product restrictions");
            }
            if (sellerId != null && !sellerId.isEmpty()) {
                return ResponseUtil.generateErrorResponse(HttpStatus.BAD_REQUEST, "Cannot restrict both by seller and by product simultaneously");
            }
            Product product = productAccessor.getProductById(productId);
            if (product == null) {
                return ResponseUtil.generateErrorResponse(HttpStatus.NOT_FOUND, "Product does not exist");
            }
            restriction.setProductId(product.getId());
            restriction.setProductName(product.getName());
        } else if (criteria == Restriction.RestrictionCriteria.BY_SELLER) {
            if (sellerId == null || sellerId.isEmpty()) {
                return ResponseUtil.generateErrorResponse(HttpStatus.BAD_REQUEST, "Seller ID is required for seller restrictions");
            }
            if (productId != null && !productId.isEmpty()) {
                return ResponseUtil.generateErrorResponse(HttpStatus.BAD_REQUEST, "Cannot restrict both by seller and by product simultaneously");
            }
            UserData sellerUser = userAccessor.getUserData(sellerId);
            if (sellerUser == null) {
                return ResponseUtil.generateErrorResponse(HttpStatus.NOT_FOUND, "Seller does not exist");
            }
            restriction.setSellerId(sellerUser.getId());
            restriction.setSellerName(sellerUser.getName());
        } else {
            return ResponseUtil.generateErrorResponse(HttpStatus.BAD_REQUEST, "Invalid restriction criteria");
        }
        restriction.setUserName(user.getName());
        restriction.setCreatedDate(System.currentTimeMillis());
        restriction.setRuleId(UUID.randomUUID().toString());

        Restriction createdRestriction = restrictionAccessor.saveRestriction(restriction);

        return ResponseUtil.generateSuccessResponse(createdRestriction, HttpStatus.CREATED);
    }

    private UserData getUserById(String userId) {
        return userAccessor.getUserData(userId);
    }
}