package com.project.transaction.authorizer.repositories;

import com.project.transaction.authorizer.dto.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query("SELECT MAX(t) FROM Transaction t WHERE t.accountId =:id")
    Transaction findLastTransaction(@Param("id") long id);

    @Query(value = "SELECT * FROM Transaction t WHERE t.account_id =:id ORDER BY t.created_at ASC LIMIT 3", nativeQuery = true)
    List<Transaction> findLastTransactions(@Param("id") long id);
}
