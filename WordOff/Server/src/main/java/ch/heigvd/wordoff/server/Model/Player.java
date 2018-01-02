package ch.heigvd.wordoff.server.Model;

import javax.persistence.*;

/**
 * Class that represents a player (User with less information)
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Player {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    protected Player() {}

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
