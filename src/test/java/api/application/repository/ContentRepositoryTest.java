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
public class ContentRepositoryTest extends ContainersEnvironment {

    private final EmployeeRepository employeeRepository;
    private final ContentRepository contentRepository;
    private EmployeeDBO employeeDBOOne;
    private EmployeeDBO employeeDBOTwo;
    private ContentDBO contentDBOOne;
    private ContentDBO contentDBOTwo;
    private ContentDBO savedContentOne;
    private ContentDBO savedContentTwo;


    @Autowired
    public ContentRepositoryTest(EmployeeRepository employeeRepository, ContentRepository contentRepository) {
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

        //Save data with associated employees.
        EmployeeDBO savedEmployeeOne = employeeRepository.save(employeeDBOOne);
        EmployeeDBO savedEmployeeTwo = employeeRepository.save(employeeDBOTwo);
        contentDBOOne.setProposedByEmployee(savedEmployeeOne);
        contentDBOOne.setRegisteredByEmployee(savedEmployeeOne);
        contentDBOTwo.setProposedByEmployee(savedEmployeeTwo);
        contentDBOTwo.setRegisteredByEmployee(savedEmployeeTwo);
        savedContentOne = contentRepository.save(contentDBOOne);
        savedContentTwo = contentRepository.save(contentDBOTwo);
    }

    @Test
    public void testFindById() {
        Optional<ContentDBO> result = contentRepository.findById(savedContentOne.getId());
        assertEquals(savedContentOne.getId(), result.orElseThrow().getId());
    }

    @Test
    public void testFindByIdAndError() {
        Optional<ContentDBO> result = contentRepository.findById(savedContentTwo.getId()+1L);
        assertFalse(result.isPresent(), "Expected Optional to be empty");
    }

    @Test
    public void testFindAll() {
        List<ContentDBO> allEmployees = contentRepository.findAll();
        assertEquals(2, allEmployees.size());
    }

    @Test
    public void testDeleteById() {
        contentRepository.deleteById(savedContentOne.getId());
        Optional<ContentDBO> result = contentRepository.findById(savedContentOne.getId());
        assertFalse(result.isPresent(), "Expected Optional to be empty after deletion");
    }

    @Test
    public void testDeleteByIdAndError() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            contentRepository.deleteById(999999L);
        });
    }

    @Test
    public void testSaveContent() {
        //Check that the content was saved on beforeAll
        Optional<ContentDBO> savedContentDBO = contentRepository.findById(savedContentOne.getId());
        assertTrue(savedContentDBO.isPresent(), "Optional is empty");
    }

    @Test
    public void testSaveContentAndConstraintError() {
        assertThrows(ConstraintViolationException.class, () -> {
            contentDBOOne.setTitle(null);
            contentRepository.save(contentDBOOne);
        });
    }

    @Test
    public void testUpdateOne() {
        savedContentOne.setYear(1234);
        savedContentOne = contentRepository.save(savedContentOne);
        Optional<ContentDBO> updatedContent = contentRepository.findById(savedContentOne.getId());
        assertTrue(updatedContent.isPresent(), "Expected updated content to be present");
        assertEquals(1234, updatedContent.orElseThrow().getYear());
    }
}
