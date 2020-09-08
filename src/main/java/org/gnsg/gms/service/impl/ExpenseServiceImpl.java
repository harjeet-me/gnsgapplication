package org.gnsg.gms.service.impl;

import org.gnsg.gms.service.ExpenseService;
import org.gnsg.gms.domain.Expense;
import org.gnsg.gms.repository.ExpenseRepository;
import org.gnsg.gms.repository.search.ExpenseSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Expense}.
 */
@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final Logger log = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    private final ExpenseRepository expenseRepository;

    private final ExpenseSearchRepository expenseSearchRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ExpenseSearchRepository expenseSearchRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseSearchRepository = expenseSearchRepository;
    }

    @Override
    public Expense save(Expense expense) {
        log.debug("Request to save Expense : {}", expense);
        Expense result = expenseRepository.save(expense);
        expenseSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> findAll() {
        log.debug("Request to get all Expenses");
        return expenseRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Expense> findOne(Long id) {
        log.debug("Request to get Expense : {}", id);
        return expenseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Expense : {}", id);
        expenseRepository.deleteById(id);
        expenseSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> search(String query) {
        log.debug("Request to search Expenses for query {}", query);
        return StreamSupport
            .stream(expenseSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
