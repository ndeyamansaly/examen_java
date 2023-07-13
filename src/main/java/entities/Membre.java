package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;


@Entity
@Table(name = "membre_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Membre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idM;

    @Column(name = "username", length = 200, unique = true)
    private String username;

    @OneToMany(mappedBy = "membre")
    private List<entities.Commentaire> commentaires;


}
