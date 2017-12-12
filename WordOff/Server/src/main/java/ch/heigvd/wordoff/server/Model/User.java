package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.common.Dto.User.RelationStatus;

import javax.persistence.*;
import java.util.Map;
import java.util.TreeMap;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class User extends Player {

    @Embedded
    private Credentials credentials;

    @OneToMany(mappedBy = "pk.owner")
    @MapKey(name = "pk.target")
    private Map<Long, Relation> relations;

    private int level;

    protected User() {
        this.relations = new TreeMap<>();
    }

    public User(String name) {
        super(name);
        this.level = 1;
        this.relations = new TreeMap<>();
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

    public Map<Long, Relation> getRelations() {
        return relations;
    }

    public void setRelations(Map<Long, Relation> relations) {
        this.relations = relations;
    }

    public Relation getRelation(User target) {
        return getRelations().getOrDefault(
                target.getId(),
                new Relation(this, target, RelationStatus.NONE));
    }

    public void setRelation(User target, RelationStatus status) {
        if(getRelations().containsKey(target.getId()) && status == RelationStatus.NONE) {
            getRelations().remove(target.getId());
        } else {
            getRelations().put(target.getId(), new Relation(this, target, status));
        }
    }
}
