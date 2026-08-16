package com.everyfind.founditem;

import com.everyfind.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoundItemController {
    private final FoundItemService foundItemService;
    private final MemberRepository memberRepository;

    @Autowired
    public FoundItemController(FoundItemService foundItemService, MemberRepository memberRepository){
        this.foundItemService = foundItemService;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/found/items")
    public FoundItem createFoundItem(@RequestBody FoundItemRequestDto requestDto, @RequestParam Long memberId) {
        return foundItemService.createFoundItem(requestDto, memberId);
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
    public FoundItem updateFoundItem(@PathVariable Long foundId, @RequestParam Long memberId,
                                     @RequestBody FoundItemRequestDto requestDto) {

        return foundItemService.updateFoundItem(foundId, memberId, requestDto);
    }

    @DeleteMapping("/found/items/{foundId}")
    public void deleteFoundItem(@PathVariable Long foundId, @RequestParam Long memberId) {
        foundItemService.deleteFoundItem(foundId, memberId);
        System.out.println(foundId + "게시물이 삭제되었습니다.");
    }
}
