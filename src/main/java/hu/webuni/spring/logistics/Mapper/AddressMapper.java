package hu.webuni.spring.logistics.Mapper;

import hu.webuni.spring.logistics.dto.AddressDto;
import hu.webuni.spring.logistics.model.Address;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "milestone.address", ignore = true)
    @Named("summary")
    Address addressDtoToAddress(AddressDto addressDto);
    @Mapping(target = "milestone.address", ignore = true)
    @Named("sum")
    AddressDto addressToDto (Address address);
    @IterableMapping(qualifiedByName = "sum")
    List<AddressDto> addressesToAddressDtos(List<Address> addresses);

    List<Address> addressDtosToAddresses(List<AddressDto> addressDtos);


}
