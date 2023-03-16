package hu.webuni.spring.logistics.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Milestone{

    @Id
    @GeneratedValue
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime plannedTime;
    @JsonIgnore
    @ManyToOne()
    private Address address;

    @ManyToOne
    private Section section;


    public Milestone() {
    }

    public Milestone(Long id, LocalDateTime plannedTime ) {
        this.id = id;
        this.plannedTime = plannedTime;
    }

    public Milestone (Long id, LocalDateTime plannedTime, Address address, Section section ){
        this.id = id;
        this.plannedTime = plannedTime;
        this.address = address;
        this.section = section;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(LocalDateTime plannedTime) {
        this.plannedTime = plannedTime;
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
