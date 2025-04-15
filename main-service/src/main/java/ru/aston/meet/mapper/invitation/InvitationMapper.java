package ru.aston.meet.mapper.invitation;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants;
import ru.aston.meet.dto.invitation.InvitationDto;
import ru.aston.meet.dto.invitation.UpdateInvitationStatusDto;
import ru.aston.meet.mapper.impl.invitation.InvitationMapperDecorator;
import ru.aston.meet.model.invitation.Invitation;

/**
 * Mapper для преобразования объектов {@link Invitation} и {@link InvitationDto}.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@DecoratedWith(InvitationMapperDecorator.class)
public interface InvitationMapper {

    /**
     * Преобразует объект {@link Invitation} в {@link InvitationDto}.
     * Поля meetingId и userId игнорируются.
     *
     * @param invitation приглашение, которое нужно преобразовать
     * @return преобразованный объект {@link InvitationDto}
     */
    @Mapping(target = "meetingId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(source = "invitation.id", target = "id")
    @Mapping(source = "invitation.status", target = "status")
    InvitationDto toInvitationDto(Invitation invitation);

    /**
     * Обновляет объект {@link Invitation} на основе данных из {@link UpdateInvitationStatusDto}.
     *
     * @param updateInvitationStatusDto объект, содержащий новый статус приглашения
     * @param invitation                 приглашение, которое нужно обновить
     * @return обновленное приглашение
     */
    Invitation mapInvitationToUpdate(UpdateInvitationStatusDto updateInvitationStatusDto, @MappingTarget Invitation invitation);
}