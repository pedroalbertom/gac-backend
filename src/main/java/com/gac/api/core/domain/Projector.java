package com.gac.api.core.domain;

public class Projector {

    private Long id;
    private String brand;
    private String model;
    private String assetTag;
    private ItemStatus status;

    public Projector() {
    }

    public Projector(Long id, String brand, String model, String assetTag, ItemStatus status) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.assetTag = assetTag;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public void setAssetTag(String assetTag) {
        this.assetTag = assetTag;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }
}
