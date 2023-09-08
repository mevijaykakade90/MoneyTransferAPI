package com.moneyapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moneyapi.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
