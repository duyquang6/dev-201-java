//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.launch.basicwebapp.model;

import java.io.Serializable;
import java.math.BigDecimal;

import vn.kms.launch.basicwebapp.annotation.Table;

/**
 * @author thanhtran
 *
 */
@Table(name = "PRODUCTS")
public class Product implements Serializable {

    private static final long serialVersionUID = -5686380356149020133L;

    private Long id;

    private String name;

    private BigDecimal price;

    private ProductCategory category;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
