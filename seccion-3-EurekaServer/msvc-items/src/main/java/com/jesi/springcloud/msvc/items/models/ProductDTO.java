package com.jesi.springcloud.msvc.items.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private LocalDate createAt;
    // cuidado: los nombres deben llamarse igual al del microservicio del producto
    // sino Jackson
    // puede no asignar ese valor al campo createAt, y me manda null.
    /*
     * Solucion
     * 1- Cambio el nombre del dto igual al del msvc-product
     * 2-Uso JSON Property
     * 
     * @JsonProperty("createAT")
     */

    @JsonProperty("port")
    private int portMsvcProduct;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public int getPortMsvcProduct() {
        return portMsvcProduct;
    }

    public void setPortMsvcProduct(int portMsvcProduct) {
        this.portMsvcProduct = portMsvcProduct;
    }

}
