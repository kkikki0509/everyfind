package com.everyfind.lostitem;

import com.everyfind.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {
}
