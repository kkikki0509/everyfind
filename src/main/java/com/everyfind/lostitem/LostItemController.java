package com.everyfind.lostitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class LostItemController {
    private final LostItemService lostItemService;

    public LostItemController(LostItemService lostItemService){
        this.lostItemService = lostItemService;
    }

    @PostMapping("/lost/items") // 분실물 게시물 생성
    public LostItemMatchResponseDto createLostItem(@RequestBody LostItemRequestDto requestDto,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        return lostItemService.createLostItem(requestDto, userDetails.getUsername());
    }

    @GetMapping("/lost/items") // 전체 분실물 게시물 조회
    public List<LostItemResponseDto> getLostItems(@AuthenticationPrincipal UserDetails userDetails) {

        return lostItemService.getLostItems(userDetails.getUsername());
    }

    @GetMapping("/lost/items/{lostId}") // 단건 분실물 게시물 조회
    public LostItemMatchResponseDto getLostItem(@PathVariable Long lostId, @AuthenticationPrincipal UserDetails userDetails) {
        return lostItemService.getLostItem(lostId, userDetails.getUsername());
    }

    @PutMapping("/lost/items/{lostId}") // 게시물 수정
    public void updateLostItem(@PathVariable Long lostId, @RequestBody LostItemRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        lostItemService.updateLostItem(lostId, userDetails.getUsername(), requestDto);
    }

    @DeleteMapping("/lost/items/{lostId}") // 게시물 삭제
    public void deleteLostItem(@PathVariable Long lostId, @AuthenticationPrincipal UserDetails userDetails) {
        lostItemService.deleteLostItem(lostId, userDetails.getUsername());
    }

    @PostMapping("/lost/items/{lostId}/matches/{foundId}") // 분실물 찾았을 때
    public void confirmMatch(@PathVariable Long lostId, @PathVariable Long foundId,
                             @AuthenticationPrincipal UserDetails userDetails) {
        lostItemService.confirmMatch(lostId, foundId, userDetails.getUsername());
    }

}
