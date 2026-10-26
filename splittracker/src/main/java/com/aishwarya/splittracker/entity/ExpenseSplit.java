
package com.aishwarya.splittracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.math.BigDecimal;


@Entity
@Table(
        name = "expense_splits",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"expense_id", "member_id"}
                )
        }
)

public class ExpenseSplit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private BigDecimal amount;

    public ExpenseSplit() {
    }

    public ExpenseSplit(
            Expense expense, Member member, BigDecimal amount) {
        this.expense = expense;
        this.member = member;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Expense getExpense() {
        return expense;
    }

    public Member getMember() {
        return member;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
