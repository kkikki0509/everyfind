package com.everyfind.school;

import com.everyfind.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
