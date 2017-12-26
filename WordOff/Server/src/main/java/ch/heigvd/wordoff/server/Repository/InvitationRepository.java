package ch.heigvd.wordoff.server.Repository;

import ch.heigvd.wordoff.common.Dto.InvitationStatus;
import ch.heigvd.wordoff.server.Model.Invitation;
import ch.heigvd.wordoff.server.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Invitation.InvitationId> {
    List<Invitation> findAllByPkTargetAndStatus(User target, InvitationStatus status);
}
