package com.everyfind.lostitem;

import com.everyfind.member.Member;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lost_items")
public class LostItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lost_item_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "category")
    private String category;

    @Column(name = "lost_place")
    private String lostPlace;

    @Column(name = "lost_date")
    private LocalDate lostDate;

    @Column(name = "feature")
    private String feature;

    @Column(name = "current_status")
    private String currentStatus = "LOST";

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    protected LostItem() {}
    protected LostItem(String title, String category, String lostPlace, String feature,
                    LocalDate lostDate, Member member) {
        this.title = title;
        this.category = category;
        this.lostPlace = lostPlace;
        this.feature = feature;
        this.lostDate = lostDate;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Member getMember() {
        return member;
    }
}
