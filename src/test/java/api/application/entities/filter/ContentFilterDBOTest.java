package api.application.entities.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContentFilterDBOTest {

    @Test
    void testBuilder() {
        // Arrange
        Long id = 1L;
        String title = "Test Title";
        Integer year = 2022;
        String director = "Test Director";
        String genre = "Test Genre";
        Integer minutesLength = 120;
        Integer seasons = 2;
        LocalDate registerDate = LocalDate.now();
        Float rating = 4.5f;
        Long proposedByEmployeeId = 101L;
        Long registeredByEmployeeId = 102L;

        // Act
        ContentFilterDBO contentFilterDBO = ContentFilterDBO.builder()
                .id(id)
                .title(title)
                .year(year)
                .director(director)
                .genre(genre)
                .minutesLength(minutesLength)
                .seasons(seasons)
                .registerDate(registerDate)
                .rating(rating)
                .proposedByEmployeeId(proposedByEmployeeId)
                .registeredByEmployeeId(registeredByEmployeeId)
                .build();

        // Assert
        assertEquals(id, contentFilterDBO.id());
        assertEquals(title, contentFilterDBO.title());
        assertEquals(year, contentFilterDBO.year());
        assertEquals(director, contentFilterDBO.director());
        assertEquals(genre, contentFilterDBO.genre());
        assertEquals(minutesLength, contentFilterDBO.minutesLength());
        assertEquals(seasons, contentFilterDBO.seasons());
        assertEquals(registerDate, contentFilterDBO.registerDate());
        assertEquals(rating, contentFilterDBO.rating());
        assertEquals(proposedByEmployeeId, contentFilterDBO.proposedByEmployeeId());
        assertEquals(registeredByEmployeeId, contentFilterDBO.registeredByEmployeeId());
    }
}

