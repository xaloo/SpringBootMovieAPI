package api.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import api.application.entities.dbo.ContentDBO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import api.application.entities.dbo.EmployeeDBO;
import api.application.entities.dto.EmployeeContentDTO;
import api.application.entities.dto.EmployeeDTO;
import api.application.repository.EmployeeRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeDTO employeeDTO;
    private EmployeeDBO employeeDBO;

    @BeforeEach
    void setUp() {
        // Init EmployeeDTO, etc.
        employeeDTO = new EmployeeDTO(1L, "Test Employee", "test@example.com",
                "password", "ROLE_USER", 30);
        employeeDBO = new EmployeeDBO();
        employeeDBO.setId(1L);
        employeeDBO.setName("Test Employee");
        employeeDBO.setEmail("test@example.com");
        employeeDBO.setPassword(new BCryptPasswordEncoder().encode("password"));
        employeeDBO.setRole("ROLE_USER");
        employeeDBO.setAge(30);
    }

    @Test
    void testCreate() {
        // Arrange
        when(employeeRepository.save(any(EmployeeDBO.class))).thenReturn(employeeDBO);

        // Act
        EmployeeDTO createdEmployeeDTO = employeeService.create(employeeDTO);

        // Assert
        assertNotNull(createdEmployeeDTO.id());
        assertEquals(employeeDTO.name(), createdEmployeeDTO.name());
        assertEquals(employeeDTO.email(), createdEmployeeDTO.email());
        assertEquals(employeeDTO.role(), createdEmployeeDTO.role());
        assertEquals(employeeDTO.age(), createdEmployeeDTO.age());
    }

    @Test
    void testFindById() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeDBO));

        // Act
        EmployeeDTO foundEmployeeDTO = employeeService.findById(1L);

        // Assert
        assertEquals(employeeDTO.name(), foundEmployeeDTO.name());
        assertEquals(employeeDTO.email(), foundEmployeeDTO.email());
        assertEquals(employeeDTO.role(), foundEmployeeDTO.role());
        assertEquals(employeeDTO.age(), foundEmployeeDTO.age());
    }

    @Test
    void testFindAndError() {
        // Throw exception always that does not find the entity
        // Assert
        assertThrows(ResponseStatusException.class, () -> employeeService.findById(1L));
    }

    @Test
    void testFindAll() {
        // Arrange
        when(employeeRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<EmployeeDTO> employeeDTOList = employeeService.findAll();

        // Assert
        assertNotNull(employeeDTOList);
    }

    @Test
    void testUpdate() {
        // Arrange
        when(employeeRepository.findById(any())).thenReturn(Optional.ofNullable(employeeDBO));

        // Act
        EmployeeDTO updatedEmployeeDTO = employeeService.update(employeeDTO);

        // Assert
        assertEquals(employeeDTO.id(), updatedEmployeeDTO.id());
        assertEquals(employeeDTO.name(), updatedEmployeeDTO.name());
        assertEquals(employeeDTO.role(), updatedEmployeeDTO.role());
        assertEquals(employeeDTO.age(), updatedEmployeeDTO.age());
    }

    @Test
    void testDelete() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(new EmployeeDBO()));

        // Act
        assertDoesNotThrow(() -> employeeService.delete(1L));
    }

    @Test
    void testFindBestEmployeeMovie() {
        // Arrange
        EmployeeDBO employeeDBO = new EmployeeDBO();
        employeeDBO.setId(1L);
        employeeDBO.setName("Javier");
        employeeDBO.setEmail("Javier@example.com");
        employeeDBO.setPassword("1234");
        employeeDBO.setRole("Backend");
        employeeDBO.setAge(32);

        ContentDBO contentDBO = new ContentDBO();
        contentDBO.setId(1L);
        contentDBO.setTitle("Test Movie");
        contentDBO.setYear(2022);
        contentDBO.setDirector("Test Director");
        contentDBO.setGenre("Action");
        contentDBO.setLengthMinutes(null);
        contentDBO.setSeasons(3);
        contentDBO.setRegisterDate(LocalDate.now());
        contentDBO.setRating(9.5f);
        contentDBO.setProposedByEmployee(employeeDBO);
        contentDBO.setRegisteredByEmployee(employeeDBO);

        Object[] innerArray = new Object[]{employeeDBO, contentDBO};

        when(employeeRepository.findBestEmployeeMovie()).thenReturn(new Object[]{innerArray});

        // Act
        EmployeeContentDTO bestEmployeeMovie = employeeService.findBestEmployeeMovie();

        // Assert
        assertNotNull(bestEmployeeMovie);
        assertEquals(employeeDBO.getId(), bestEmployeeMovie.employee().id());
        assertEquals(employeeDBO.getName(), bestEmployeeMovie.employee().name());
        assertEquals(employeeDBO.getEmail(), bestEmployeeMovie.employee().email());
        assertEquals(employeeDBO.getRole(), bestEmployeeMovie.employee().role());
        assertEquals(employeeDBO.getAge(), bestEmployeeMovie.employee().age());
    }




}

