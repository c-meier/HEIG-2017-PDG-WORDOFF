package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
