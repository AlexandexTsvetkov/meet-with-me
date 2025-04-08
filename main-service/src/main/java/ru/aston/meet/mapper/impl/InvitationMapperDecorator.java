package ru.aston.meet.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.aston.meet.dto.InvitationDto;
import ru.aston.meet.dto.UpdateInvitationStatusDto;
import ru.aston.meet.mapper.InvitationMapper;
import ru.aston.meet.model.Invitation;

import javax.annotation.processing.Generated;

@Generated(value = "org.mapstruct.ap.MappingProcessor")
public abstract class InvitationMapperDecorator implements InvitationMapper {

    @Autowired
    private InvitationMapper delegate;

    @Override
    public InvitationDto toInvitationDto(Invitation invitation) {

        InvitationDto invitationDto = delegate.toInvitationDto(invitation);
        invitationDto.setUserId(invitation.getInvited().getId());
        invitationDto.setMeetingId(invitation.getMeeting().getId());

        return invitationDto;
    }

    @Override
    public Invitation mapInvitationToUpdate(UpdateInvitationStatusDto updateInvitationStatusDto, Invitation invitation) {

        invitation.setStatus(updateInvitationStatusDto.getStatus());

        return invitation;
    }
}
