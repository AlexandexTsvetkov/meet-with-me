package ru.aston.meet.service.meeting;

import ru.aston.meet.dto.meeting.MeetingDto;
import ru.aston.meet.dto.meeting.MeetingResponseDto;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.user.User;

import java.time.LocalDate;
import java.util.List;

public interface MeetingService {

    /**
     * Создание новой встречи
     */
    MeetingDto create(MeetingDto meetingDto, User user);

    /**
     * Получение встречи по id
     */
    Meeting findById(long meetingId);

    /**
     * Получение встречи по id
     */
    MeetingResponseDto get(long meetingId);

    /**
     * редактирование встречи
     */
    MeetingDto update(long meetingId, MeetingDto meetingDto, long userId);

    /**
     * Удаление встречи по id
     */
    void delete(long meetingId, Long userId);

    /**
     * Получение списка встреч с возможностью фильтра по дате проведения и участникам
     */
    List<MeetingResponseDto> getMeetingsByDateForParticipants(LocalDate eventDate, List<Long> participantsId);

    /**
     * Добавление участника во встречу
     */
    void addConfirmedParticipants(Meeting meeting, Long serId);

    /**
     * Удаление участника из встречи
     */
    void deleteConfirmedParticipants(Meeting meeting, Long serId);
}
