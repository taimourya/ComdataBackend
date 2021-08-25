package com.comdata.backend.comdatapointage.dao;

import com.comdata.backend.comdatapointage.entity.Temps;
import com.comdata.backend.comdatapointage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
