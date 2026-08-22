package com.everyfind.founditem;

import com.everyfind.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoundItemController {
    private final FoundItemService foundItemService;

    @Autowired
    public FoundItemController(FoundItemService foundItemService, MemberRepository memberRepository){
        this.foundItemService = foundItemService;
    }

    @PostMapping("/found/items")
    public FoundItem createFoundItem(@RequestBody FoundItemRequestDto requestDto,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        return foundItemService.createFoundItem(requestDto, userDetails.getUsername());
    }

    @GetMapping("/found/items")
    public List<FoundItem> getFoundItems() {
        return foundItemService.getFoundItems();
    }

    @GetMapping("/found/items/{foundId}")
    public FoundItem getFoundItem(@PathVariable Long foundId) {
        return foundItemService.getFoundItem(foundId);
    }

    @PutMapping("/found/items/{foundId}")
    public FoundItem updateFoundItem(@PathVariable Long foundId, @AuthenticationPrincipal UserDetails userDetails,
                                     @RequestBody FoundItemRequestDto requestDto) {

        return foundItemService.updateFoundItem(foundId, userDetails.getUsername(), requestDto);
    }

    @DeleteMapping("/found/items/{foundId}")
    public void deleteFoundItem(@PathVariable Long foundId, @AuthenticationPrincipal UserDetails userDetails) {
        foundItemService.deleteFoundItem(foundId, userDetails.getUsername());
        System.out.println(foundId + "게시물이 삭제되었습니다.");
    }
}
