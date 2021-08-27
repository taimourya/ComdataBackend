package com.comdata.backend.comdatapointage.dao;

import com.comdata.backend.comdatapointage.entity.Inactif;
import com.comdata.backend.comdatapointage.entity.Pause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface PauseRepository extends JpaRepository<Pause, Integer> {
}
