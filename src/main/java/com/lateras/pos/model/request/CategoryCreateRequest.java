package com.lateras.pos.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryCreateRequest {

    @NotNull @NotBlank
    private String name;

    @NotNull
    private String description;

}
