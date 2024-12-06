package data.general.model;
import lombok.*;

import java.util.List;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> {
    private boolean success;
    private int code;
    private HttpStatus status;
}
