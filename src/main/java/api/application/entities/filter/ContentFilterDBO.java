package api.application.entities.filter;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ContentFilterDBO(Long id, String title, Integer year, String director,
                               String genre, Integer minutesLength, Integer seasons,
                               LocalDate registerDate, Float rating, Long proposedByEmployeeId,
                               Long registeredByEmployeeId) {
}
