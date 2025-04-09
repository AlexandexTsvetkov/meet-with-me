package ru.aston.meet.controller.invitation;

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
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvitationDto createInvitation(@RequestBody @Valid CreateInvitationDto createInvitationDto) {
        log.info("/invitations {}", createInvitationDto);
        return invitationService.createInvitation(createInvitationDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
        log.info("DELETE - /invitations/{}", id);
        invitationService.deleteInvitation(id);
    }

    @PatchMapping()
    public void edit(@RequestBody UpdateInvitationStatusDto updateInvitationStatusDto) {
        log.info("PATCH - /invitations {}", updateInvitationStatusDto);
        invitationService.updateInvitationStatus(updateInvitationStatusDto);
    }

    @GetMapping("/{id}")
    public InvitationDto getInvocationById(@PathVariable @NotNull @Positive Long id) {
        log.info("GET - /invitations/{}", id);
        return invitationService.getInvitationDto(id);
    }

    @GetMapping("")
    public InvitationDto getInvocationByUserAndMeeting(@RequestParam @Positive @NotNull Long user, @RequestParam  @Positive @NotNull Long meeting) {
        log.info("GET - /invitations?user ={}&meeting={}", user, meeting);
        return invitationService.getInvitationDtoByUserAndMeeting(user, meeting);
    }

    @GetMapping("/meeting/{id}")
    public List<InvitationDto> getInvocationsByMeeting(@PathVariable @NotNull @Positive Long id) {
        log.info("GET - /invitations/meeting/{}", id);
        return invitationService.invitationListByMeeting(id);
    }

    @GetMapping("/user/{id}")
    public List<InvitationDto> getInvocationsByInvited(@PathVariable @NotNull @Positive Long id) {
        log.info("GET - /invitations/user/{}", id);
        return invitationService.invitationListByInvited(id);
    }
}
