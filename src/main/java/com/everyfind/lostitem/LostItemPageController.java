package com.everyfind.lostitem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LostItemPageController {

    @GetMapping("/lost/items/new")
    public String lostItemPage() {
        return "lost-item-form";
    }

    @GetMapping("/lost/items/{lostId}/page")
    public String lostItemDetailPage() {
        return "lost-detail";
    }
}