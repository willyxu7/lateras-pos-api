package com.lateras.pos.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lateras.pos.entity.Category;
import com.lateras.pos.model.WebResponse;
import com.lateras.pos.model.request.CategoryCreateRequest;
import com.lateras.pos.model.request.ProductCreateRequest;
import com.lateras.pos.model.response.ProductResponse;
import com.lateras.pos.repository.CategoryRepository;
import com.lateras.pos.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest extends BaseControllerTest {

    private final String productUrl = "/products";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void insertSuccess() throws Exception {
        Category category = Category.builder()
                .name("Beverage")
                .description("")
                .build();

        category = categoryRepository.save(category);

        ProductCreateRequest productCreateRequest = ProductCreateRequest.builder()
                .name("Coffee")
                .description("")
                .categoryId(category.getId())
                .build();

        String requestJson = objectMapper.writeValueAsString(productCreateRequest);

        mockMvc.perform(
                post(baseUrlV1 + productUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            WebResponse<ProductResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<ProductResponse>>() {
            });
            assertEquals(response.getData().getName(), productCreateRequest.getName());
            assertEquals(response.getData().getDescription(), productCreateRequest.getDescription());
        });

    }
}