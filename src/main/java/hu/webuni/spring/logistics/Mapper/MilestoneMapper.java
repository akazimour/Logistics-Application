package hu.webuni.spring.logistics.Mapper;

import hu.webuni.spring.logistics.dto.AddressDto;
import hu.webuni.spring.logistics.dto.MilestoneDto;
import hu.webuni.spring.logistics.model.Address;
import hu.webuni.spring.logistics.model.Milestone;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MilestoneMapper {

    Milestone milestoneDtoToMilestone(MilestoneDto milestoneDto);

    MilestoneDto milestoneToDto (Milestone milestone);

    List<MilestoneDto> milestonesToMilestoneDtos(List<Milestone> milestones);

}
