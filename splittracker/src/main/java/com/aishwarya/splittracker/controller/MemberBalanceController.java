
package com.aishwarya.splittracker.controller;

import com.aishwarya.splittracker.MemberBalance;
import com.aishwarya.splittracker.service.MemberBalanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class MemberBalanceController {

    private final MemberBalanceService service;

    public MemberBalanceController(MemberBalanceService service) {
        this.service = service;
    }

    @GetMapping("/{groupId}/balances")
    public List<MemberBalance> getGroupBalances(
            @PathVariable Long groupId) {

        return service.getGroupBalances(groupId);
    }
}
