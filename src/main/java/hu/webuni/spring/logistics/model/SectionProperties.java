package hu.webuni.spring.logistics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
public class SectionProperties {
@Id
@GeneratedValue
    private Long id;

    @ManyToOne
    private Milestone fromMilestone;

    @ManyToOne
    private Milestone toMilestone;
    @OneToMany(mappedBy = "sectionProperties")
    private List<Section> section;

    public SectionProperties() {
    }

    public SectionProperties(Long id, Milestone fromMilestone, Milestone toMilestone) {
        this.id = id;
        this.fromMilestone = fromMilestone;
        this.toMilestone = toMilestone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Milestone getFromMilestone() {
        return fromMilestone;
    }

    public void setFromMilestone(Milestone fromMilestone) {
        this.fromMilestone = fromMilestone;
    }

    public Milestone getToMilestone() {
        return toMilestone;
    }

    public void setToMilestone(Milestone toMilestone) {
        this.toMilestone = toMilestone;
    }

    public List<Section> getSection() {
        return section;
    }

    public void setSection(List<Section> section) {
        this.section = section;
    }

    public void addPropToSection(Section addedSection) {
        if (this.section == null)
            this.section = new ArrayList<>();
        addedSection.setSectionProperties(this);
        this.section.add(addedSection);
    }
}
