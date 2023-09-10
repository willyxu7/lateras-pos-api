package com.lateras.pos.service;

import com.lateras.pos.model.request.ProductCreateRequest;
import com.lateras.pos.model.response.ProductResponse;

public interface ProductService {

    public ProductResponse insert(ProductCreateRequest productCreateRequest);

}
