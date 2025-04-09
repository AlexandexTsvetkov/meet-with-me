package ru.aston.meet.service.invitation;

import ru.aston.meet.dto.invitation.CreateInvitationDto;
import ru.aston.meet.dto.invitation.InvitationDto;
import ru.aston.meet.dto.invitation.UpdateInvitationStatusDto;

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
