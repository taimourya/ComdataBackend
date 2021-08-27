package com.comdata.backend.comdatapointage.dao;

import com.comdata.backend.comdatapointage.entity.Pause;
import com.comdata.backend.comdatapointage.entity.Temps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface TempsRepository extends JpaRepository<Temps, Integer> {
}
