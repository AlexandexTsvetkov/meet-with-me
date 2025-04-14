package ru.aston.meet.service.invitation;

import ru.aston.meet.dto.invitation.CreateInvitationDto;
import ru.aston.meet.dto.invitation.InvitationDto;
import ru.aston.meet.dto.invitation.UpdateInvitationStatusDto;

import java.util.List;

public interface InvitationService {

    /**
     * Создание нового приглашения
     */
    InvitationDto createInvitation(CreateInvitationDto request);

    /**
     * Получение приглашения по id
     */
    InvitationDto getInvitationDto(Long id);

    /**
     * Удаление приглашения по id
     */
    void deleteInvitation(Long id);

    /**
     * Получшение приглашения по идентификатору пользователя и встречи
     */
    InvitationDto getInvitationDtoByUserAndMeeting(Long userId, Long meetingId);

    /**
     * Редактированеи приглашения
     */
    InvitationDto updateInvitationStatus(UpdateInvitationStatusDto request);

    /**
     * Получение списока приглашений по идентификатору встречи
     */
    List<InvitationDto> invitationListByMeeting(Long id);

    /**
     * Получение списока приглашений по идентификатору пользователя
     */
    List<InvitationDto> invitationListByInvited(Long id);
}
