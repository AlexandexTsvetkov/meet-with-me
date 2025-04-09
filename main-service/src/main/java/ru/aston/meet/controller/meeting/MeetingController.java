package ru.aston.meet.controller.meeting;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.meet.dto.meeting.MeetingDto;
import ru.aston.meet.dto.meeting.MeetingResponseDto;
import ru.aston.meet.model.user.User;
import ru.aston.meet.service.meeting.MeetingService;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = MeetingController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MeetingController {

    static final String REST_URL = "/meetings";

    private final MeetingService meetingService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MeetingDto create(@Valid @RequestBody MeetingDto meetingDto, @AuthenticationPrincipal User user) {
        log.debug("Create a new meeting {}", meetingDto);
        return meetingService.create(meetingDto, user);
    }

    @PutMapping(value = "/{meetingId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MeetingDto update(@PathVariable long meetingId, @Valid @RequestBody MeetingDto meetingDto, @AuthenticationPrincipal User user) {
        log.debug("Update meeting with id {}", meetingId);

        return meetingService.update(meetingId, meetingDto, user.getId());
    }

    @DeleteMapping("/{meetingId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long meetingId, @AuthenticationPrincipal User user) {
        log.debug("Delete meeting with id {}", meetingId);
        meetingService.delete(meetingId, user.getId());
    }

    @GetMapping("/{meetingId}")
    public MeetingResponseDto get(@PathVariable long meetingId) {
        log.debug("Get the meeting with id {}", meetingId);
        return meetingService.get(meetingId);
    }

    @GetMapping()
    public List<MeetingResponseDto> getAll(
            @RequestParam @Nullable LocalDate eventDate,
            @RequestParam @Nullable List<Long> participantsId) {
        log.debug("Get meetings for date {} and participants {}", eventDate, participantsId);
        return meetingService.getMeetingsByDateForParticipants(eventDate, participantsId);
    }
}
