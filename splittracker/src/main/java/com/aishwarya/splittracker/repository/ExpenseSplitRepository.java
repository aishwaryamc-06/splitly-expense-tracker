
package com.aishwarya.splittracker.repository;

import com.aishwarya.splittracker.entity.ExpenseSplit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseSplitRepository
        extends JpaRepository<ExpenseSplit, Long> {

    List<ExpenseSplit> findByExpense_Id(Long expenseId);

    boolean existsByExpense_Id(Long expenseId);
}
