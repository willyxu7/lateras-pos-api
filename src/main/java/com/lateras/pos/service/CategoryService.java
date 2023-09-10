package com.lateras.pos.service;

import com.lateras.pos.entity.Category;
import com.lateras.pos.model.request.CategoryCreateRequest;
import com.lateras.pos.model.request.CategoryUpdateRequest;
import com.lateras.pos.model.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    public List<CategoryResponse> findAll();

    public CategoryResponse findCategoryResponseById(String categoryId);

    public CategoryResponse insert(CategoryCreateRequest createRequest);

    public CategoryResponse update(String categoryId, CategoryUpdateRequest updateRequest);

    public void delete(String categoryId);

    public Category findById(String categoryId);

}
