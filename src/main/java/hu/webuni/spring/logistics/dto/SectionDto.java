package hu.webuni.spring.logistics.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.webuni.spring.logistics.model.Milestone;
import hu.webuni.spring.logistics.model.TransportPlan;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

public class SectionDto {

    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    @OneToMany(mappedBy = "section")
    private List<Milestone> fromMilestone;
    @JsonIgnore
    @OneToMany(mappedBy = "section")
    private List<Milestone> toMilestone;

    private int number;

    @ManyToOne
    private TransportPlan tPlan;


    public SectionDto() {
    }


    public SectionDto(Long id, int number) {
        this.id = id;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Milestone> getFromMilestone() {
        return fromMilestone;
    }

    public void setFromMilestone(List<Milestone> fromMilestone) {
        this.fromMilestone = fromMilestone;
    }

    public List<Milestone> getToMilestone() {
        return toMilestone;
    }

    public void setToMilestone(List<Milestone> toMilestone) {
        this.toMilestone = toMilestone;
    }

    public TransportPlan gettPlan() {
        return tPlan;
    }

    public void settPlan(TransportPlan tPlan) {
        this.tPlan = tPlan;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
