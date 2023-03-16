package hu.webuni.spring.logistics.repository;

import hu.webuni.spring.logistics.model.Milestone;
import hu.webuni.spring.logistics.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SectionRepository extends JpaRepository<Section,Long> {

    List<Section> findByNumber(int number);

 /*   @Query("SELECT s.fromMilestone m FROM section s INNER Join s.fromMilestone m WHERE m.id =:id ")
    List<Section> findByMilestone(Long id);*/
}
