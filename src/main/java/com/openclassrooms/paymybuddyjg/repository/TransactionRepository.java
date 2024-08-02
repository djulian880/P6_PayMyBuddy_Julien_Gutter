package com.openclassrooms.paymybuddyjg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.openclassrooms.paymybuddyjg.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

}



