package za.ac.ladzani.uptimeMonitor.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import za.ac.ladzani.uptimeMonitor.domain.dtos.PingLogDto;
import za.ac.ladzani.uptimeMonitor.domain.entity.PingLog;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PingLogMapper {

    PingLogDto toDto(PingLog pingLog);
    PingLog toEntity(PingLogDto pingLogDto);
}
