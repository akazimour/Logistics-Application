package hu.webuni.spring.logistics.dto;

import hu.webuni.spring.logistics.model.Section;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

public class TransportPlanDto {


    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "tPlan")
    private List<Section> sections;
    private int profit;

    public TransportPlanDto() {
    }

    public TransportPlanDto(Long id, List<Section> sections) {
        this.id = id;
        this.sections = sections;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }


}
