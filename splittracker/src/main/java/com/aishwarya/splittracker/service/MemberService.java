
package com.aishwarya.splittracker.service;

import com.aishwarya.splittracker.entity.ExpenseGroup;
import com.aishwarya.splittracker.entity.Member;
import com.aishwarya.splittracker.repository.ExpenseGroupRepository;
import com.aishwarya.splittracker.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ExpenseGroupRepository groupRepository;

    public MemberService(
            MemberRepository memberRepository,
            ExpenseGroupRepository groupRepository) {
        this.memberRepository = memberRepository;
        this.groupRepository = groupRepository;
    }

    public Member createMember(
            String name, String email, Long groupId) {

        ExpenseGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Group not found"
                ));

        Member member = new Member(name, email);
        member.setGroup(group);

        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}
