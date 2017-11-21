package ch.heigvd.wordoff.server.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
public class Mode {
    @Id
    private Long id;

    @OneToMany(mappedBy = "pk.mode")
    private List<Invitation> invitations;

    @OneToMany(mappedBy = "mode")
    private List<Game> games;

    private Date startDate;
}
