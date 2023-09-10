package com.lateras.pos.service.impl;

import com.lateras.pos.entity.Category;
import com.lateras.pos.entity.Product;
import com.lateras.pos.mapper.ProductMapper;
import com.lateras.pos.model.request.ProductCreateRequest;
import com.lateras.pos.model.response.CategoryResponse;
import com.lateras.pos.model.response.ProductResponse;
import com.lateras.pos.repository.ProductRepository;
import com.lateras.pos.service.CategoryService;
import com.lateras.pos.service.ProductService;
import com.lateras.pos.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ValidationService validationService;

    private final CategoryService categoryService;

    @Override
    public ProductResponse insert(ProductCreateRequest productCreateRequest) {
        validationService.validate(productCreateRequest);

        Category category = categoryService.findById(productCreateRequest.getCategoryId());

        Product product = Product.builder()
                .name(productCreateRequest.getName())
                .category(category)
                .description(productCreateRequest.getDescription())
                .build();
        product = productRepository.save(product);

        return productMapper.toProductResponse(product);
    }
}
