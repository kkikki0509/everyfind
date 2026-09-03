package com.everyfind.lostitem;

import com.everyfind.match.MatchResult;

import java.util.List;

public class LostItemMatchResponseDto {

    private final LostItem lostItem;
    private final List<MatchResult> matches;

    public LostItemMatchResponseDto(LostItem lostItem, List<MatchResult> matches) {
        this.lostItem = lostItem;
        this.matches = matches;
    }

    public LostItem getLostItem() {
        return lostItem;
    }

    public List<MatchResult> getMatches() {
        return matches;
    }
}