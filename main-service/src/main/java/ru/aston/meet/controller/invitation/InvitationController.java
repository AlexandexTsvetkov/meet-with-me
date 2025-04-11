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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invitations")
@Slf4j
@Validated
@Tag(name = "Invitation Controller", description = "Managing invitation")
@SecurityRequirement(name = "Bearer Authentication")
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    @Operation(summary = "Create a new invitation", description = "Creates a new invitation with the specified details")
    @ResponseStatus(HttpStatus.CREATED)
    public InvitationDto createInvitation(@RequestBody @Valid CreateInvitationDto createInvitationDto) {
        log.info("/invitations {}", createInvitationDto);
        return invitationService.createInvitation(createInvitationDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete invitation", description = "Deletes the specified invitation")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
        log.info("DELETE - /invitations/{}", id);
        invitationService.deleteInvitation(id);
    }

    @PatchMapping()
    @Operation(summary = "Update invitation details", description = "Updates the details of the specified invitation")
    public void edit(@Valid @RequestBody UpdateInvitationStatusDto updateInvitationStatusDto) {
        log.info("PATCH - /invitations {}", updateInvitationStatusDto);
        invitationService.updateInvitationStatus(updateInvitationStatusDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get invocation details by id", description = "Gets the details of the specified invocation")
    public InvitationDto getInvocationById(@PathVariable @NotNull @Positive Long id) {
        log.info("GET - /invitations/{}", id);
        return invitationService.getInvitationDto(id);
    }

    @GetMapping()
    @Operation(summary = "Get invocation details by userId and meetingId", description = "Gets the details of the specified invocation")
    public InvitationDto getInvocationByUserAndMeeting(@RequestParam @Positive @NotNull Long user,
                                                       @RequestParam @Positive @NotNull Long meeting) {
        log.info("GET - /invitations?user ={}&meeting={}", user, meeting);
        return invitationService.getInvitationDtoByUserAndMeeting(user, meeting);
    }

    @GetMapping("/meeting/{id}")
    @Operation(summary = "Get invocations details by meetingId", description = "Gets the details of the list of invocations")
    public List<InvitationDto> getInvocationsByMeeting(@PathVariable @NotNull @Positive Long id) {
        log.info("GET - /invitations/meeting/{}", id);
        return invitationService.invitationListByMeeting(id);
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Get invocations details by userId", description = "Gets the details of the specified list of invocations")
    public List<InvitationDto> getInvocationsByInvited(@PathVariable @NotNull @Positive Long id) {
        log.info("GET - /invitations/user/{}", id);
        return invitationService.invitationListByInvited(id);
    }
}
