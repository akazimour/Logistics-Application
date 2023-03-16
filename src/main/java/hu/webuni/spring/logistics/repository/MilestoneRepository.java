package hu.webuni.spring.logistics.repository;

import hu.webuni.spring.logistics.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {


}
