
package com.aishwarya.splittracker.repository;

import com.aishwarya.splittracker.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByGroup_Id(Long groupId);
}
