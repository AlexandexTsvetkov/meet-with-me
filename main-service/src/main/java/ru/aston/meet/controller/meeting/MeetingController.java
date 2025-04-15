package ru.aston.meet.controller.meeting;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.aston.meet.dto.meeting.MeetingDto;
import ru.aston.meet.dto.meeting.MeetingResponseDto;
import ru.aston.meet.model.user.User;
import ru.aston.meet.service.meeting.MeetingService;

import java.time.LocalDate;
import java.util.List;

/**
 * REST-контроллер для управления встречами.
 * <p>
 * Обрабатывает HTTP-запросы по пути "/meetings".
 * Позволяет создавать, обновлять, удалять и получать информацию о встречах.
 * Также предоставляет возможность фильтровать список встреч по дате события и участникам.
 * Методы контроллера защищены Bearer Authentication и логируют действия.
 * </p>
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = MeetingController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Meeting Controller", description = "Managing meetings")
@SecurityRequirement(name = "Bearer Authentication")
public class MeetingController {

    static final String REST_URL = "/meetings";

    private final MeetingService meetingService;

    /**
     * Создаёт новую встречу.
     *
     * @param meetingDto данные новой встречи
     * @param user аутентифицированный пользователь, создающий встречу
     * @return созданная встреча
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new meeting", description = "Creates a new meeting with the specified details")
    @ResponseStatus(HttpStatus.OK)
    public MeetingDto create(@Valid @RequestBody MeetingDto meetingDto,
                             @AuthenticationPrincipal User user) {
        log.debug("Create a new meeting {}", meetingDto);
        return meetingService.create(meetingDto, user);
    }

    /**
     * Обновляет детали существующей встречи.
     *
     * @param meetingId идентификатор встречи для обновления
     * @param meetingDto обновлённые данные встречи
     * @param user аутентифицированный пользователь, выполняющий обновление
     * @return обновлённая встреча
     */
    @PutMapping(value = "/{meetingId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update meeting details", description = "Updates the details of the specified meeting")
    @ResponseStatus(HttpStatus.OK)
    public MeetingDto update(@PathVariable long meetingId,
                             @Valid @RequestBody MeetingDto meetingDto,
                             @AuthenticationPrincipal User user) {
        log.debug("Update meeting with id {}", meetingId);
        return meetingService.update(meetingId, meetingDto, user.getId());
    }

    /**
     * Удаляет встречу по её идентификатору.
     *
     * @param meetingId идентификатор удаляемой встречи
     * @param user аутентифицированный пользователь, выполняющий удаление
     */
    @DeleteMapping("/{meetingId}")
    @Operation(summary = "Delete meeting", description = "Deletes the specified meeting")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long meetingId,
                       @AuthenticationPrincipal User user) {
        log.debug("Delete meeting with id {}", meetingId);
        meetingService.delete(meetingId, user.getId());
    }

    /**
     * Получает детали встречи по идентификатору.
     *
     * @param meetingId идентификатор встречи
     * @return данные встречи
     */
    @GetMapping("/{meetingId}")
    @Operation(summary = "Get meeting details by id", description = "Gets the details of the specified meeting")
    public MeetingResponseDto get(@PathVariable long meetingId) {
        log.debug("Get the meeting with id {}", meetingId);
        return meetingService.get(meetingId);
    }

    /**
     * Получает список встреч с возможностью фильтрации по дате события и участникам.
     *
     * @param eventDate дата события для фильтрации (необязательный параметр)
     * @param participantsId список идентификаторов участников для фильтрации (необязательный параметр)
     * @return список встреч, соответствующих фильтрам
     */
    @GetMapping()
    @Operation(summary = "Get meetings details", description = "Gets the details of the list of meetings filtered by event date and participants")
    public List<MeetingResponseDto> getAll(
            @RequestParam @Nullable LocalDate eventDate,
            @RequestParam @Nullable List<Long> participantsId) {
        log.debug("Get meetings for date {} and participants {}", eventDate, participantsId);
        return meetingService.getMeetingsByDateForParticipants(eventDate, participantsId);
    }
}
