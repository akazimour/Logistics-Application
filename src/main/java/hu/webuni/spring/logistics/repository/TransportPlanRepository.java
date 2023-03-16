package hu.webuni.spring.logistics.repository;

import hu.webuni.spring.logistics.model.TransportPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransportPlanRepository extends JpaRepository<TransportPlan,Long> {

 /*  @Query("SELECT DISTINCT t FROM transport_plan t "
            + "INNER Join t.sections s "
            + "WHERE t.id= :tPlanId "
            + "AND s.fromMilestone.id= :id "
            + "OR s.fromMilestone.id= :id "
    )
    public List<TransportPlan>findByIdAndSection(Long tPlanId);*/
}
