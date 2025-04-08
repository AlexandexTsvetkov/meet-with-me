package ru.aston.meet.service;

import ru.aston.meet.dto.CreateInvitationDto;
import ru.aston.meet.dto.InvitationDto;
import ru.aston.meet.dto.UpdateInvitationStatusDto;

import java.util.List;

public interface InvitationService {

    InvitationDto createInvitation(CreateInvitationDto request);

    InvitationDto getInvitationDto(Long id);

    void deleteInvitation(Long id);

    InvitationDto getInvitationDtoByUserAndMeeting(Long userId, Long meetingId);

    InvitationDto updateInvitationStatus(UpdateInvitationStatusDto request);

    List<InvitationDto> invitationListByMeeting(Long id);

    List<InvitationDto> invitationListByInvited(Long id);
}
