package com.comdata.backend.comdatapointage.dao;

import com.comdata.backend.comdatapointage.entity.Actif;
import com.comdata.backend.comdatapointage.entity.Parametrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametrageRepository extends JpaRepository<Parametrage, Integer> {
}
