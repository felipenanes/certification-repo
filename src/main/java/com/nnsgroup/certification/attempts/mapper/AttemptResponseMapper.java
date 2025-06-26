package com.nnsgroup.certification.attempts.mapper;

import com.nnsgroup.certification.attempts.domain.Attempt;
import com.nnsgroup.certification.attempts.web.response.AttemptResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttemptResponseMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "provider.name", target = "provider")
    @Mapping(source = "document.name", target = "document")
    AttemptResponseDTO toDto(Attempt attempt);
}