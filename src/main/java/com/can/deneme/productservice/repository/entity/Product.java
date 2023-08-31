package com.can.deneme.productservice.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name="Product",schema = "stock_management")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long productId;

    @Column(name = "product_name")
    private String productName;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private int price;
    @Builder.Default
    @Column(name = "product_updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productUpdatedDate = new Date();
    @Builder.Default
    @Column(name = "product_create_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productCreateDate = new Date();
    @Column(name = "is_deleted")
    private boolean deleted;
}
