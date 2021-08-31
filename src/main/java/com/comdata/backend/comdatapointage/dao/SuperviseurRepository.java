package com.comdata.backend.comdatapointage.dao;

import com.comdata.backend.comdatapointage.entity.Activiter;
import com.comdata.backend.comdatapointage.entity.Admin;
import com.comdata.backend.comdatapointage.entity.Collaborateur;
import com.comdata.backend.comdatapointage.entity.Superviseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface SuperviseurRepository extends JpaRepository<Superviseur, String> {

    @Query("SELECT u FROM Superviseur u " +
            "WHERE u.cin LIKE %?1% " +
            "OR u.matricule LIKE %?1% " +
            "OR u.adresse LIKE %?1% " +
            "OR u.firstname LIKE %?1% " +
            "OR u.lastname LIKE %?1%")
    Page<Superviseur> findByMc(String mc, Pageable pageable);

    @Query("SELECT u FROM Superviseur u " +
            "WHERE (" +
            "u.cin LIKE %?1% " +
            "OR u.matricule LIKE %?1% " +
            "OR u.adresse LIKE %?1% " +
            "OR u.firstname LIKE %?1% " +
            "OR u.lastname LIKE %?1%" +
            ")" +
            "AND u.isActive = ?2")
    Page<Superviseur> findByMcAndEtatCmpt(String mc, boolean etat, Pageable pageable);


    @Query("SELECT u FROM Superviseur u " +
            "WHERE (" +
            "u.cin LIKE %?1% " +
            "OR u.matricule LIKE %?1% " +
            "OR u.adresse LIKE %?1% " +
            "OR u.firstname LIKE %?1% " +
            "OR u.lastname LIKE %?1%" +
            ")" +
            "AND u.activiter = ?2")
    Page<Superviseur> findByMcAndActiviter(String mc, Activiter activiter, Pageable pageable);

    @Query("SELECT u FROM Superviseur u " +
            "WHERE (" +
            "u.cin LIKE %?1% " +
            "OR u.matricule LIKE %?1% " +
            "OR u.adresse LIKE %?1% " +
            "OR u.firstname LIKE %?1% " +
            "OR u.lastname LIKE %?1%" +
            ")" +
            "AND u.activiter = ?2 " +
            "AND u.isActive = ?3")
    Page<Superviseur> findByMcAndEtatCmptAndActiviter(String mc, Activiter activiter, boolean etat, Pageable pageable);

}
