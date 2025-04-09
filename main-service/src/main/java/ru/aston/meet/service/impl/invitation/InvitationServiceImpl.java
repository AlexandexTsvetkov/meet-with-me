package ru.aston.meet.service.impl.invitation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aston.meet.dto.invitation.CreateInvitationDto;
import ru.aston.meet.dto.invitation.InvitationDto;
import ru.aston.meet.dto.invitation.UpdateInvitationStatusDto;
import ru.aston.meet.exception.AlreadyExistsException;
import ru.aston.meet.exception.InvitationException;
import ru.aston.meet.exception.NotFoundException;
import ru.aston.meet.mapper.invitation.InvitationMapper;
import ru.aston.meet.model.invitation.Invitation;
import ru.aston.meet.model.invitation.InvitationStatus;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.user.User;
import ru.aston.meet.repository.invitation.InvitationRepository;
import ru.aston.meet.repository.meeting.MeetingRepository;
import ru.aston.meet.repository.user.UserRepository;
import ru.aston.meet.service.invitation.InvitationService;
import ru.aston.meet.service.meeting.MeetingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    private final InvitationMapper invitationMapper;
    private final MeetingRepository meetingRepository;
    private final MeetingService meetingService;

    @Override
    @Transactional
    public InvitationDto createInvitation(CreateInvitationDto request) {

        Long userId = request.getUserId();
        Long meetingId = request.getMeetingId();

        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new NotFoundException("Meeting not found, id: " + meetingId));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found, id: " + userId));

        Optional<Invitation> optionalInvitation = invitationRepository.findByInvitedIdAndMeetingId(userId, meetingId);
        if (optionalInvitation.isPresent()) {
            throw new AlreadyExistsException("Invitation already exists, meetingId: " + meetingId + ", userId: " + userId);
        }

        Invitation newInvitation = new Invitation();
        newInvitation.setInvited(user);
        newInvitation.setMeeting(meeting);
        newInvitation.setStatus(InvitationStatus.NEW);

        Invitation invitation = invitationRepository.save(newInvitation);

        return invitationMapper.toInvitationDto(invitation);
    }

    @Override
    public InvitationDto getInvitationDto(Long id) {
        return invitationMapper.toInvitationDto(invitationRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public void deleteInvitation(Long id) {
        invitationRepository.deleteById(id);
    }

    @Override
    public InvitationDto getInvitationDtoByUserAndMeeting(Long userId, Long meetingId) {
        return invitationMapper.toInvitationDto(invitationRepository.findByInvitedIdAndMeetingId(userId, meetingId).orElseThrow(() -> new NotFoundException("Invitation not found, meetingId: " + meetingId + ", userId: " + userId)));
    }

    @Override
    @Transactional
    public InvitationDto updateInvitationStatus(UpdateInvitationStatusDto request) {
        log.debug("Attempt to change status for invitation with id = {}", request.getId());
        Invitation invitation = findById(request.getId());
        Meeting meeting = invitation.getMeeting();

        if (meeting.getEventDate().isBefore(LocalDateTime.now())) {
            throw new InvitationException("You can't change invitation status for for a past meeting with id " + meeting.getId());
        }

        Long userId = invitation.getInvited().getId();
        InvitationStatus newStatus = request.getStatus();

        if (newStatus == InvitationStatus.CONFIRMED) {
            meetingService.addConfirmedParticipants(meeting, userId);
        } else if (newStatus == InvitationStatus.CANCELLED) {
            meetingService.deleteConfirmedParticipants(meeting, userId);
        }

        Invitation updated = invitationMapper.mapInvitationToUpdate(request, invitation);
        log.debug("Change status {} for invitation with id = {}", newStatus, request.getId());
        return invitationMapper.toInvitationDto(invitationRepository.save(updated));
    }

    @Override
    public List<InvitationDto> invitationListByMeeting(Long id) {
        return invitationRepository.findByMeetingId(id).stream().map(invitationMapper::toInvitationDto).collect(Collectors.toList());
    }

    @Override
    public List<InvitationDto> invitationListByInvited(Long id) {
        return invitationRepository.findByInvitedId(id).stream().map(invitationMapper::toInvitationDto).collect(Collectors.toList());
    }

    private Invitation findById(long invitationId) {
        log.debug("Find invitation with id = {}", invitationId);
        return invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NotFoundException("Invitation with id = " + invitationId + " not found"));
    }
}
