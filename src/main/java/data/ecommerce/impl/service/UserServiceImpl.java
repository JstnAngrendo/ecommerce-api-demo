package data.ecommerce.impl.service;

import data.general.model.BaseResponse;
import data.general.util.ResponseUtil;
import data.ecommerce.api.model.PaginationRequest;
import data.ecommerce.api.model.UserData;
import data.ecommerce.api.model.UserDataErrorResponse;
import data.ecommerce.api.model.UserRole;
import data.ecommerce.api.service.UserService;
import data.ecommerce.impl.accessor.UserAccessor;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserAccessor userAccessor;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    @PostMapping("/insert")
    public BaseResponse saveUser( @Valid @RequestBody UserData userData) {
        System.out.println("ROLE DATA: " + userData.getRole());

        if (userData == null || userData.getName() == null || userData.getPassword() == null || userData.getEmail() == null){
            Map<String, String> errors = Map.of(
                    "name", "Name cannot be null",
                    "password", "Password cannot be null",
                    "email", "Email cannot be null"
            );
            UserDataErrorResponse errorResponse = UserDataErrorResponse.builder()
                    .errors(errors)
                    .build();
            return ResponseUtil.generateErrorResponse(HttpStatus.BAD_REQUEST, errorResponse);
        }
        userData.setId(UUID.randomUUID().toString());
        userData.setCreatedDate(System.currentTimeMillis());

        String encryptPassword = passwordEncoder.encode(userData.getPassword());
        userData.setPassword(encryptPassword);

        if (userData.getRole() == null) {
            userData.setRole(UserRole.CUSTOMER);
        }
        UserData data = userAccessor.saveUser(userData);
        return ResponseUtil.generateSuccessResponse(data, HttpStatus.OK);
    }

    @Override
    @PostMapping("/get-all")
    public BaseResponse getAllUser(@RequestBody PaginationRequest request) {
        List<UserData> allUser = userAccessor.getAllUserData();
        int size = request.getSize();
        int page = request.getPage();

        int start = (page - 1) * size;
        int end = Math.min(start + size, allUser.size());

        if (start >= allUser.size()) {
            return ResponseUtil.generateSuccessResponse(List.of(), HttpStatus.OK);
        }

        List<UserData> paginatedUser = allUser.subList(start, end);
        paginatedUser.forEach(userData -> userData.setPassword(null));

        return ResponseUtil.generateSuccessResponse(paginatedUser, HttpStatus.OK);
    }

    @Override
    @GetMapping("/get-user/{id}")
    public BaseResponse getById(@PathVariable String id) {
        UserData user = userAccessor.getUserData(id);
        if (user == null) {
            return ResponseUtil.generateErrorResponse(
                    HttpStatus.NOT_FOUND,
                    "User not found with id: " + id
            );
        }
        user.setPassword(null);
        return ResponseUtil.generateSuccessResponse(user, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public BaseResponse deleteUser(@PathVariable String id) {
        UserData user = userAccessor.getUserData(id);
        if (user == null) {
            return ResponseUtil.generateErrorResponse(
                    HttpStatus.NOT_FOUND,
                    "User not found with id: " + id
            );
        }
        userAccessor.deleteUser(id);
        return ResponseUtil.generateSuccessResponse("User deleted successfully", HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete-by-email/{email}")
    public BaseResponse deleteUserByEmail(@PathVariable String email) {
        UserData user = userAccessor.getByFilter("email", email);
        if (user == null) {
            return ResponseUtil.generateErrorResponse(
                    HttpStatus.NOT_FOUND,
                    "User not found with email: " + email
            );
        }
        userAccessor.deleteUserbyEmail(email);
        return ResponseUtil.generateSuccessResponse("User deleted successfully", HttpStatus.OK);
    }


    @Override
    @PutMapping("/update/{id}")
    public BaseResponse updateUser(@PathVariable String id, @RequestBody UserData userData) {
        UserData existingUser = userAccessor.getUserData(id);
        if (existingUser == null) {
            return ResponseUtil.generateErrorResponse(
                    HttpStatus.NOT_FOUND,
                    "User not found with id: " + id
            );
        }

        existingUser.setName(userData.getName() != null ? userData.getName() : existingUser.getName());
        existingUser.setEmail(userData.getEmail() != null ? userData.getEmail() : existingUser.getEmail());
        existingUser.setRole(userData.getRole() != null ? userData.getRole() : existingUser.getRole());

        if (userData.getPassword() != null) {
            String encryptedPassword = passwordEncoder.encode(userData.getPassword());
            existingUser.setPassword(encryptedPassword);
        }

        UserData updatedUser = userAccessor.saveUser(existingUser);
        updatedUser.setPassword(null);
        return ResponseUtil.generateSuccessResponse(updatedUser, HttpStatus.OK);
    }

    @Override
    @GetMapping("/get-user-by-email/{email}")
    public BaseResponse getUserByEmail(@PathVariable  String email) {
        UserData user = userAccessor.getByFilter("email", email);
        if (user == null) {
            return ResponseUtil.generateErrorResponse(
                    HttpStatus.NOT_FOUND,
                    "User not found with email: " + email
            );
        }
        user.setPassword(null);
        return ResponseUtil.generateSuccessResponse(user, HttpStatus.OK);
    }

}
