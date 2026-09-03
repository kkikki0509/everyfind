package com.everyfind.lostitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class LostItemController {
    private final LostItemService lostItemService;

    @Autowired
    public LostItemController(LostItemService lostItemService){
        this.lostItemService = lostItemService;
    }

    @PostMapping("/lost/items")
    public LostItemMatchResponseDto createLostItem(@RequestBody LostItemRequestDto requestDto,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        return lostItemService.createLostItem(requestDto, userDetails.getUsername());
    }

    @GetMapping("/lost/items")
    public List<LostItem> getLostItems(@AuthenticationPrincipal UserDetails userDetails) {

        return lostItemService.getLostItems(userDetails.getUsername());
    }

    @PutMapping("/lost/items/{lostId}")
    public LostItem updateLostItem(@PathVariable Long lostId, @RequestBody LostItemRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        return lostItemService.updateLostItem(lostId, userDetails.getUsername(), requestDto);
    }

    @GetMapping("/lost/items/{lostId}")
    public LostItem getLostItem(@PathVariable Long lostId, @AuthenticationPrincipal UserDetails userDetails) {
        return lostItemService.getLostItem(lostId, userDetails.getUsername());
    }

    @DeleteMapping("/lost/items/{lostId}")
    public void deleteLostItem(@PathVariable Long lostId, @AuthenticationPrincipal UserDetails userDetails) {
        lostItemService.deleteLostItem(lostId, userDetails.getUsername());
    }
}
