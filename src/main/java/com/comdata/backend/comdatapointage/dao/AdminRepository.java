package com.comdata.backend.comdatapointage.dao;

import com.comdata.backend.comdatapointage.entity.Admin;
import com.comdata.backend.comdatapointage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface AdminRepository extends JpaRepository<Admin, String> {
}
