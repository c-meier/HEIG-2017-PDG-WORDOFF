package ch.heigvd.wordoff.server.Model;

import javax.persistence.*;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Player {
    @Id
    @GeneratedValue
    private Long id;

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
