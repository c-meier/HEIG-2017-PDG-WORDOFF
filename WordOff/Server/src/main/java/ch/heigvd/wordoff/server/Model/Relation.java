/*
 * File: Relation.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.common.Dto.User.RelationStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class that represents the relation between two users.
 */
@Entity
@AssociationOverrides({
        @AssociationOverride(name = "pk.owner",
                joinColumns = @JoinColumn(name = "owner_id")),
        @AssociationOverride(name = "pk.target",
                joinColumns = @JoinColumn(name = "target_id")) })
public class Relation {
    @EmbeddedId
    private RelationId pk;

    @Enumerated(EnumType.ORDINAL)
    private RelationStatus status;

    public Relation() {
    }

    public Relation(User owner, User target, RelationStatus status) {
        this.pk = new RelationId(owner, target);
        this.status = status;
    }

    public RelationId getPk() {
        return pk;
    }

    public void setPk(RelationId pk) {
        this.pk = pk;
    }

    @Transient
    public User getOwner() {
        return pk.owner;
    }

    public void setOwner(User owner) {
        this.pk.owner = owner;
    }

    @Transient
    public User getTarget() {
        return pk.target;
    }

    public void setTarget(User target) {
        this.pk.target = target;
    }

    public RelationStatus getStatus() {
        return status;
    }

    public void setStatus(RelationStatus status) {
        this.status = status;
    }

    @Embeddable
    public static class RelationId implements Serializable {
        @ManyToOne
        private User owner;

        @ManyToOne
        private User target;

        RelationId() {}

        public RelationId(User owner, User target) {
            this.owner = owner;
            this.target = target;
        }

        public User getOwner() {
            return owner;
        }

        public void setOwner(User owner) {
            this.owner = owner;
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
            if (!(o instanceof RelationId)) {
                return false;
            }
            RelationId relationId = (RelationId) o;
            return Objects.equals(owner, relationId.owner) &&
                    Objects.equals(target, relationId.target);
        }

        @Override
        public int hashCode() {
            return Objects.hash(owner, target);
        }
    }
}
