package com.everyfind.lostitem;

import com.everyfind.member.Member;
import com.everyfind.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class LostItemController {
    private final LostItemService lostItemService;
    private final MemberRepository memberRepository;

    @Autowired
    public LostItemController(LostItemService lostItemService, MemberRepository memberRepository){
        this.lostItemService = lostItemService;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/lost/items")
    public LostItem createLostItem(@RequestBody LostItemRequestDto requestDto, @RequestParam Long memberId) {
        return lostItemService.createLostItem(requestDto, memberId);
    }

    @GetMapping("/lost/items")
    public List<LostItem> getLostItems() {
        return lostItemService.getLostItems();
    }

    @PutMapping("/lost/items/{lostId}")
    public LostItem updateLostItem(@PathVariable Long lostId, @RequestParam Long memberId,
                                   @RequestBody LostItemRequestDto requestDto) {

        return lostItemService.updateLostItem(lostId, memberId, requestDto);
    }

    @GetMapping("/lost/items/{lostId}")
    public LostItem getLostItem(@PathVariable Long lostId) {
        return lostItemService.getLostItem(lostId);
    }

    @DeleteMapping("/lost/items/{lostId}")
    public void deleteLostItem(@PathVariable Long lostId, @RequestParam Long memberId) {
        lostItemService.deleteLostItem(lostId, memberId);
    }

}
