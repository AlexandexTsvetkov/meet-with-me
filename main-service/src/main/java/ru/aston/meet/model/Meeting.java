package ru.aston.meet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

