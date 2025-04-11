package ru.aston.meet.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.aston.meet.kafka.notifications.invitation.InvitationAvro;
import ru.aston.meet.kafka.notifications.meeting.MeetingAvro;
import ru.aston.meet.kafka.notifications.partipant.ParticipantAvro;
import ru.aston.meet.mapper.avro.invitation.InvitationAvroMapper;
import ru.aston.meet.mapper.avro.meeting.MeetingAvroMapper;
import ru.aston.meet.mapper.avro.partipant.ParticipantAvroMapper;
import ru.aston.meet.mapper.user.PartipantEventType;
import ru.aston.meet.model.invitation.Invitation;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.meeting.MeetingEventType;
import ru.aston.meet.model.user.User;
import ru.aston.meet.util.KafkaProperties;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final Producer<String, SpecificRecordBase> producer;
    private final KafkaProperties kafkaProperties;
    private final MeetingAvroMapper meetingAvroMapper;
    private final InvitationAvroMapper invitationAvroMapper;
    private final ParticipantAvroMapper participantAvroMapper;

    @Override
    public void sendMeetingEvent(Meeting meeting, MeetingEventType meetingEventType, List<User> users) {

        MeetingAvro meetingAvro = meetingAvroMapper.mapToMeetingAvro(meeting, meetingEventType, users);

        producer.send(new ProducerRecord<>(kafkaProperties.getMeetingTopic(),
                null,
                Instant.now().toEpochMilli(),
                meetingAvro.getId(),
                meetingAvro));

        log.info("Kafka message meetingAvro : {}", meetingAvro.getId());
        log.debug("Kafka message meetingAvro = {}, {}", meetingAvro.getId(), meetingAvro);
    }

    @Override
    public void sendInvitation(Invitation invitation) {

        InvitationAvro invitationAvro = invitationAvroMapper.mapToInvitationAvro(invitation);

        producer.send(new ProducerRecord<>(kafkaProperties.getInvitationTopic(),
                null,
                Instant.now().toEpochMilli(),
                invitationAvro.getId(),
                invitationAvro));

        log.info("Kafka message invitationAvro : {}", invitationAvro.getId());
        log.debug("Kafka message invitationAvro = {}, {}", invitationAvro.getId(), invitationAvro);
    }

    @Override
    public void sendPartipant(Meeting meeting, User user, PartipantEventType partipantEventType) {

        ParticipantAvro participantAvro = participantAvroMapper.mapToParticipantAvro(meeting, user, partipantEventType);

        producer.send(new ProducerRecord<>(kafkaProperties.getPartipantTopic(),
                null,
                Instant.now().toEpochMilli(),
                participantAvro.getId(),
                participantAvro));

        log.info("Kafka message participantAvro : {}", participantAvro.getId());
        log.debug("Kafka message participantAvro = {}, {}", participantAvro.getId(), participantAvro);
    }
}

