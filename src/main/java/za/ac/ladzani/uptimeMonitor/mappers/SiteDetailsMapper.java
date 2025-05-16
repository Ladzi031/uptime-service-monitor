package za.ac.ladzani.uptimeMonitor.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import za.ac.ladzani.uptimeMonitor.domain.RegisterServiceRequest;
import za.ac.ladzani.uptimeMonitor.domain.dtos.SiteDetailsDto;
import za.ac.ladzani.uptimeMonitor.domain.entity.SiteDetails;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SiteDetailsMapper {

   SiteDetails toEntity(RegisterServiceRequest registerServiceRequest);
   SiteDetails toEntity(SiteDetailsDto siteDetailsDto);
   SiteDetailsDto toDto(SiteDetails siteDetails);
}
