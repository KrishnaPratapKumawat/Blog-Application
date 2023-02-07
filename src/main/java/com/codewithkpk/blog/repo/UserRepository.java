package com.codewithkpk.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithkpk.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}