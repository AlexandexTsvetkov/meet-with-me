package ru.aston.meet.controller.invitation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.aston.meet.dto.invitation.CreateInvitationDto;
import ru.aston.meet.dto.invitation.InvitationDto;
import ru.aston.meet.dto.invitation.UpdateInvitationStatusDto;
import ru.aston.meet.service.invitation.InvitationService;

/**
 * REST-контроллер для управления приглашениями.
 * <p>
 * Предоставляет API для создания, удаления, обновления и получения приглашений.
 * Методы контроллера валидируют входные данные и обрабатывают запросы по пути "/invitations".
 * Контроллер защищён с помощью Bearer Authentication.
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/invitations")
@Slf4j
@Validated
@Tag(name = "Invitation Controller", description = "Managing invitation")
@SecurityRequirement(name = "Bearer Authentication")
public class InvitationController {

    private final InvitationService invitationService;

    /**
     * Создаёт новое приглашение с указанными данными.
     *
     * @param createInvitationDto объект с данными для создания приглашения
     * @return созданное приглашение
     */
    @PostMapping
    @Operation(summary = "Create a new invitation", description = "Creates a new invitation with the specified details")
    @ResponseStatus(HttpStatus.CREATED)
    public InvitationDto createInvitation(@RequestBody @Valid CreateInvitationDto createInvitationDto) {
        log.info("/invitations {}", createInvitationDto);
        return invitationService.createInvitation(createInvitationDto);
    }

    /**
     * Удаляет приглашение по идентификатору.
     *
     * @param id идентификатор приглашения, должен быть положительным числом
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete invitation", description = "Deletes the specified invitation")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
        log.info("DELETE - /invitations/{}", id);
        invitationService.deleteInvitation(id);
    }

    /**
     * Обновляет статус приглашения.
     *
     * @param updateInvitationStatusDto объект с данными для обновления статуса приглашения
     */
    @PatchMapping()
    @Operation(summary = "Update invitation details", description = "Updates the details of the specified invitation")
    public void edit(@Valid @RequestBody UpdateInvitationStatusDto updateInvitationStatusDto) {
        log.info("PATCH - /invitations {}", updateInvitationStatusDto);
        invitationService.updateInvitationStatus(updateInvitationStatusDto);
    }

    /**
     * Получает детали приглашения по его идентификатору.
     *
     * @param id идентификатор приглашения, должен быть положительным числом
     * @return DTO с данными приглашения
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get invocation details by id", description = "Gets the details of the specified invocation")
    public InvitationDto getInvocationById(@PathVariable @NotNull @Positive Long id) {
        log.info("GET - /invitations/{}", id);
        return invitationService.getInvitationDto(id);
    }

    /**
     * Получает детали приглашения по идентификатору пользователя и идентификатору встречи.
     *
     * @param user идентификатор пользователя, должен быть положительным числом
     * @param meeting идентификатор встречи, должен быть положительным числом
     * @return DTO с данными приглашения
     */
    @GetMapping()
    @Operation(summary = "Get invocation details by userId and meetingId", description = "Gets the details of the specified invocation")
    public InvitationDto getInvocationByUserAndMeeting(@RequestParam @Positive @NotNull Long user,
                                                       @RequestParam @Positive @NotNull Long meeting) {
        log.info("GET - /invitations?user ={}&meeting={}", user, meeting);
        return invitationService.getInvitationDtoByUserAndMeeting(user, meeting);
    }
}