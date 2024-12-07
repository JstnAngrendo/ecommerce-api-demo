package data.general.model;


import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class SuccessResponse<T> extends BaseResponse{
//    private boolean success;
//    private int code;
//    private HttpStatus status;
    private T data;
}
