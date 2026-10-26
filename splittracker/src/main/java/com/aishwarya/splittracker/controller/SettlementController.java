
package com.aishwarya.splittracker.controller;

import com.aishwarya.splittracker.Settlement;
import com.aishwarya.splittracker.service.SettlementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class SettlementController {

    private final SettlementService service;

    public SettlementController(SettlementService service) {
        this.service = service;
    }

    @GetMapping("/{groupId}/settlements")
    public List<Settlement> getSettlements(
            @PathVariable Long groupId) {

        return service.calculateSettlements(groupId);
    }
}
