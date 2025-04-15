package ru.aston.meet.mapper.impl.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import ru.aston.meet.dto.meeting.MeetingDto;
import ru.aston.meet.mapper.meeting.MeetingMapper;
import ru.aston.meet.model.meeting.Meeting;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

/**
 * Декоратор для {@link MeetingMapper}, который добавляет дополнительную логику преобразования.
 */
@Generated(value = "org.mapstruct.ap.MappingProcessor")
public abstract class MeetingMapperDecorator implements MeetingMapper {

    @Autowired
    protected MeetingMapper delegate;

    /**
     * Преобразует объект {@link MeetingDto} в {@link Meeting} и устанавливает время создания.
     *
     * @param dto объект, содержащий данные встречи
     * @return преобразованный объект {@link Meeting} с установленным временем создания
     */
    @Override
    public Meeting toMeeting(MeetingDto dto) {
        Meeting meeting = delegate.toMeeting(dto);
        meeting.setCreateOn(LocalDateTime.now());
        return meeting;
    }
}