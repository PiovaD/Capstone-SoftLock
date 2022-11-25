package softLock.entities.games;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
@Table(name = "platform")
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "igdb_id", nullable = false, unique = true)
    private Long igdbID;

    private String name;
    private String slug;
    private String abbreviation;
    private String platformImageId;
}