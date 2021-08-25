package com.comdata.backend.comdatapointage.dao;

import com.comdata.backend.comdatapointage.entity.Admin;
import com.comdata.backend.comdatapointage.entity.Superviseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperviseurRepository extends JpaRepository<Superviseur, String> {
}
