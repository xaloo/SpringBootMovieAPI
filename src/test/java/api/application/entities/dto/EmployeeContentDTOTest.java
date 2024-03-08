package api.application.entities.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import api.application.entities.dbo.ContentDBO;
import api.application.entities.dbo.EmployeeDBO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class EmployeeContentDTOTest {

    @Test
    void testFromDBOs() {
        // Arrange ContentDBO
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

        // Arrange EmployeeDBO
        EmployeeDBO employeeDBO = new EmployeeDBO();
        employeeDBO.setId(101L);
        employeeDBO.setName("Javier");
        employeeDBO.setEmail("javier@example.com");
        employeeDBO.setRole("Backend");
        employeeDBO.setAge(30);

        // Mocking
        EmployeeDTO expectedEmployeeDTO = new EmployeeDTO(
            employeeDBO.getId(),
            employeeDBO.getName(),
            employeeDBO.getEmail(),
            employeeDBO.getPassword(),
            employeeDBO.getRole(),
            employeeDBO.getAge()
        );

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

        EmployeeContentDTO expectedEmployeeContentDTO = new EmployeeContentDTO(
                expectedEmployeeDTO,
                expectedContentDTO
        );

        // Act
        EmployeeContentDTO actualEmployeeContentDTO = EmployeeContentDTO.fromDBOs(employeeDBO, contentDBO);

        // Assert
        assertEquals(expectedEmployeeContentDTO, actualEmployeeContentDTO);
    }
}

