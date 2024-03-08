package api.application.entities.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import api.application.entities.dbo.ContentDBO;
import api.application.entities.dbo.EmployeeDBO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@ExtendWith(MockitoExtension.class)
class ContentDTOTest {

    private final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

    @Test
    void testFromDBO() {
        // Arrange database object
        ContentDBO contentDBO = new ContentDBO();
        contentDBO.setId(1L);
        contentDBO.setTitle("Test Title");
        contentDBO.setYear(2022);
        contentDBO.setDirector("Test Director");
        contentDBO.setGenre("Test Genre");
        contentDBO.setLengthMinutes(120);
        contentDBO.setSeasons(2);
        contentDBO.setRegisterDate(LocalDate.now());
        contentDBO.setRating(4.5f);

        EmployeeDBO proposedByEmployee = new EmployeeDBO();
        proposedByEmployee.setId(101L);
        contentDBO.setProposedByEmployee(proposedByEmployee);

        EmployeeDBO registeredByEmployee = new EmployeeDBO();
        registeredByEmployee.setId(102L);
        contentDBO.setRegisteredByEmployee(registeredByEmployee);

        // Mocking
        ContentDTO expectedContentDTO = new ContentDTO(
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

        // Act
        ContentDTO newContentDTO = ContentDTO.fromDBO(contentDBO);

        // Assert
        assertEquals(expectedContentDTO, newContentDTO);
    }
}

