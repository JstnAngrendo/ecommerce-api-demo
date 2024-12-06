package data.ecommerce.api.service;

import data.ecommerce.api.model.PaginationRequest;
import data.ecommerce.api.model.UserData;

public interface UserService {
    Object saveUser(UserData userData);
    Object getAllUser(PaginationRequest paginationRequest);
    Object getById(String id);

    Object deleteUser(String id);
    Object deleteUserByEmail(String email);
    Object updateUser(String id, UserData userData);
    Object getUserByEmail(String email);
}
