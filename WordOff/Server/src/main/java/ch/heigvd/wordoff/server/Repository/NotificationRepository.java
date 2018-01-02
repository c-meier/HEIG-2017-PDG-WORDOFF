package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByTargetId(Long targetId);
}
