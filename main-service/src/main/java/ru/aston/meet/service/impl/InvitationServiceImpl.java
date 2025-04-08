package ru.aston.meet.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aston.meet.dto.CreateInvitationDto;
import ru.aston.meet.dto.InvitationDto;
import ru.aston.meet.dto.UpdateInvitationStatusDto;
import ru.aston.meet.exception.AlreadyExistsException;
import ru.aston.meet.exception.NotFoundException;
import ru.aston.meet.mapper.InvitationMapper;
import ru.aston.meet.model.Invitation;
import ru.aston.meet.model.InvitationStatus;
import ru.aston.meet.model.Meeting;
import ru.aston.meet.model.User;
import ru.aston.meet.repository.InvitationRepository;
import ru.aston.meet.repository.UserRepository;
import ru.aston.meet.service.InvitationService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    private final InvitationMapper invitationMapper;

    @Override
    public InvitationDto createInvitation(CreateInvitationDto request) {

        Long userId = request.getUserId();
        Long meetingId = request.getMeetingId();

        Meeting meeting = new Meeting(); // Здесь получение meeting
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
    public void deleteInvitation(Long id) {
        invitationRepository.deleteById(id);
    }

    @Override
    public InvitationDto getInvitationDtoByUserAndMeeting(Long userId, Long meetingId) {
        return invitationMapper.toInvitationDto(invitationRepository.findByInvitedIdAndMeetingId(userId, meetingId).orElseThrow(() -> new NotFoundException("Invitation not found, meetingId: " + meetingId + ", userId: " + userId)));
    }

    @Override
    public InvitationDto updateInvitationStatus(UpdateInvitationStatusDto request) {
        Invitation invitation = invitationRepository.findById(request.getId()).orElseThrow(() -> new NotFoundException("Invitation not found, id: " + request.getId()));
        return invitationMapper.toInvitationDto(invitationRepository.save(invitationMapper.mapInvitationToUpdate(request, invitation)));
    }

    @Override
    public List<InvitationDto> invitationListByMeeting(Long id) {
        return invitationRepository.findByMeetingId(id).stream().map(invitationMapper::toInvitationDto).collect(Collectors.toList());
    }

    @Override
    public List<InvitationDto> invitationListByInvited(Long id) {
        return invitationRepository.findByInvitedId(id).stream().map(invitationMapper::toInvitationDto).collect(Collectors.toList());
    }
}
