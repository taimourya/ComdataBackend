package com.comdata.backend.comdatapointage.dao;

import com.comdata.backend.comdatapointage.entity.Temps;
import com.comdata.backend.comdatapointage.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u " +
            "WHERE u.cin LIKE %?1% " +
            "OR u.matricule LIKE %?1% " +
            "OR u.adresse LIKE %?1% " +
            "OR u.firstname LIKE %?1% " +
            "OR u.lastname LIKE %?1%")
    Page<User> findByMc(String mc, Pageable pageable);

    @Query("SELECT u FROM User u " +
            "WHERE (" +
            "u.cin LIKE %?1% " +
            "OR u.matricule LIKE %?1% " +
            "OR u.adresse LIKE %?1% " +
            "OR u.firstname LIKE %?1% " +
            "OR u.lastname LIKE %?1%" +
            ")" +
            "AND u.isActive = ?2")
    Page<User> findByMcAndEtatCmpt(String mc, boolean etat, Pageable pageable);

}
