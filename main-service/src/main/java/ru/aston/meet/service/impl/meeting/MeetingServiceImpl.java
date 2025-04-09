package ru.aston.meet.service.impl.meeting;

import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aston.meet.dto.meeting.MeetingDto;
import ru.aston.meet.dto.meeting.MeetingResponseDto;
import ru.aston.meet.exception.AuthenticationException;
import ru.aston.meet.exception.InvitationException;
import ru.aston.meet.exception.NotFoundException;
import ru.aston.meet.mapper.meeting.MeetingMapper;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.user.User;
import ru.aston.meet.repository.meeting.MeetingRepository;
import ru.aston.meet.service.meeting.MeetingService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;

    @Override
    public MeetingDto create(MeetingDto meetingDto, User user) {
        Meeting newMeeting = meetingMapper.toMeeting(meetingDto);
        newMeeting.setInitiator(user);
        MeetingDto savedMeeting = meetingMapper.toMeetingDto(meetingRepository.save(newMeeting));
        log.debug("New meeting {} was created", savedMeeting);
        return savedMeeting;
    }

    @Override
    public Meeting findById(long meetingId) {
        log.debug("Find meeting with id = {}", meetingId);
        return meetingRepository.findById(meetingId)
                .orElseThrow(() -> new NotFoundException("Meeting with id = " + meetingId + " not found"));
    }

    @Override
    public MeetingResponseDto get(long meetingId) {
        return meetingMapper.toMeetingResponseDto(findById(meetingId));
    }

    @Override
    public List<MeetingResponseDto> getMeetingsByDateForParticipants(LocalDate eventDate, List<Long> participantsId) {
        log.debug("Find meetings for date {} and participants {}", eventDate, participantsId);
        Specification<Meeting> spec = Specification.where(null);

        if (eventDate != null) {
            LocalDateTime start = eventDate.atStartOfDay();
            LocalDateTime end = eventDate.atTime(LocalTime.MAX);

            spec = spec.and((root, query, cb) ->
                    cb.between(root.get("eventDate"), start, end));
        }

        if (participantsId != null && !participantsId.isEmpty()) {
            spec = spec.and((root, query, cb) -> {
                Join<Meeting, Long> participantsJoin = root.join("participants");
                return participantsJoin.in(participantsId);
            });
        }

        return meetingRepository.findAll(spec).stream()
                .map(meetingMapper::toMeetingResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public MeetingDto update(long meetingId, MeetingDto meetingDto, long userId) {
        log.debug("Update meeting with id {}", meetingId);

        Meeting meeting = findById(meetingId);
        verifyCanEdit(meeting, userId);

        meeting.setTitle(meetingDto.getTitle());
        meeting.setDescription(meetingDto.getDescription());
        meeting.setEventDate(meetingDto.getEventDate());
        meeting.setLocation(meetingDto.getLocation());

        Meeting updated = meetingRepository.save(meeting);
        log.debug("Meeting successfully updated by user with id {}", userId);

        return meetingMapper.toMeetingDto(updated);
    }

    @Override
    @Transactional
    public void delete(long meetingId, Long userId) {
        log.debug("Delete meeting with id {}", meetingId);

        Meeting meeting = findById(meetingId);
        verifyCanEdit(meeting, userId);

        meetingRepository.deleteById(meetingId);
        log.debug("Meeting with id {} successfully deleted by user with id {}", meetingId, userId);
    }

    @Override
    public void addConfirmedParticipants(Meeting meeting, Long userId) {
        log.debug("Trying to add participant with id {} to meeting with id {}", userId, meeting.getId());

        Set<Long> participants = meeting.getParticipants();
        Long meetingId = meeting.getId();

        if (participants.contains(userId)) {
            throw new InvitationException("User with id " + userId + " is already a participant of meeting with id " + meetingId);
        }

        participants.add(userId);
        meetingRepository.save(meeting);
        log.debug("Participant with id {} was added to meeting with id {}", userId, meetingId);

    }

    @Override
    public void deleteConfirmedParticipants(Meeting meeting, Long userId) {
        log.debug("Trying to remove participant with id {} from meeting with id {}", userId, meeting.getId());

        Set<Long> participants = meeting.getParticipants();
        Long meetingId = meeting.getId();

        if (!participants.contains(userId)) {
            throw new InvitationException("User with id " + userId + " is not a participant of meeting with id " + meetingId);
        }

        participants.remove(userId);
        meetingRepository.save(meeting);
        log.debug("Participant with id {} was removed from meeting with id {}", userId, meetingId);
    }

    private void verifyCanEdit(Meeting meeting, long userId) {
        if (!canEdit(meeting, userId)) {
            throw new AuthenticationException("You are not allowed to edit this meeting");
        }
    }

    private boolean canEdit(Meeting meeting, long userId) {
        return Objects.equals(meeting.getInitiator().getId(), userId)
                || meeting.getParticipants().contains(userId);
    }
}
