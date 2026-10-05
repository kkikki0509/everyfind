package com.everyfind.match;

import org.springframework.web.bind.annotation.*;

@RestController
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PutMapping("/matches/{lostId}") // 매칭 확정 수정 요청
    public void confirmMatch(@PathVariable Long lostId) {
        matchService.confirmMatch(lostId);
    }
}