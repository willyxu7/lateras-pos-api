package com.lateras.pos.service.impl;

import com.lateras.pos.entity.Category;
import com.lateras.pos.mapper.CategoryMapper;
import com.lateras.pos.model.request.CategoryCreateRequest;
import com.lateras.pos.model.request.CategoryUpdateRequest;
import com.lateras.pos.model.response.CategoryResponse;
import com.lateras.pos.repository.CategoryRepository;
import com.lateras.pos.service.CategoryService;
import com.lateras.pos.service.ValidationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final ValidationService validationService;

    @Override
    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(categoryMapper::toCategoryResponse).toList();
    }

    @Override
    public CategoryResponse findCategoryResponseById(String categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        return categoryOptional.map(categoryMapper::toCategoryResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category could not be found"));
    }

    @Transactional
    @Override
    public CategoryResponse insert(CategoryCreateRequest createRequest) {
        validationService.validate(createRequest);

        Category category = Category.builder()
                .name(createRequest.getName())
                .description(createRequest.getDescription()).build();

        category = categoryRepository.save(category);

        log.info(category.toString());

        return categoryMapper.toCategoryResponse(category);
    }

    @Transactional
    @Override
    public CategoryResponse update(String categoryId, CategoryUpdateRequest updateRequest) {
        validationService.validate(updateRequest);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category could not be found"));

        category.setName(updateRequest.getName());
        category.setDescription(updateRequest.getDescription());
        category = categoryRepository.save(category);

        return categoryMapper.toCategoryResponse(category);
    }

    @Transactional
    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category could not be found"));

        categoryRepository.delete(category);
    }

    @Override
    public Category findById(String categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category could not be found"));
    }
}
