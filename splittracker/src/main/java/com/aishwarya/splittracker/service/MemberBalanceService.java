package com.aishwarya.splittracker.service;

import com.aishwarya.splittracker.MemberBalance;
import com.aishwarya.splittracker.entity.Expense;
import com.aishwarya.splittracker.entity.ExpenseSplit;
import com.aishwarya.splittracker.entity.Member;
import com.aishwarya.splittracker.repository.ExpenseGroupRepository;
import com.aishwarya.splittracker.repository.ExpenseRepository;
import com.aishwarya.splittracker.repository.ExpenseSplitRepository;
import com.aishwarya.splittracker.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberBalanceService {

    private final ExpenseGroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository splitRepository;

    public MemberBalanceService(
            ExpenseGroupRepository groupRepository,
            MemberRepository memberRepository,
            ExpenseRepository expenseRepository,
            ExpenseSplitRepository splitRepository) {

        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
        this.expenseRepository = expenseRepository;
        this.splitRepository = splitRepository;
    }

    public List<MemberBalance> getGroupBalances(Long groupId) {

        // Check whether the group exists.
        if (!groupRepository.existsById(groupId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Group not found"
            );
        }

        List<Member> members =
                memberRepository.findByGroup_Id(groupId);

        List<Expense> expenses =
                expenseRepository.findByGroup_Id(groupId);

        Map<Long, BigDecimal> balances = new HashMap<>();

        // Initialize every member's balance to zero.
        for (Member member : members) {
            balances.put(member.getId(), BigDecimal.ZERO);
        }

        // Calculate balances from expenses that have been split.
        for (Expense expense : expenses) {

            List<ExpenseSplit> splits =
                    splitRepository.findByExpense_Id(expense.getId());

            // Ignore expenses that have not been split yet.
            if (splits.isEmpty()) {
                continue;
            }

            // Add the full expense amount to the payer's balance.
            Long payerId = expense.getPaidBy().getId();

            balances.put(
                    payerId,
                    balances.getOrDefault(
                            payerId, BigDecimal.ZERO
                    ).add(expense.getAmount())
            );

            // Subtract each member's share.
            for (ExpenseSplit split : splits) {

                Long memberId = split.getMember().getId();

                balances.put(
                        memberId,
                        balances.getOrDefault(
                                memberId, BigDecimal.ZERO
                        ).subtract(split.getAmount())
                );
            }
        }

        // Convert the calculated balances into the response list.
        List<MemberBalance> result = new ArrayList<>();

        for (Member member : members) {
            result.add(
                    new MemberBalance(
                            member.getId(),
                            member.getName(),
                            balances.get(member.getId())
                    )
            );
        }

        return result;
    }
}