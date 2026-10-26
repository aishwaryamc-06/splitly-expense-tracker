
package com.aishwarya.splittracker.service;

import com.aishwarya.splittracker.entity.Expense;
import com.aishwarya.splittracker.entity.ExpenseGroup;
import com.aishwarya.splittracker.entity.Member;
import com.aishwarya.splittracker.repository.ExpenseGroupRepository;
import com.aishwarya.splittracker.repository.ExpenseRepository;
import com.aishwarya.splittracker.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseGroupRepository groupRepository;
    private final MemberRepository memberRepository;

    public ExpenseService(
            ExpenseRepository expenseRepository,
            ExpenseGroupRepository groupRepository,
            MemberRepository memberRepository) {
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
    }

    public Expense createExpense(
            String description,
            BigDecimal amount,
            Long groupId,
            Long paidByMemberId) {

        ExpenseGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Group not found"
                ));

        Member paidBy = memberRepository.findById(paidByMemberId)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Member not found"
                ));

        Expense expense = new Expense(description, amount);
        expense.setGroup(group);
        expense.setPaidBy(paidBy);

        return expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByGroup(Long groupId) {
        return expenseRepository.findByGroup_Id(groupId);
    }
}
