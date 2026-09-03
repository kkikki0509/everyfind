package com.everyfind.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    // 매칭 확정
    @PutMapping("/matches/{lostId}/{foundId}")
    public void confirmMatch(@PathVariable Long lostId, @PathVariable Long foundId) {

        matchService.confirmMatch(lostId, foundId);
    }
}