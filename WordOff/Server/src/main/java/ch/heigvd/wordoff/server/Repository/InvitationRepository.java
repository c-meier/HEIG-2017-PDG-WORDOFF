/*
 * File: InvitationRepository.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.common.Dto.InvitationStatus;
import ch.heigvd.wordoff.server.Model.Invitation;
import ch.heigvd.wordoff.server.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for the invitations.
 */
public interface InvitationRepository extends JpaRepository<Invitation, Invitation.InvitationId> {
    List<Invitation> findAllByPkTargetAndStatus(User target, InvitationStatus status);
    Invitation findByPkTargetIdAndPkModeId(Long targetId, Long modeId);
}
