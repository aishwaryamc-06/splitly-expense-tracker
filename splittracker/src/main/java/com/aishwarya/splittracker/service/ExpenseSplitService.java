
package com.aishwarya.splittracker.service;

import com.aishwarya.splittracker.entity.Expense;
import com.aishwarya.splittracker.entity.ExpenseSplit;
import com.aishwarya.splittracker.entity.Member;
import com.aishwarya.splittracker.repository.ExpenseRepository;
import com.aishwarya.splittracker.repository.ExpenseSplitRepository;
import com.aishwarya.splittracker.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseSplitService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository splitRepository;
    private final MemberRepository memberRepository;

    public ExpenseSplitService(
            ExpenseRepository expenseRepository,
            ExpenseSplitRepository splitRepository,
            MemberRepository memberRepository) {
        this.expenseRepository = expenseRepository;
        this.splitRepository = splitRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public List<ExpenseSplit> splitExpenseEqually(Long expenseId) {

        // Check whether the expense has already been split
        if (splitRepository.existsByExpense_Id(expenseId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This expense has already been split"
            );
        }

        // Find the expense
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Expense not found"
                ));

        // Get the group associated with the expense
        Long groupId = expense.getGroup().getId();

        // Find all members belonging to that group
        List<Member> members =
                memberRepository.findByGroup_Id(groupId);

        if (members.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot split expense: group has no members"
            );
        }

        // Ensure the expense amount is positive
        if (expense.getAmount() == null
                || expense.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Expense amount must be greater than zero"
            );
        }

        BigDecimal totalAmount = expense.getAmount();

        // Calculate each member's share, rounding down to two decimals
        BigDecimal share = totalAmount.divide(
                BigDecimal.valueOf(members.size()),
                2,
                RoundingMode.DOWN
        );

        List<ExpenseSplit> splits = new ArrayList<>();
        BigDecimal allocated = BigDecimal.ZERO;

        for (int i = 0; i < members.size(); i++) {

            BigDecimal memberShare;

            // Give the last member the remainder to preserve the total
            if (i == members.size() - 1) {
                memberShare = totalAmount.subtract(allocated);
            } else {
                memberShare = share;
                allocated = allocated.add(memberShare);
            }

            ExpenseSplit split = new ExpenseSplit(
                    expense,
                    members.get(i),
                    memberShare
            );

            splits.add(split);
        }

        return splitRepository.saveAll(splits);
    }

    public List<ExpenseSplit> getSplitsForExpense(Long expenseId) {

        // Return 404 if the expense does not exist
        if (!expenseRepository.existsById(expenseId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Expense not found"
            );
        }

        return splitRepository.findByExpense_Id(expenseId);
    }
}
