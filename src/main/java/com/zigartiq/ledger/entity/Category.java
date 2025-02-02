package com.zigartiq.ledger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "name", "user_id" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    private String name;

    @NotBlank
    private String color;

    @NotBlank
    private String icon;

    public Category(String name, String color, String icon) {
        this.name = name;
        this.color = color;
        this.icon = icon;
    }

    public Category(User user, Category category) {
        this.user = user;
        this.name = category.getName();
        this.color = category.getColor();
        this.icon = category.getIcon();
    }

    public Category(User user, String name, String color, String icon) {
        this.user = user;
        this.name = name;
        this.color = color;
        this.icon = icon;
    }
}
