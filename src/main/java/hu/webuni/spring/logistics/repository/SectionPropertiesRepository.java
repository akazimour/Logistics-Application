package hu.webuni.spring.logistics.repository;

import hu.webuni.spring.logistics.model.SectionProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionPropertiesRepository extends JpaRepository<SectionProperties,Long> {

/* @Query(" SELECT DISTINCT s FROM section_properties s WHERE s.fromMilestone.id =:id ")*/
 List<SectionProperties> findByFromMilestoneId(Long id);
 List<SectionProperties> findByToMilestoneId(Long id);


}
