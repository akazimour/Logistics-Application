package hu.webuni.spring.logistics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Employee.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})*/
@Entity
public class Section {
    @Id
    @GeneratedValue
    private Long id;
@JsonIgnore
    @ManyToOne
    private SectionProperties sectionProperties;
    private int number;

    public SectionProperties getSectionProperties() {
        return sectionProperties;
    }

    public void setSectionProperties(SectionProperties sectionProperties) {
        this.sectionProperties = sectionProperties;
    }

    @ManyToOne
    private TransportPlan tPlan;

    public TransportPlan getTPlan() {
        return tPlan;
    }

    public void setTPlan(TransportPlan tPlan) {
        this.tPlan = tPlan;
    }

    public Section() {
    }

    public Section(Long id, int number) {
        this.id = id;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /*public void addFromMilestone(Milestone addedMilestone) {
        if (this.fromMilestone == null)
            this.fromMilestone = new ArrayList<>();
        addedMilestone.setSection(this);
        this.fromMilestone.add(addedMilestone);
    }
    public void addToMilestone(Milestone addedMilestone) {
        if (this.toMilestone == null)
            this.toMilestone = new ArrayList<>();
        addedMilestone.setSection(this);
        this.toMilestone.add(addedMilestone);
    }*/
}
