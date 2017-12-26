package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.common.Dto.User.RelationStatus;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class User extends Player {

    @Embedded
    private Credentials credentials;

    @OneToMany(mappedBy = "pk.owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "pk.target")
    private Map<User, Relation> relations;

    private int level;

    protected User() {
        this.relations = new HashMap<>();
    }

    public User(String name) {
        super(name);
        this.level = 1;
        this.relations = new HashMap<>();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Map<User, Relation> getRelations() {
        return relations;
    }

    public void setRelations(Map<User, Relation> relations) {
        this.relations = relations;
    }

    public Relation getRelation(User target) {
        return getRelations().getOrDefault(
                target,
                new Relation(this, target, RelationStatus.NONE));
    }

    @Transactional
    public void setRelation(User target, RelationStatus status) {
        if(getRelations().containsKey(target)) {
            if(status == RelationStatus.NONE) {
                getRelations().remove(target);
            } else {
                getRelations().get(target).setStatus(status);
            }
        } else {
            getRelations().put(target, new Relation(this, target, status));
        }
    }
}
