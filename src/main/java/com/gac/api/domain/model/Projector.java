package com.gac.api.domain.model;

public class Projector {

    private Long id;
    private String brand;
    private String model;
    private String serialNumber;
    private String assetTag;
    private ItemStatus status;
    private String reservedRegistrationNumber;
    private String defectDescription;

    public Projector() {
    }

    public Projector(
            Long id,
            String brand,
            String model,
            String serialNumber,
            String assetTag,
            ItemStatus status,
            String reservedRegistrationNumber,
            String defectDescription) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.assetTag = assetTag;
        this.status = status;
        this.reservedRegistrationNumber = reservedRegistrationNumber;
        this.defectDescription = defectDescription;
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public String getReservedRegistrationNumber() {
        return reservedRegistrationNumber;
    }

    public void setReservedRegistrationNumber(String reservedRegistrationNumber) {
        this.reservedRegistrationNumber = reservedRegistrationNumber;
    }

    public String getDefectDescription() {
        return defectDescription;
    }

    public void setDefectDescription(String defectDescription) {
        this.defectDescription = defectDescription;
    }
}
