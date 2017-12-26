package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.common.Dto.InvitationStatus;
import ch.heigvd.wordoff.server.Model.Modes.Mode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@AssociationOverrides({
        @AssociationOverride(name = "pk.mode",
                joinColumns = @JoinColumn(name = "mode_id")),
        @AssociationOverride(name = "pk.target",
                joinColumns = @JoinColumn(name = "target_id")) })
public class Invitation {
    @EmbeddedId
    private InvitationId pk;

    @Enumerated(EnumType.ORDINAL)
    private InvitationStatus status;

    private String name;

    public Invitation() {
    }

    public Invitation(Mode mode, User target, InvitationStatus status, String name) {
        this.pk = new InvitationId(mode, target);
        this.status = status;
        this.name = name;
    }

    public InvitationId getPk() {
        return pk;
    }

    public void setPk(InvitationId pk) {
        this.pk = pk;
    }

    @Transient
    public Mode getMode() {
        return pk.mode;
    }

    public void setMode(Mode mode) {
        this.pk.mode = mode;
    }

    @Transient
    public User getTarget() {
        return pk.target;
    }

    public void setTarget(User target) {
        this.pk.target = target;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Embeddable
    public static class InvitationId implements Serializable {
        @ManyToOne
        private Mode mode;

        @ManyToOne
        private User target;

        InvitationId() {}

        public InvitationId(Mode mode, User target) {
            this.mode = mode;
            this.target = target;
        }

        public Mode getMode() {
            return mode;
        }

        public void setMode(Mode mode) {
            this.mode = mode;
        }

        public User getTarget() {
            return target;
        }

        public void setTarget(User target) {
            this.target = target;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof InvitationId)) {
                return false;
            }
            InvitationId relationId = (InvitationId) o;
            return Objects.equals(mode, relationId.mode) &&
                    Objects.equals(target, relationId.target);
        }

        @Override
        public int hashCode() {
            return Objects.hash(mode.getId(), target.getId());
        }
    }
}
