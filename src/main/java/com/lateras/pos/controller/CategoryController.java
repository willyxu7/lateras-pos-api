package com.lateras.pos.controller;

import com.lateras.pos.model.request.CategoryCreateRequest;
import com.lateras.pos.model.request.CategoryUpdateRequest;
import com.lateras.pos.model.response.CategoryResponse;
import com.lateras.pos.model.WebResponse;
import com.lateras.pos.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(
            path = "/categories",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public WebResponse<List<CategoryResponse>> findAll() {
        List<CategoryResponse> categoryResponses = categoryService.findAll();
        return WebResponse.<List<CategoryResponse>>builder().data(categoryResponses).build();
    }

    @GetMapping(
            path = "/categories/{categoryId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public WebResponse<CategoryResponse> findById(@PathVariable(name = "categoryId") String categoryId) {
        CategoryResponse categoryResponse = categoryService.findCategoryResponseById(categoryId);
        return WebResponse.<CategoryResponse>builder().data(categoryResponse).build();
    }

    @PostMapping(
            path = "/categories",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    public WebResponse<CategoryResponse> insert(@RequestBody CategoryCreateRequest categoryCreateRequest) {
        CategoryResponse categoryResponse = categoryService.insert(categoryCreateRequest);
        return WebResponse.<CategoryResponse>builder().data(categoryResponse).build();
    }

    @PutMapping(
            path = "/categories/{categoryId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public WebResponse<CategoryResponse> update(
            @PathVariable("categoryId") String categoryId,
            @RequestBody CategoryUpdateRequest categoryUpdateRequest){
        CategoryResponse categoryResponse = categoryService.update(categoryId, categoryUpdateRequest);
        return WebResponse.<CategoryResponse>builder().data(categoryResponse).build();
    }

    @DeleteMapping(
            path = "/categories/{categoryId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public WebResponse<Void> delete(@PathVariable(name = "categoryId") String categoryId) {
        categoryService.delete(categoryId);
        return WebResponse.<Void>builder().build();
    }

}
