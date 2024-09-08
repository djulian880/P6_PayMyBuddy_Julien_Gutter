package com.openclassrooms.paymybuddyjg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddyjg.model.Connection;

@Repository
public interface ConnectionRepository extends CrudRepository<Connection, Integer> {

}



