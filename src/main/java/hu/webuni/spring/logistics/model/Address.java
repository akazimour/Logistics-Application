package hu.webuni.spring.logistics.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Address {
@Id
@GeneratedValue
private Long id;
private String countryIsoCode;
private String city;
private String street;
private String zipCode;
private String streetAddress;
private double longitude;
private double latitude;

@OneToMany(mappedBy = "address")
private List<Milestone> milestone;

    public List<Milestone> getMilestone() {
        return milestone;
    }

    public void setMilestone(List<Milestone> milestone) {
        this.milestone = milestone;
    }

    public Address() {
    }

    public Address(Long id, String countryIsoCode, String city, String street, String zipCode, String streetAddress, double longitude, double latitude) {
        this.id = id;
        this.countryIsoCode = countryIsoCode;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.streetAddress = streetAddress;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void addMilestoneToAddress(Milestone addedMilestone) {
        if (this.milestone == null)
            this.milestone = new ArrayList<>();
        addedMilestone.setAddress(this);
        this.milestone.add(addedMilestone);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", countryIsoCode='" + countryIsoCode + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", milestone=" + milestone +
                '}';
    }
}
