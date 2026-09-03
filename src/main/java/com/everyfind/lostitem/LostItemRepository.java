package com.everyfind.lostitem;

import com.everyfind.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {
    List<LostItem> findByMemberSchoolId(Long schoolId);
}
