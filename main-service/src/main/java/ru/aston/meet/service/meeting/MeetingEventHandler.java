package ru.aston.meet.service.meeting;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.meeting.MeetingEventType;
import ru.aston.meet.model.meeting.MeetingScheduledEvent;
import ru.aston.meet.model.user.User;
import ru.aston.meet.repository.user.UserRepository;
import ru.aston.meet.service.notification.NotificationService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
public class MeetingEventHandler {

    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final TaskScheduler taskScheduler;


    private final ConcurrentHashMap<Long, ScheduledFuture<?>> scheduledTasks;

    public MeetingEventHandler(NotificationService notificationService, UserRepository userRepository, TaskScheduler taskScheduler) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
        this.taskScheduler = taskScheduler;
        this.scheduledTasks = new ConcurrentHashMap<>();
    }

    @EventListener
    @Async
    public void handleMeetingScheduled(MeetingScheduledEvent event) {
        Meeting meeting = event.getMeeting();
        scheduleNotification(meeting);
    }

    private void scheduleNotification(Meeting meeting) {

        cancelScheduledNotification(meeting.getId());

        LocalDateTime notificationTime = meeting.getEventDate().minusMinutes(15);

        if (notificationTime.isAfter(LocalDateTime.now())) {
            ScheduledFuture<?> future = taskScheduler.schedule(() ->
                    {
                        List<User> users = userRepository.findUsersByMeetingId(meeting.getId());
                        notificationService.sendMeetingEvent(meeting, MeetingEventType.REMIND, users);
                    },
                    notificationTime.atZone(ZoneId.systemDefault()).toInstant()
            );

            scheduledTasks.put(meeting.getId(), future);
        }
    }

    public void cancelScheduledNotification(Long meetingId) {
        ScheduledFuture<?> future = scheduledTasks.get(meetingId);
        if (future != null) {
            future.cancel(false);
            scheduledTasks.remove(meetingId);
        }
    }
}

