package api.application.entities.dbo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "content", uniqueConstraints = @UniqueConstraint(columnNames = "title"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDBO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "year", nullable = false)
    private int year;

    @NotNull @Size(max = 255)
    @Column(name = "director", nullable = false)
    private String director;

    @NotNull @Size(max = 50)
    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "length_minutes")
    private Integer lengthMinutes;

    @Column(name = "seasons")
    private Integer seasons;

    @Column(name = "register_date")
    private LocalDate registerDate;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Float rating;

    @ManyToOne
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "id_proposed_by_employee", nullable = false)
    @NotNull
    private EmployeeDBO proposedByEmployee;

    @ManyToOne
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "id_registered_by_employee", nullable = false)
    @NotNull
    private EmployeeDBO registeredByEmployee;
}
