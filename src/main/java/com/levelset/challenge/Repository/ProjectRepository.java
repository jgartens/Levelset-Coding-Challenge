package com.levelset.challenge.Repository;

import java.util.Optional;

import com.levelset.challenge.Model.Project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Optional<Project> findById(Long id);
}
