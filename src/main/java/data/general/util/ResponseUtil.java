package data.general.util;

import data.general.model.ErrorResponse;
import data.general.model.SuccessResponse;
import org.springframework.http.HttpStatus;

public class ResponseUtil {
    public static <T> SuccessResponse<T> generateSuccessResponse(T data, HttpStatus code) {
        SuccessResponse<T> response = new SuccessResponse<>();
        response.setSuccess(true);
        response.setCode(code.value());
        response.setStatus(code);
        response.setData(data);
        return response;
    }
    public static <T> ErrorResponse<T> generateErrorResponse(HttpStatus code, T errors) {
        ErrorResponse<T> response = new ErrorResponse<>();
        response.setSuccess(false);
        response.setCode(code.value());
        response.setStatus(code);
        response.setErrors(errors);
        return response;
    }
}
