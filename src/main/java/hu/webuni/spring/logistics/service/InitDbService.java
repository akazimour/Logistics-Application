package hu.webuni.spring.logistics.service;

import hu.webuni.spring.logistics.model.*;
import hu.webuni.spring.logistics.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Service
public class InitDbService {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    MilestoneRepository milestoneRepository;
    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    TransportPlanRepository transportPlanRepository;
    @Autowired
    SectionPropertiesRepository sectionPropertiesRepository;

    @Autowired
    LogisticsService logisticsService;

    @Transactional
    public void insertAddress(){
        Address Székesfehérvár = new Address(1L,"HU","Székesfehérvár","József Attila utca","8000","2/C 6/3",18.4137d, 47.1888d);
        Address Debrecen = new Address(2L,"HU","Debrecen","Budai út","4000","2/C 6/3",19.0402349d, 47.497912d);
        Address Győr = new Address(3L,"HU","Győr","Audi utca","9028","15-25/a",17.6350d, 47.6839d);
        Address Kecskemét = new Address(4L,"HU","Kecskemét","Mercedes utca","6000","2-25/c",19.72045d,46.8992d);
        Address Miskolc = new Address(5L,"HU","Miskilc","Alkotás út","3529","9-15",16.72045d,48.8992d);
        Address Budapest = new Address(6L,"HU","Budapest","Doberdó út","1037","10-15",19.028637d,47.538068d);
        Address Budapest2 = new Address(7L,"HU","Budapest","Kiscelli út","1037","10-15",19.028637d,47.538068d);
        Address Székesfehérvár2 = new Address(8L,"HU","Székesfehérvár","Sarló utca","8000","4 10/61",18.4137d, 47.1888d);


        Milestone loading = new Milestone(1L, LocalDateTime.of(2022,12,06,10,12));
        Milestone dropping = new Milestone(2L, LocalDateTime.of(2022,12,24,14,20));
        Milestone test1 = new Milestone(3L, LocalDateTime.of(2022,10,06,13,11));
        Milestone test2 = new Milestone(4L, LocalDateTime.of(2022,12,24,9,15));
        Section first = new Section(1L,1);
        Section second = new Section(2L,2);
        SectionProperties props = new SectionProperties(1L,loading,dropping);
        SectionProperties props2 = new SectionProperties(2L,test1,test2);
        TransportPlan transportPlan = new TransportPlan(1L);
        transportPlan.setProfit(1000000);
        transportPlanRepository.save(transportPlan);

        first.setTPlan(transportPlan);
        transportPlan.addSection(first);
        second.setTPlan(transportPlan);
        transportPlan.addSection(second);
        loading.setAddress(Székesfehérvár);
        test1.setAddress(Győr);
        test2.setAddress(Kecskemét);
        dropping.setAddress(Debrecen);
        loading.setSection(first);
        dropping.setSection(first);
        test1.setSection(second);
        test2.setSection(second);
        sectionRepository.save(first);
        sectionRepository.save(second);
        first.setSectionProperties(props);
        props.addPropToSection(first);
        second.setSectionProperties(props2);
        props2.addPropToSection(second);
        logisticsService.simpleSave(Székesfehérvár);
        logisticsService.simpleSave(Debrecen);
        logisticsService.simpleSave(Győr);
        logisticsService.simpleSave(Kecskemét);
        logisticsService.simpleSave(Miskolc);
        logisticsService.simpleSave(Budapest);
        logisticsService.simpleSave(Budapest2);
        logisticsService.simpleSave(Székesfehérvár2);
        Székesfehérvár.addMilestoneToAddress(loading);
        Debrecen.addMilestoneToAddress(dropping);
        Győr.addMilestoneToAddress(test1);
        Kecskemét.addMilestoneToAddress(test2);
        milestoneRepository.save(loading);
        milestoneRepository.save(dropping);
        milestoneRepository.save(test1);
        milestoneRepository.save(test2);
        sectionPropertiesRepository.save(props);
        sectionPropertiesRepository.save(props2);
        sectionRepository.save(first);
        sectionRepository.save(second);


    }

}
