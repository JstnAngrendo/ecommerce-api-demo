package data.ecommerce.api.model;


import lombok.Getter;

@Getter
public class PaginationRequest {
    private int page;
    private int size;
}
