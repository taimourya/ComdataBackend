package com.comdata.backend.comdatapointage.dao;

import com.comdata.backend.comdatapointage.entity.Parametrage;
import com.comdata.backend.comdatapointage.entity.TypePause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface TypePauseRepository extends JpaRepository<TypePause, Integer> {
}
