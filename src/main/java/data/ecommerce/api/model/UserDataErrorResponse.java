package data.ecommerce.api.model;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDataErrorResponse {
    private Map<String, String> errors;
}
