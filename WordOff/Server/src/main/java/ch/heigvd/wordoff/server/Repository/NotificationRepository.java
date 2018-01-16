/*
 * File: NotificationRepository.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.server.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for the notifications.
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByTargetId(Long targetId);
}
