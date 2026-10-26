
package com.aishwarya.splittracker.repository;

import com.aishwarya.splittracker.entity.ExpenseGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseGroupRepository
        extends JpaRepository<ExpenseGroup, Long> {
}
