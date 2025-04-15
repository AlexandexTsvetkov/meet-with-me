package ru.aston.meet.model.meeting;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность, представляющая участника встречи.
 * Связывает пользователя (user_id) с конкретной встречей (meeting_id) в системе.
 * Используется для отображения отношения многие-ко-многим между встречами и пользователями.
 */
@Entity
@Table(name = "meeting_participant")
@Getter
@Setter
public class MeetingParticipant {
    /**
     * Уникальный идентификатор записи об участии
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор встречи, в которой участвует пользователь
     */
    @Column(name = "meeting_id")
    private Long meetingId;

    /**
     * Идентификатор пользователя-участника встречи
     */
    @Column(name = "user_id")
    private Long userId;
}