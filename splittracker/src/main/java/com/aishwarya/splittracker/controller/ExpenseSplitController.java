
package com.aishwarya.splittracker.controller;

import com.aishwarya.splittracker.entity.ExpenseSplit;
import com.aishwarya.splittracker.service.ExpenseSplitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense-splits")
public class ExpenseSplitController {

    private final ExpenseSplitService service;

    public ExpenseSplitController(ExpenseSplitService service) {
        this.service = service;
    }

    @PostMapping("/{expenseId}")
    public List<ExpenseSplit> splitExpense(
            @PathVariable Long expenseId) {
        return service.splitExpenseEqually(expenseId);
    }

    @GetMapping("/{expenseId}")
    public List<ExpenseSplit> getSplits(
            @PathVariable Long expenseId) {
        return service.getSplitsForExpense(expenseId);
    }
}
