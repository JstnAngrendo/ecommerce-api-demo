package data.general.util;

import data.general.model.BaseResponse;
import data.general.model.ErrorResponse;
import data.general.model.Status;
import data.general.model.SuccessResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> SuccessResponse<T> generateSuccessResponse(T data, HttpStatus code) {
        return SuccessResponse.<T>builder()
                .success(true)
                .code(code.value())
                .status(code)
                .data(data)
                .build();
    }

//     Error response
    public static <T> ErrorResponse<T> generateErrorResponse(HttpStatus code, T errors) {
        return ErrorResponse.<T>builder()
                .success(false)
                .code(code.value())
                .status(code)
                .errors(errors)
                .build();
    }

//    public static ResponseEntity<ErrorResponse> generateErrorResponse(HttpStatus code, Object errors) {
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .success(false)
//                .code(code.value())
//                .status(code)
//                .errors(errors)
//                .build();
//
//        return new ResponseEntity<>(errorResponse, code);
//    }
}
