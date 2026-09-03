package com.everyfind.match;

import com.everyfind.founditem.FoundItem;

public class MatchResult {

    private final FoundItem foundItem;
    private final double score;

    public MatchResult(FoundItem foundItem, double score) {
        this.foundItem = foundItem;
        this.score = score;
    }

    public FoundItem getFoundItem() {
        return foundItem;
    }

    public double getScore() {
        return score;
    }
}