package ru.aston.meet.mapper.meeting;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.aston.meet.dto.meeting.MeetingDto;
import ru.aston.meet.dto.meeting.MeetingResponseDto;
import ru.aston.meet.mapper.impl.meeting.MeetingMapperDecorator;
import ru.aston.meet.model.meeting.Meeting;

/**
 * Mapper для преобразования объектов {@link Meeting} и {@link MeetingDto}.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@DecoratedWith(MeetingMapperDecorator.class)
public interface MeetingMapper {

    /**
     * Преобразует объект {@link MeetingDto} в {@link Meeting}.
     *
     * @param dto объект, содержащий данные встречи
     * @return преобразованный объект {@link Meeting}
     */
    Meeting toMeeting(MeetingDto dto);

    /**
     * Преобразует объект {@link Meeting} в {@link MeetingDto}.
     *
     * @param meeting встреча, которую нужно преобразовать
     * @return преобразованный объект {@link MeetingDto}
     */
    MeetingDto toMeetingDto(Meeting meeting);

    /**
     * Преобразует объект {@link Meeting} в {@link MeetingResponseDto}.
     * Устанавливает идентификатор инициатора встречи.
     *
     * @param meeting встреча, которую нужно преобразовать
     * @return преобразованный объект {@link MeetingResponseDto}
     */
    @Mapping(source = "initiator.id", target = "initiatorId")
    MeetingResponseDto toMeetingResponseDto(Meeting meeting);
}