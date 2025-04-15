package ru.aston.meet.mapper.impl.invitation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.aston.meet.dto.invitation.InvitationDto;
import ru.aston.meet.dto.invitation.UpdateInvitationStatusDto;
import ru.aston.meet.mapper.invitation.InvitationMapper;
import ru.aston.meet.model.invitation.Invitation;

import javax.annotation.processing.Generated;

/**
 * Декоратор для {@link InvitationMapper}, который добавляет дополнительную логику преобразования.
 */
@Generated(value = "org.mapstruct.ap.MappingProcessor")
public abstract class InvitationMapperDecorator implements InvitationMapper {

    @Autowired
    private InvitationMapper delegate;

    /**
     * Преобразует объект {@link Invitation} в {@link InvitationDto} с добавлением дополнительных полей.
     *
     * @param invitation приглашение, которое нужно преобразовать
     * @return преобразованный объект {@link InvitationDto}
     */
    @Override
    public InvitationDto toInvitationDto(Invitation invitation) {
        InvitationDto invitationDto = delegate.toInvitationDto(invitation);
        invitationDto.setUserId(invitation.getInvited().getId());
        invitationDto.setMeetingId(invitation.getMeeting().getId());

        return invitationDto;
    }

    /**
     * Обновляет статус приглашения на основе данных из {@link UpdateInvitationStatusDto}.
     *
     * @param updateInvitationStatusDto объект, содержащий новый статус приглашения
     * @param invitation                 приглашение, которое нужно обновить
     * @return обновленное приглашение
     */
    @Override
    public Invitation mapInvitationToUpdate(UpdateInvitationStatusDto updateInvitationStatusDto, Invitation invitation) {
        invitation.setStatus(updateInvitationStatusDto.getStatus());
        return invitation;
    }
}