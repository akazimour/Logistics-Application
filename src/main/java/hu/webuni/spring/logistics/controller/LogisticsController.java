package hu.webuni.spring.logistics.controller;

import hu.webuni.spring.logistics.Mapper.AddressMapper;
import hu.webuni.spring.logistics.Mapper.MilestoneMapper;
import hu.webuni.spring.logistics.dto.AddressDto;
import hu.webuni.spring.logistics.dto.MilestoneDto;
import hu.webuni.spring.logistics.model.Address;
import hu.webuni.spring.logistics.service.LogisticsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/addresses")
public class LogisticsController {

@Autowired
LogisticsService logisticsService;
@Autowired
AddressMapper addressMapper;

@Autowired
MilestoneMapper milestoneMapper;

//Address Api endpoints

/*Example: Post Address
    {
            "countryIsoCode": "HU",
            "city": "Győr",
            "street": "Audi utca",
            "zipCode": "9028",
            "streetAddress": "15-25/a",
            "longitude": 17.635,
            "latitude": 47.6839,
            "milestone": []
    }
    */

@PostMapping
    public ResponseEntity<AddressDto> postNewAddress(@RequestBody @Valid AddressDto addressDto){
    logisticsService.checkUniqueId(addressMapper.addressDtoToAddress(addressDto));
    Address address = logisticsService.clearSave(addressMapper.addressDtoToAddress(addressDto));
return ResponseEntity.ok(addressMapper.addressToDto(address));

}
@GetMapping
    public List<AddressDto> getAllAddresses(){
    return addressMapper.addressesToAddressDtos(logisticsService.findAllAddresses());
}
    @GetMapping("/{id}")
    public AddressDto getAddressesById(@PathVariable Long id){
        return addressMapper.addressToDto(logisticsService.findAddressById(id));
    }
    @DeleteMapping("/{id}")
    public void deleteAddressById(@PathVariable Long id){
         logisticsService.deleteAddress(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> modifyEmployee(@RequestBody @Valid AddressDto addressDto, @PathVariable Long id) {
        Address byId = logisticsService.findAddressById(id);
        if (byId == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " Address does not exist with id: " + id);
        }
        if (byId != null && addressDto.getId() != id) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided Id-s are not same: " + addressDto.getId() + " and " + id);
        }
        if (byId != null) {
            Address address = logisticsService.clearSave(addressMapper.addressDtoToAddress(addressDto));
            return ResponseEntity.ok(addressMapper.addressToDto(address));
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " Employee does not exist with id: " + id);
    }

    //Dynamic search with Post, Pageable and Header

   @PostMapping("/search")
    public ResponseEntity<?> searchBySpecifications(@RequestBody @Valid AddressDto addressDto,@SortDefault(sort = "id",direction = Sort.Direction.ASC) Pageable pageable){
        Page<Address> addressByExample = logisticsService.findAddressByExample(addressMapper.addressDtoToAddress(addressDto),pageable);
        List<AddressDto> body = addressMapper.addressesToAddressDtos(addressByExample.getContent());
        Long Count = addressByExample.getTotalElements();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Tolal-Count", String.valueOf(Count));
        return ResponseEntity.ok().headers(headers).body(addressByExample.getContent());

    }

    /*Registering delay with a milestone and with added minutes to plannedTime +30, 60, 120 min
   use this url http://localhost:8080/api/addresses/api/transportPlans/1/delay

    Example:
    {
        "id": 1,
            "plannedTime": "2022-12-24T15:20:00",
            "address": null,
            "section": null
    }*/
    @PostMapping("/api/transportPlans/{id}/delay")
    public void addDelayToPlannedTime (@PathVariable Long id, @RequestBody MilestoneDto milestoneDto){
    logisticsService.addDelay(id,milestoneMapper.milestoneDtoToMilestone(milestoneDto));
}
}
