package com.everyfind.founditem;

import com.everyfind.member.Member;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "found_items")
public class FoundItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "found_item_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "category")
    private String category;

    @Column(name = "found_place")
    private String foundPlace;

    @Column(name = "found_date")
    private LocalDate foundDate;

    @Column(name = "feature")
    private String feature;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    protected FoundItem() {
    }

    protected FoundItem(String title, String category, String foundPlace, String feature,
                        LocalDate foundDate, Member member) {
        this.title = title;
        this.category = category;
        this.foundPlace = foundPlace;
        this.feature = feature;
        this.foundDate = foundDate;
        this.member = member;
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

    public String getFoundPlace() {
        return foundPlace;
    }

    public LocalDate getFoundDate() {
        return foundDate;
    }

    public String getFeature() {
        return feature;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Member getMember() {
        return member;
    }
}