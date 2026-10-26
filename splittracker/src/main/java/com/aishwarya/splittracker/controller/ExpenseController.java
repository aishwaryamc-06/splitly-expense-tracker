
package com.aishwarya.splittracker.controller;

import com.aishwarya.splittracker.entity.Expense;
import com.aishwarya.splittracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public Expense createExpense(
            @Valid @RequestBody CreateExpenseRequest request) {

        return service.createExpense(
                request.description(),
                request.amount(),
                request.groupId(),
                request.paidByMemberId()
        );
    }

    @GetMapping("/group/{groupId}")
    public List<Expense> getExpensesByGroup(
            @PathVariable Long groupId) {
        return service.getExpensesByGroup(groupId);
    }

    public record CreateExpenseRequest(

            @NotBlank(message = "Expense description is required")
            String description,

            @NotNull(message = "Expense amount is required")
            @DecimalMin(
                    value = "0.01",
                    message = "Expense amount must be greater than zero"
            )
            java.math.BigDecimal amount,

            @NotNull(message = "Group ID is required")
            @Positive(message = "Group ID must be positive")
            Long groupId,

            @NotNull(message = "Payer member ID is required")
            @Positive(message = "Payer member ID must be positive")
            Long paidByMemberId

    ) {
    }
}
