package com.everyfind.founditem;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FoundItemResponseDto {

    private final Long id;
    private final String title;
    private final String category;
    private final String foundPlace;
    private final LocalDate foundDate;
    private final String feature;
    private final LocalDateTime createdAt;

    public FoundItemResponseDto(FoundItem foundItem) {
        this.id = foundItem.getId();
        this.title = foundItem.getTitle();
        this.category = foundItem.getCategory();
        this.foundPlace = foundItem.getFoundPlace();
        this.foundDate = foundItem.getFoundDate();
        this.feature = foundItem.getFeature();
        this.createdAt = foundItem.getCreatedAt();
    }

    public Long getId() { return id; }

    public String getTitle() { return title; }

    public String getCategory() { return category; }

    public String getFoundPlace() { return foundPlace; }

    public LocalDate getFoundDate() { return foundDate; }

    public String getFeature() { return feature; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}