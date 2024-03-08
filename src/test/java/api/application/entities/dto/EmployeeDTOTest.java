package api.application.entities.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import api.application.entities.dbo.EmployeeDBO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeDTOTest {

    @Test
    void testFromDBO() {
        // Arrange
        EmployeeDBO employeeDBO = new EmployeeDBO();
        employeeDBO.setId(101L);
        employeeDBO.setName("Javier");
        employeeDBO.setEmail("javier@example.com");
        employeeDBO.setPassword("password123");
        employeeDBO.setRole("Backend");
        employeeDBO.setAge(30);

        // Mocking
        EmployeeDTO expectedEmployeeDTO = new EmployeeDTO(
                employeeDBO.getId(),
                employeeDBO.getName(),
                employeeDBO.getEmail(),
                null,
                employeeDBO.getRole(),
                employeeDBO.getAge()
        );

        // Act
        EmployeeDTO actualEmployeeDTO = EmployeeDTO.fromDBO(employeeDBO);

        // Assert
        assertEquals(expectedEmployeeDTO, actualEmployeeDTO);
    }
}
