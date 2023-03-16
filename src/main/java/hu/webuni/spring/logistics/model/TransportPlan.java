package hu.webuni.spring.logistics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
public class TransportPlan {

    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    @OneToMany(mappedBy = "tPlan")
    private List<Section> sections;
    private int profit;

    public TransportPlan() {
    }

    public TransportPlan(Long id) {
        this.id = id;
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

    public void addSection(Section addedSection) {
        if (this.sections == null)
            this.sections = new ArrayList<>();
        addedSection.setTPlan(this);
        this.sections.add(addedSection);
    }
}
