package org.example.model;

import java.io.Serializable;

public class Wine implements Serializable {
    private Long id;
    private String code;
    private String name;
    private Double concentration;
    private Long yearManufacture;
    private String image;
    private Long producerId;

    public Wine(String code, String name, Double concentration, Long yearManufacture, String image, Long producerId) {
        this.code = code;
        this.name = name;
        this.concentration = concentration;
        this.yearManufacture = yearManufacture;
        this.image = image;
        this.producerId = producerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getConcentration() {
        return concentration;
    }

    public void setConcentration(Double concentration) {
        this.concentration = concentration;
    }

    public Long getYearManufacture() {
        return yearManufacture;
    }

    public void setYearManufacture(Long yearManufacture) {
        this.yearManufacture = yearManufacture;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getProducerId() {
        return producerId;
    }

    public void setProducerId(Long producerId) {
        this.producerId = producerId;
    }

}
