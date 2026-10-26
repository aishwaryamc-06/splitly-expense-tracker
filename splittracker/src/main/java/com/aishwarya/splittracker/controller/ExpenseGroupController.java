
package com.aishwarya.splittracker.controller;

import com.aishwarya.splittracker.entity.ExpenseGroup;
import com.aishwarya.splittracker.service.ExpenseGroupService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;


import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class ExpenseGroupController {

    private final ExpenseGroupService service;

    public ExpenseGroupController(ExpenseGroupService service) {
        this.service = service;
    }

    @PostMapping
    public ExpenseGroup createGroup(
            @Valid @RequestBody CreateGroupRequest request) {
        return service.createGroup(request.name());
    }

    @GetMapping
    public List<ExpenseGroup> getAllGroups() {
        return service.getAllGroups();
    }

    public record CreateGroupRequest(
            @NotBlank(message = "Group name is required")
            String name) {
    }
}
