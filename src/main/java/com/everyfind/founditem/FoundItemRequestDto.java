package com.everyfind.founditem;

import java.time.LocalDate;

public class FoundItemRequestDto {

    private String title;
    private String category;
    private String foundPlace;
    private String feature;
    private LocalDate foundDate;

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getFoundPlace() {
        return foundPlace;
    }

    public String getFeature() {
        return feature;
    }

    public LocalDate getFoundDate() {
        return foundDate;
    }
}