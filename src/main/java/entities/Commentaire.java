package entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "message_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Commentaire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idC;

    @Column(name = "message", length = 500)
    private String message;

    @Column(name = "date")
    private Date dateC;

    @ManyToOne
    @JoinColumn(name = "utilisateur")
    private Membre membre;
}
