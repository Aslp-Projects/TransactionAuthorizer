package com.project.transaction.authorizer.repositories;

import com.project.transaction.authorizer.dto.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
