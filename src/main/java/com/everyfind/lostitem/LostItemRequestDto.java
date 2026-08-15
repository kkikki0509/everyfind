package com.everyfind.lostitem;

import java.time.LocalDate;

public class LostItemRequestDto {

    private String title;
    private String category;
    private String lostPlace;
    private String feature;
    private LocalDate lostDate;

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getLostPlace() {
        return lostPlace;
    }

    public String getFeature() {
        return feature;
    }

    public LocalDate getLostDate() {
        return lostDate;
    }
}