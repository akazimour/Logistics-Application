package hu.webuni.spring.logistics.service;

import hu.webuni.spring.logistics.model.Address;
import hu.webuni.spring.logistics.model.Address_;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecifications {


    public static Specification<Address> hasCountryName(String countryIsoCode) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Address_.countryIsoCode), countryIsoCode);
    }
    public static Specification<Address> hasZipCode(String zipCode) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Address_.zipCode), zipCode);
    }
    public static Specification<Address> hasCity(String city) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Address_.city)), (city + "%" ).toLowerCase());
    }
    public static Specification<Address> hasStreet(String street) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Address_.street)), (street + "%" ).toLowerCase());
    }
}
