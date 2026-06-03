package com.gac.api.domain.model;

public class Key {

    private Long id;
    private String room;
    private String block;
    private String assetTag;
    private boolean spareKey;
    private ItemStatus status;
    private String reservedRegistrationNumber;
    private String defectDescription;

    public Key() {
    }

    public Key(
            Long id,
            String room,
            String block,
            String assetTag,
            boolean spareKey,
            ItemStatus status,
            String reservedRegistrationNumber,
            String defectDescription) {
        this.id = id;
        this.room = room;
        this.block = block;
        this.assetTag = assetTag;
        this.spareKey = spareKey;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public void setAssetTag(String assetTag) {
        this.assetTag = assetTag;
    }

    public boolean isSpareKey() {
        return spareKey;
    }

    public void setSpareKey(boolean spareKey) {
        this.spareKey = spareKey;
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
