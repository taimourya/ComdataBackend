package com.comdata.backend.comdatapointage.dao;

import com.comdata.backend.comdatapointage.entity.Collaborateur;
import com.comdata.backend.comdatapointage.entity.Pause;
import com.comdata.backend.comdatapointage.entity.Temps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RepositoryRestResource
public interface PauseRepository extends JpaRepository<Pause, Integer> {

    @Query("SELECT a from Pause a WHERE a.collaborateur = ?1 AND a.date >= ?2 ORDER BY a.date")
    List<Temps> findByAfterDate(Collaborateur collaborateur, Date from);


    @Query("SELECT a from Pause a WHERE a.collaborateur = ?1 AND a.date BETWEEN ?2 AND ?3 ORDER BY a.date")
    List<Temps> findByBetweenDate(Collaborateur collaborateur, Date from, Date to);

}
