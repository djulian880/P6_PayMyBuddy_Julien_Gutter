package com.openclassrooms.paymybuddyjg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.openclassrooms.paymybuddyjg.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

}



