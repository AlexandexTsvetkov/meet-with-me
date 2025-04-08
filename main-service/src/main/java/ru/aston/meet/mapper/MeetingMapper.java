package ru.aston.meet.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.aston.meet.dto.MeetingDto;
import ru.aston.meet.mapper.impl.MeetingMapperDecorator;
import ru.aston.meet.model.Meeting;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@DecoratedWith(MeetingMapperDecorator.class)
public interface MeetingMapper {

    Meeting toMeeting(MeetingDto dto);

    MeetingDto toMeetingDto(Meeting meeting);
}
