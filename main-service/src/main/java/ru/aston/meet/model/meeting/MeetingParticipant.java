package ru.aston.meet.model.meeting;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "meeting_participant")
@Getter
@Setter
public class MeetingParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meeting_id")
    private Long meetingId;

    @Column(name = "user_id")
    private Long userId;
}
