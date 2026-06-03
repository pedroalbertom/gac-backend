package com.gac.api.core.domain;

public class Key {

    private Long id;
    private String room;
    private String block;
    private ItemStatus status;

    public Key() {
    }

    public Key(Long id, String room, String block, ItemStatus status) {
        this.id = id;
        this.room = room;
        this.block = block;
        this.status = status;
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

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }
}
