package com.lateras.pos.controller;

import com.lateras.pos.model.WebResponse;
import com.lateras.pos.model.request.ProductCreateRequest;
import com.lateras.pos.model.response.ProductResponse;
import com.lateras.pos.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(
            path = "/products",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public WebResponse<ProductResponse> insert(@RequestBody ProductCreateRequest createRequest) {
        ProductResponse productResponse = productService.insert(createRequest);
        return WebResponse.<ProductResponse>builder().data(productResponse).build();
    }

}
