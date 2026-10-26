
package com.aishwarya.splittracker.service;

import com.aishwarya.splittracker.entity.ExpenseGroup;
import com.aishwarya.splittracker.repository.ExpenseGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseGroupService {

    private final ExpenseGroupRepository repository;

    public ExpenseGroupService(ExpenseGroupRepository repository) {
        this.repository = repository;
    }

    public ExpenseGroup createGroup(String name) {
        ExpenseGroup group = new ExpenseGroup(name);
        return repository.save(group);
    }

    public List<ExpenseGroup> getAllGroups() {
        return repository.findAll();
    }
}
