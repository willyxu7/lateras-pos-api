package com.lateras.pos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@SQLDelete(sql = "UPDATE categories SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at IS NULL")
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false, columnDefinition = "varchar(36)")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
