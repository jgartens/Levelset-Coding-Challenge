package com.levelset.challenge.Repository;

import java.util.List;
import java.util.Optional;

import com.levelset.challenge.Model.Project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findAll();
    Optional<Project> findById(Long id);
    List<Project> findByIdIn(List<Long> ids); 
    List<Project> findByCustomerName(String customerName);
}
