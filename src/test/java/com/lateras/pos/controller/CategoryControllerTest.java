package com.lateras.pos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lateras.pos.entity.Category;
import com.lateras.pos.model.request.CategoryCreateRequest;
import com.lateras.pos.model.request.CategoryUpdateRequest;
import com.lateras.pos.model.response.CategoryResponse;
import com.lateras.pos.model.WebResponse;
import com.lateras.pos.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
    }


    @Test
    void findAllSuccess() throws Exception {
        for (int i = 0; i < 5; i++) {
            Category category = Category.builder()
                    .name("Food")
                    .description("")
                    .build();
            categoryRepository.save(category);
        }

        mockMvc.perform(
                get("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
           WebResponse<List<CategoryResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<List<CategoryResponse>>>() {
           });
           assertNull(response.getErrors());
           assertEquals(5, response.getData().size());
        });
    }

    @Test
    void findAllEmptyData() throws Exception {
        mockMvc.perform(
                get("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<CategoryResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<List<CategoryResponse>>>() {
            });
            assertEquals(0, response.getData().size());
        });
    }

    @Test
    void findByIdSuccess() throws Exception {
        Category category = Category.builder()
                .name("Food")
                .description("")
                .build();

        Category savecCategory = categoryRepository.save(category);

        mockMvc.perform(
                get("/categories/" + savecCategory.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
           WebResponse<CategoryResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<CategoryResponse>>() {
           });
           assertEquals(savecCategory.getId(), response.getData().getId());
           assertEquals(savecCategory.getName(), response.getData().getName());
           assertEquals(savecCategory.getDescription(), response.getData().getDescription());
        });
    }

    @Test
    void findByIdNotFound() throws Exception {
        Category category = Category.builder()
                .name("Food")
                .description("")
                .build();

        categoryRepository.save(category);

        mockMvc.perform(
                get("/categories/wrongId")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void createSuccess() throws Exception {
        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest();
        categoryCreateRequest.setName("Food");
        categoryCreateRequest.setDescription("");

        String requestJson = objectMapper.writeValueAsString(categoryCreateRequest);

        mockMvc.perform(
                post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            WebResponse<CategoryResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<CategoryResponse>>() {
            });

            assertEquals(categoryCreateRequest.getName(), response.getData().getName());
            assertEquals(categoryCreateRequest.getDescription(), response.getData().getDescription());
        });
    }

    @Test
    void createBadRequest() throws Exception {
        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest();
        categoryCreateRequest.setName("");
        categoryCreateRequest.setDescription("");

        String requestJson = objectMapper.writeValueAsString(categoryCreateRequest);

        mockMvc.perform(
                post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void updateSuccess() throws Exception {
        Category category = Category.builder()
                .name("Beverage")
                .description("new beverage")
                .build();

        Category savedCategory = categoryRepository.save(category);

        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest();
        categoryUpdateRequest.setName("Updated Beverage");
        categoryUpdateRequest.setDescription("Updated Beverage");

        String requestJson = objectMapper.writeValueAsString(categoryUpdateRequest);

        mockMvc.perform(
                put("/categories/" + savedCategory.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<CategoryResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<CategoryResponse>>() {
            });
            assertEquals(categoryUpdateRequest.getName(), response.getData().getName());
            assertEquals(categoryUpdateRequest.getDescription(), response.getData().getDescription());
        });
    }

    @Test
    void updateBadRequest() throws Exception {
        Category category = Category.builder()
                .name("Beverage")
                .description("new beverage")
                .build();

        Category savedCategory = categoryRepository.save(category);

        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest();
        categoryUpdateRequest.setName("");
        categoryUpdateRequest.setDescription("");

        String requestJson = objectMapper.writeValueAsString(categoryUpdateRequest);

        mockMvc.perform(
                put("/categories/" + savedCategory.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void updateNotFound() throws Exception {
        Category category = Category.builder()
                .name("Beverage")
                .description("new beverage")
                .build();

        Category savedCategory = categoryRepository.save(category);

        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest();
        categoryUpdateRequest.setName("Update Beverage");
        categoryUpdateRequest.setDescription("Update Beverage");

        String requestJson = objectMapper.writeValueAsString(categoryUpdateRequest);

        mockMvc.perform(
                put("/categories/wrongId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void deleteSuccess() throws Exception {
        Category category = Category.builder()
                .name("Beverage")
                .description("")
                .build();

        Category savedCategory = categoryRepository.save(category);

        mockMvc.perform(
                delete("/categories/" + savedCategory.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNoContent()
        );
    }

    @Test
    void deleteNotFound() throws Exception {
        Category category = Category.builder()
                .name("Beverage")
                .description("")
                .build();

        Category savedCategory = categoryRepository.save(category);

        mockMvc.perform(
                delete("/categories/wrongId")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        );
    }
}