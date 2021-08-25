package com.comdata.backend.comdatapointage.dao;

import com.comdata.backend.comdatapointage.entity.Actif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActifRepository extends JpaRepository<Actif, Integer> {
}
