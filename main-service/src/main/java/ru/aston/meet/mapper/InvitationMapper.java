package ru.aston.meet.mapper;

import org.mapstruct.*;
import ru.aston.meet.dto.InvitationDto;
import ru.aston.meet.dto.UpdateInvitationStatusDto;
import ru.aston.meet.mapper.impl.InvitationMapperDecorator;
import ru.aston.meet.model.Invitation;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@DecoratedWith(InvitationMapperDecorator.class)
public interface InvitationMapper {

    @Mapping(target = "meetingId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(source = "invitation.id", target = "id")
    @Mapping(source = "invitation.status", target = "status")
    InvitationDto toInvitationDto(Invitation invitation);

    Invitation mapInvitationToUpdate(UpdateInvitationStatusDto updateInvitationStatusDto, @MappingTarget Invitation invitation);
}
