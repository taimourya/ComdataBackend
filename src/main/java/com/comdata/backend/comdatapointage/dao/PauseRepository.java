package com.comdata.backend.comdatapointage.dao;

import com.comdata.backend.comdatapointage.entity.Inactif;
import com.comdata.backend.comdatapointage.entity.Pause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PauseRepository extends JpaRepository<Pause, Integer> {
}
