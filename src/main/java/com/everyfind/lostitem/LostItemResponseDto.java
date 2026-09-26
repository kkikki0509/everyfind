package com.everyfind.lostitem;

import java.time.LocalDate;

public class LostItemResponseDto {

    private final Long id;
    private final String title;
    private final String category;
    private final String lostPlace;
    private final LocalDate lostDate;
    private final String feature;
    private final String currentStatus;

    public LostItemResponseDto(LostItem lostItem) {
        this.id = lostItem.getId();
        this.title = lostItem.getTitle();
        this.category = lostItem.getCategory();
        this.lostPlace = lostItem.getLostPlace();
        this.lostDate = lostItem.getLostDate();
        this.feature = lostItem.getFeature();
        this.currentStatus = lostItem.getCurrentStatus();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getLostPlace() {
        return lostPlace;
    }

    public LocalDate getLostDate() {
        return lostDate;
    }

    public String getFeature() {
        return feature;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }
}