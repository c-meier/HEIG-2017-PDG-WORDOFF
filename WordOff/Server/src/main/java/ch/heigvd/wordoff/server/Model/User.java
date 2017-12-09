package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.common.Dto.User.RelationStatus;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class User extends Player {

    @Embedded
    private Credentials credentials;

    @OneToMany(mappedBy = "pk.owner")
    private List<Relation> relations;

    private int level;

    protected User() {
        this.relations = new ArrayList<>();
    }

    public User(String name) {
        super(name);
        this.level = 1;
        this.relations = new ArrayList<>();
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

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    public void setRelation(User target, RelationStatus status) {
        boolean present = false;
        for(Relation r : getRelations()) {
            if(Objects.equals(r.getTarget().getId(), target.getId())) {
                present = true;
                r.setStatus(status);
                if(status == RelationStatus.NONE) {
                    getRelations().remove(r);
                }
            }
        }
        if(!present && status != RelationStatus.NONE) {
            getRelations().add(new Relation(this, target, status));
        }
    }
}
