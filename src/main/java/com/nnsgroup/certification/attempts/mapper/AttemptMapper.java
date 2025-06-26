package com.nnsgroup.certification.attempts.mapper;

import com.nnsgroup.certification.attempts.domain.Attempt;
import com.nnsgroup.certification.attempts.web.request.AttemptRequestDTO;
import com.nnsgroup.certification.documents.domain.Document;
import com.nnsgroup.certification.providers.domain.Provider;
import com.nnsgroup.certification.users.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttemptMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "provider", source = "provider")
    @Mapping(target = "document", source = "document")
    Attempt toEntity(AttemptRequestDTO dto, User user, Provider provider, Document document);
}