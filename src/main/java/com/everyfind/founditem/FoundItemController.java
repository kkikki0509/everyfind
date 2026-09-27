package com.everyfind.founditem;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoundItemController {
    private final FoundItemService foundItemService;

    public FoundItemController(FoundItemService foundItemService){
        this.foundItemService = foundItemService;
    }

    @PostMapping("/found/items") // 습득물 게시물 생성
    public FoundItemResponseDto createFoundItem(@RequestBody FoundItemRequestDto requestDto,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        return foundItemService.createFoundItem(requestDto, userDetails.getUsername());
    }

    @GetMapping("/found/items") // 습득물 게시물 전체 조회
    public List<FoundItemResponseDto> getFoundItems(@AuthenticationPrincipal UserDetails userDetails) {
        return foundItemService.getFoundItems(userDetails.getUsername());
    }

    @GetMapping("/found/items/{foundId}") // 습득물 게시물 단건 조회
    public FoundItemResponseDto getFoundItem(@PathVariable Long foundId, @AuthenticationPrincipal UserDetails userDetails) {
        return foundItemService.getFoundItem(foundId, userDetails.getUsername());
    }

    @PutMapping("/found/items/{foundId}") // 습득물 게시물 수정
    public void updateFoundItem(@PathVariable Long foundId, @AuthenticationPrincipal UserDetails userDetails,
                                     @RequestBody FoundItemRequestDto requestDto) {

        foundItemService.updateFoundItem(foundId, userDetails.getUsername(), requestDto);
    }

    @DeleteMapping("/found/items/{foundId}") // 습득물 게시물 제거
    public void deleteFoundItem(@PathVariable Long foundId, @AuthenticationPrincipal UserDetails userDetails) {
        foundItemService.deleteFoundItem(foundId, userDetails.getUsername());
    }
}
