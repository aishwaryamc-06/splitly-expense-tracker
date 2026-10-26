
package com.aishwarya.splittracker.controller;

import com.aishwarya.splittracker.entity.Member;
import com.aishwarya.splittracker.service.MemberService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @PostMapping
    public Member createMember(
            @Valid @RequestBody CreateMemberRequest request) {

        return service.createMember(
                request.name(),
                request.email(),
                request.groupId()
        );
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return service.getAllMembers();
    }

    public record CreateMemberRequest(

            @NotBlank(message = "Member name is required")
            String name,

            @NotBlank(message = "Email is required")
            @Email(message = "Please provide a valid email address")
            String email,

            @NotNull(message = "Group ID is required")
            @Positive(message = "Group ID must be positive")
            Long groupId

    ) {
    }
}
