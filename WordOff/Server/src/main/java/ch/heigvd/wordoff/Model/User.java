package ch.heigvd.wordoff.Model;

import ch.heigvd.wordoff.common.Model.Player;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class User extends Player{
    String password;
}
