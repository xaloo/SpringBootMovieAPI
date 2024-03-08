package api.application.repository;

import api.application.MainApplication;
import api.application.config.ContainersEnvironment;
import api.application.entities.dbo.ContentDBO;
import api.application.entities.dbo.EmployeeDBO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class EmployeeRepositoryTest extends ContainersEnvironment {

    private final EmployeeRepository employeeRepository;
    private final ContentRepository contentRepository;
    private EmployeeDBO employeeDBOOne;
    private EmployeeDBO employeeDBOTwo;
    private ContentDBO contentDBOOne;
    private ContentDBO contentDBOTwo;


    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository employeeRepository, ContentRepository contentRepository) {
        this.employeeRepository = employeeRepository;
        this.contentRepository = contentRepository;
    }

    @BeforeEach
    void setUp() {
        employeeDBOOne = new EmployeeDBO();
        employeeDBOOne.setName("Test Employee");
        employeeDBOOne.setEmail("test@example.com");
        employeeDBOOne.setPassword(new BCryptPasswordEncoder().encode("password"));
        employeeDBOOne.setRole("ROLE_USER");
        employeeDBOOne.setAge(30);

        employeeDBOTwo = new EmployeeDBO();
        employeeDBOTwo.setName("Two Employee Name");
        employeeDBOTwo.setEmail("twotest@example.com");
        employeeDBOTwo.setPassword(new BCryptPasswordEncoder().encode("password"));
        employeeDBOTwo.setRole("ROLE_USER");
        employeeDBOTwo.setAge(30);

        contentDBOOne = new ContentDBO();
        contentDBOOne.setId(1L);
        contentDBOOne.setTitle("Test Title");
        contentDBOOne.setYear(2022);
        contentDBOOne.setDirector("Test Director");
        contentDBOOne.setGenre("Test Genre");
        contentDBOOne.setLengthMinutes(120);
        contentDBOOne.setRegisterDate(LocalDate.now());
        contentDBOOne.setRating(4.5f);

        contentDBOTwo = new ContentDBO();
        contentDBOTwo.setId(1L);
        contentDBOTwo.setTitle("Best Serie Title");
        contentDBOTwo.setYear(2022);
        contentDBOTwo.setDirector("Test Director");
        contentDBOTwo.setGenre("Test Genre");
        contentDBOTwo.setSeasons(2);
        contentDBOTwo.setRegisterDate(LocalDate.now());
        contentDBOTwo.setRating(9f);
    }

    @Test
    public void testFindById() {
        EmployeeDBO savedEmployeeOne = employeeRepository.save(employeeDBOOne);
        EmployeeDBO savedEmployeeTwo = employeeRepository.save(employeeDBOTwo);
        Optional<EmployeeDBO> result = employeeRepository.findById(savedEmployeeOne.getId());
        assertEquals(savedEmployeeOne.getId(), result.orElseThrow().getId());
    }

    @Test
    public void testFindByIdAndError() {
        EmployeeDBO savedEmployeeOne = employeeRepository.save(employeeDBOOne);
        EmployeeDBO savedEmployeeTwo = employeeRepository.save(employeeDBOTwo);
        Optional<EmployeeDBO> result = employeeRepository.findById(savedEmployeeTwo.getId()+1L);
        assertFalse(result.isPresent(), "Expected Optional to be empty");
    }

    @Test
    public void testFindOneByEmail() {
        employeeRepository.save(employeeDBOOne);
        employeeRepository.save(employeeDBOTwo);
        Optional<EmployeeDBO> result = employeeRepository.findOneByEmail("test@example.com");
        assertTrue(result.isPresent(), "Expected Optional to be present");
        assertEquals("Test Employee", result.orElseThrow().getName());
    }

    @Test
    public void testFindAll() {
        employeeRepository.save(employeeDBOOne);
        employeeRepository.save(employeeDBOTwo);
        List<EmployeeDBO> allEmployees = employeeRepository.findAll();
        assertEquals(2, allEmployees.size());
    }

    @Test
    public void testDeleteById() {
        EmployeeDBO saveEmployeeOne = employeeRepository.save(employeeDBOOne);
        employeeRepository.deleteById(saveEmployeeOne.getId());
        Optional<EmployeeDBO> result = employeeRepository.findById(saveEmployeeOne.getId());
        assertFalse(result.isPresent(), "Expected Optional to be empty after deletion");
    }

    @Test
    public void testDeleteByIdAndError() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            employeeRepository.deleteById(999999L);
        });
    }

    @Test
    public void testSaveEmployee() {
        EmployeeDBO savedEmployee = employeeRepository.save(employeeDBOOne);
        Optional<EmployeeDBO> employeeDBO = employeeRepository.findById(savedEmployee.getId());
        assertTrue(employeeDBO.isPresent(), "Optional is empty");
    }

    @Test
    public void testSaveEmployeeAndConstraintError() {
        assertThrows(ConstraintViolationException.class, () -> {
            employeeDBOOne.setEmail(null);
            employeeRepository.save(employeeDBOOne);
        });
    }

    @Test
    public void testFindBestEmployeeMovie() {
        employeeDBOOne = employeeRepository.save(employeeDBOOne);
        employeeDBOTwo = employeeRepository.save(employeeDBOTwo);
        contentDBOOne.setRegisteredByEmployee(employeeDBOOne);
        contentDBOOne.setProposedByEmployee(employeeDBOOne);
        contentDBOTwo.setRegisteredByEmployee(employeeDBOTwo);
        contentDBOTwo.setProposedByEmployee(employeeDBOTwo);
        contentRepository.save(contentDBOOne);
        contentRepository.save(contentDBOTwo);
        //Retrieve
        Object[] result = employeeRepository.findBestEmployeeMovie();
        Object[] firstResult = (Object[]) result[0];
        ContentDBO content = (ContentDBO) firstResult[1];
        //Assert
        assertEquals("Best Serie Title", content.getTitle());
    }

    @Test
    public void testUpdateOne() {
        EmployeeDBO savedEmployeeOne = employeeRepository.save(employeeDBOOne);
        savedEmployeeOne.setName("Updated Name");
        savedEmployeeOne = employeeRepository.save(employeeDBOOne);
        Optional<EmployeeDBO> updatedEmployee = employeeRepository.findById(savedEmployeeOne.getId());
        assertTrue(updatedEmployee.isPresent(), "Expected updated employee to be present");
        assertEquals("Updated Name", updatedEmployee.orElseThrow().getName());
    }
}
