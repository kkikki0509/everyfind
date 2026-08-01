package com.everyfind.founditem;

import com.everyfind.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {
}
