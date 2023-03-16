package hu.webuni.spring.logistics.dto;

import hu.webuni.spring.logistics.model.Address;
import hu.webuni.spring.logistics.model.Section;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MilestoneDto {
    @Id
    @GeneratedValue
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime plannedTime;
    @ManyToOne
    private Address address;

    @ManyToOne
    private Section section;


    public MilestoneDto() {
    }

    public MilestoneDto(Long id, LocalDateTime plannedTime) {
        this.id = id;
        this.plannedTime = plannedTime;
    }

    public MilestoneDto(Long id, LocalDateTime plannedTime, Address address, Section section ){
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
