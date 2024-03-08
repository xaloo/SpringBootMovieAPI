package api.application.entities.dto;

import api.application.entities.dbo.ContentDBO;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.lang.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContentDTO(@Nullable Long id,
                         @NotNull @Size(max = 255) String title,
                         @NotNull Integer year,
                         @NotNull @Size(max = 255) String director,
                         @NotNull @Size(max = 50) String genre,
                         @Nullable Integer minutesLength,
                         @Nullable Integer seasons,
                         @Nullable LocalDate registerDate,
                         @NotNull Float rating,
                         @NotNull Long proposedByEmployeeId,
                         @NotNull Long registeredByEmployeeId) {
    public static ContentDTO fromDBO(ContentDBO contentDBO) {
        return new ContentDTO(
                contentDBO.getId(),
                contentDBO.getTitle(),
                contentDBO.getYear(),
                contentDBO.getDirector(),
                contentDBO.getGenre(),
                contentDBO.getLengthMinutes(),
                contentDBO.getSeasons(),
                contentDBO.getRegisterDate(),
                contentDBO.getRating(),
                contentDBO.getProposedByEmployee().getId(),
                contentDBO.getRegisteredByEmployee().getId()
        );
    }
}
