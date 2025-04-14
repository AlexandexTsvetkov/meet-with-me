package ru.aston.meet.mapper.meeting;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.aston.meet.dto.meeting.MeetingDto;
import ru.aston.meet.dto.meeting.MeetingResponseDto;
import ru.aston.meet.mapper.impl.meeting.MeetingMapperDecorator;
import ru.aston.meet.model.meeting.Meeting;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@DecoratedWith(MeetingMapperDecorator.class)
public interface MeetingMapper {

    Meeting toMeeting(MeetingDto dto);

    MeetingDto toMeetingDto(Meeting meeting);

    @Mapping(source = "initiator.id", target = "initiatorId")
    MeetingResponseDto toMeetingResponseDto(Meeting meeting);
}
