package ru.aston.meet.model.meeting;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.aston.meet.model.user.User;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Класс, представляющий встречу в системе.
 *
 * <p>{@link #id} - Идентификатор.</p>
 * <p>{@link #title} - Название.</p>
 * <p>{@link #description} - Описание.</p>
 * <p>{@link #createOn} - Дата создания.</p>
 * <p>{@link #eventDate} - Дата прведения.</p>
 * <p>{@link #initiator} - Инициатор встречи.</p>
 * <p>{@link #location} - Место проведения.</p>
 * <p>{@link #participants} - Список участников.</p>
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(name = "create_on")
    private LocalDateTime createOn;

    @Column(name = "meeting_date")
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    private String location;

    @ElementCollection
    @CollectionTable(name = "meeting_participant", joinColumns = @JoinColumn(name = "meeting_id"))
    @Column(name = "user_id")
    private Set<Long> participants;
}

