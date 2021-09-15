package com.comdata.backend.comdatapointage.dao;

import com.comdata.backend.comdatapointage.entity.Collaborateur;
import com.comdata.backend.comdatapointage.entity.Inactif;
import com.comdata.backend.comdatapointage.entity.Temps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RepositoryRestResource
public interface InactifRepository extends JpaRepository<Inactif, Integer> {

    @Query("SELECT a from Inactif a WHERE a.collaborateur = ?1 AND a.date >= ?2 ORDER BY a.date")
    List<Temps> findByAfterDate(Collaborateur collaborateur, Date from);


    @Query("SELECT a from Inactif a WHERE a.collaborateur = ?1 AND a.date BETWEEN ?2 AND ?3 ORDER BY a.date")
    List<Temps> findByBetweenDate(Collaborateur collaborateur, Date from, Date to);
}
