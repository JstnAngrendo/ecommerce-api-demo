package data.ecommerce.api.service;

import data.ecommerce.api.model.PaginationRequest;
import data.ecommerce.api.model.UserData;
import data.general.model.BaseResponse;

public interface UserService {
    BaseResponse saveUser(UserData userData);
    BaseResponse getAllUser(PaginationRequest paginationRequest);
    BaseResponse getById(String id);

    BaseResponse deleteUser(String id);
    BaseResponse deleteUserByEmail(String email);
    BaseResponse updateUser(String id, UserData userData);
    BaseResponse getUserByEmail(String email);
}
