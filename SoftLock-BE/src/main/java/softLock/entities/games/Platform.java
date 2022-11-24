package softLock.entities.games;

import javax.persistence.*;

@Entity
@Table(name = "platform")
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String slug;
    private String abbreviation;
    private String platform;
}