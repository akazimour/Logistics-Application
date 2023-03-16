package hu.webuni.spring.logistics.service;
import hu.webuni.spring.logistics.Mapper.AddressMapper;
import hu.webuni.spring.logistics.configuration.DelayConfiguration;
import hu.webuni.spring.logistics.dto.AddressDto;
import hu.webuni.spring.logistics.model.*;
import hu.webuni.spring.logistics.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class LogisticsService {

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
DelayConfiguration delayConfiguration;
@Autowired
DelayService delayService;

    public LogisticsService(DelayConfiguration delayConfiguration) {
        this.delayConfiguration = delayConfiguration;
    }

    public Address addNewAddress(Address address){
     return clearSave(address);

}
    public Address clearSave(Address address) {
      address.setMilestone(null);
        return addressRepository.save(address);
    }

    public Address simpleSave(Address address) {
        return addressRepository.save(address);
    }

    public void checkUniqueId(Address address) {
        if (address.getId() != null) {
            Optional<Address> addressDtoWithSameId = addressRepository.findById(address.getId());
            if (addressDtoWithSameId.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided Id is already exist: " + address.getId());
        } if (address.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Body was sent with filled id: " + address.getId());
        }
    }

    public List<Address>findAllAddresses(){
    return addressRepository.findAll();
    }

    public Address findAddressById(Long id){
        return addressRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, " Address does not exist with id: " + id));
    }

    public void deleteAddress(Long id){
    addressRepository.deleteById(id);
    }

    public LocalDateTime saveBeforeState(){ Optional<Milestone> byId = milestoneRepository.findById(1L);
        Milestone milestoneBef= byId.get();
        LocalDateTime plannedTimeBefore = milestoneBef.getPlannedTime();
        System.out.println(plannedTimeBefore);
        return plannedTimeBefore;
    }
    public LocalDateTime saveAfterState(){ Optional<Milestone> byIdAfter = milestoneRepository.findById(1L);
        Milestone milestoneAft= byIdAfter.get();
        LocalDateTime plannedTimeAft = milestoneAft.getPlannedTime();
        System.out.println(plannedTimeAft);
        return plannedTimeAft;
    }

    public int getFinalProfit(Long id){
        Optional<TransportPlan> byId = transportPlanRepository.findById(id);
        if(byId.isPresent()){
            TransportPlan transportPlan = byId.get();
           return transportPlan.getProfit();
        }   else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transport Plan does not exist with : " + id);


}

@Transactional
   public Milestone addDelay(Long tPlanId, Milestone milestone) {
       Optional<TransportPlan> byId = transportPlanRepository.findById(tPlanId);
       if (byId.isPresent()) {
           TransportPlan transportPlan = byId.get();
           List<SectionProperties> byFromMilestoneId = sectionPropertiesRepository.findByFromMilestoneId(milestone.getId());
           if (!byFromMilestoneId.isEmpty()) {
               byFromMilestoneId.stream().forEach(m -> {
                   Milestone fromMilestone = m.getFromMilestone();
                   long minutes = ChronoUnit.MINUTES.between(fromMilestone.getPlannedTime(), milestone.getPlannedTime());
                   if ( minutes >= 30){
                       int finalProfit = delayService.getFinalProfit(transportPlan.getProfit(), minutes);
                       transportPlan.setProfit(finalProfit);
                   }
                   transportPlan.getSections().forEach(section -> {
                       if (section.getSectionProperties().getFromMilestone().getId().equals(fromMilestone.getId())) {
                           Milestone toMilestone = fromMilestone.getSection().getSectionProperties().getToMilestone();
                           fromMilestone.setPlannedTime(milestone.getPlannedTime());
                           LocalDateTime plannedTime = toMilestone.getPlannedTime();
                           plannedTime = plannedTime.plusMinutes(minutes);
                           toMilestone.setPlannedTime(plannedTime);

                       }
                   });
                   });
           }else {
               List<SectionProperties> byToMilestoneId = sectionPropertiesRepository.findByToMilestoneId(milestone.getId());
               if(!byToMilestoneId.isEmpty()){

                   byToMilestoneId.stream().forEach(tm->{
                       Milestone toMilestone = tm.getToMilestone();
                       transportPlan.getSections().forEach(section ->{
                           if (section.getSectionProperties().getToMilestone().getId().equals(toMilestone.getId())) {
                               long minutes = ChronoUnit.MINUTES.between(toMilestone.getPlannedTime(), milestone.getPlannedTime());
                               if ( minutes >= 30){
                                   int finalProfit =  delayService.getFinalProfit(transportPlan.getProfit(),minutes);
                                   transportPlan.setProfit(finalProfit);
                               }
                               toMilestone.setPlannedTime(milestone.getPlannedTime());
                               int number = toMilestone.getSection().getNumber();
                               number++;
                               List<Section> sectionByNumber = sectionRepository.findByNumber(number);
                               if(!sectionByNumber.isEmpty()){
                                   sectionByNumber.forEach(snext-> {
                                       int sNumber = snext.getNumber();
                                       transportPlan.getSections().forEach(sect->{if(sect.getNumber()==sNumber){
                                           sectionByNumber.forEach(sprop-> {
                                               Milestone fromMilestone2 = sprop.getSectionProperties().getFromMilestone();
                                               LocalDateTime plannedTime2 = fromMilestone2.getPlannedTime();
                                               plannedTime2= plannedTime2.plusMinutes(minutes);
                                               fromMilestone2.setPlannedTime(plannedTime2);

                                           });
                                       }
                                   });
                               });
                               }
                           }
                       });
                   });
               }  else {
                   Optional byId1 = milestoneRepository.findById(milestone.getId());
                   if (byId1.isPresent()){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "milestone does exist with id : " + milestone.getId()+" but not part of the actual Transport Plan with id: "+tPlanId);
                   }  else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "milestone does not exist with id : " + milestone.getId());
               }
           }
       }  else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "transportPlan does not exist with id : " + tPlanId);

    return milestone;
}

   public Page<Address> findAddressByExample(Address example, Pageable pageable) {
        String countryIsoCode = example.getCountryIsoCode();
        String city = example.getCity();
        String zipCode = example.getZipCode();
        String street = example.getStreet();

        Specification <Address> spec = Specification.where(null);

        if (StringUtils.hasText(countryIsoCode)) {
            spec = spec.and(AddressSpecifications.hasCountryName(countryIsoCode));
        }
        if (StringUtils.hasText(zipCode)) {
            spec = spec.and(AddressSpecifications.hasZipCode(zipCode));
        }
        if (StringUtils.hasText(city)){
            spec = spec.and(AddressSpecifications.hasCity(city));
        }
        if (StringUtils.hasText(street)){
            spec = spec.and(AddressSpecifications.hasStreet(street));
        }
return addressRepository.findAll(spec,pageable);
    }


}
