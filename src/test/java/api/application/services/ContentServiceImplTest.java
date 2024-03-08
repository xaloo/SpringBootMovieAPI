package api.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import api.application.entities.dbo.ContentDBO;
import api.application.entities.dbo.EmployeeDBO;
import api.application.entities.dto.ContentDTO;
import api.application.repository.ContentRepository;
import api.application.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class ContentServiceImplTest {

    @Mock
    private ContentRepository contentRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private ContentServiceImpl contentService;

    private EmployeeDBO proposedByEmployee;
    private EmployeeDBO registeredByEmployee;
    private ContentDTO contentDTO;
    private ContentDBO contentDBO;

    @BeforeEach
    void setUp() {
        //Init ContentDTO, etc.
        contentDTO = new ContentDTO(1L, "Test Title", 2022, "Test Director",
                "Test Genre", 120, null, null, 4.5f, 1L, 2L);
        proposedByEmployee = new EmployeeDBO();
        proposedByEmployee.setId(1L);
        registeredByEmployee= new EmployeeDBO();
        registeredByEmployee.setId(2L);
        contentDBO = new ContentDBO(
                1L,
                contentDTO.title(),
                contentDTO.year(),
                contentDTO.director(),
                contentDTO.genre(),
                contentDTO.minutesLength(),
                contentDTO.seasons(),
                LocalDate.now(),
                contentDTO.rating(),
                proposedByEmployee,
                registeredByEmployee);
    }

    @Test
    void testCreate() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(proposedByEmployee));
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(registeredByEmployee));
        // Act
        ContentDTO createdContentDTO = contentService.create(contentDTO);

        // Assert
        assertEquals(contentDTO.title(), createdContentDTO.title());
        assertEquals(contentDTO.year(), createdContentDTO.year());
        assertEquals(contentDTO.director(), createdContentDTO.director());
        assertEquals(contentDTO.genre(), createdContentDTO.genre());
        assertEquals(contentDTO.minutesLength(), createdContentDTO.minutesLength());
        assertEquals(contentDTO.seasons(), createdContentDTO.seasons());
        assertEquals(contentDTO.rating(), createdContentDTO.rating());
        assertEquals(contentDTO.proposedByEmployeeId(), createdContentDTO.proposedByEmployeeId());
        assertEquals(contentDTO.registeredByEmployeeId(), createdContentDTO.registeredByEmployeeId());
    }

    @Test
    void testCreateAndError() {
        ContentDTO contentDTOWithoutNulls = new ContentDTO(1L, "Test Title", 2022, "Test Director",
                "Test Genre", 120, 12, null, 4.5f, 1L, 2L);
        ContentDTO contentDTOWithNulls = new ContentDTO(1L, "Test Title", 2022, "Test Director",
                "Test Genre", null, null, null, 4.5f, 1L, 2L);

        // Assert
        assertThrows(ResponseStatusException.class, () -> contentService.create(contentDTOWithoutNulls));
        assertThrows(ResponseStatusException.class, () -> contentService.create(contentDTOWithNulls));
    }

    @Test
    void testFindById() {
        // Arrange
        when(contentRepository.findById(1L)).thenReturn(Optional.of(contentDBO));

        // Act
        ContentDTO foundContentDTO = contentService.findById(1L);

        // Assert
        assertEquals(contentDTO.title(), foundContentDTO.title());
        assertEquals(contentDTO.year(), foundContentDTO.year());
        assertEquals(contentDTO.director(), foundContentDTO.director());
        assertEquals(contentDTO.genre(), foundContentDTO.genre());
        assertEquals(contentDTO.minutesLength(), foundContentDTO.minutesLength());
        assertEquals(contentDTO.seasons(), foundContentDTO.seasons());
        assertEquals(contentDTO.rating(), foundContentDTO.rating());
        assertEquals(contentDTO.proposedByEmployeeId(), foundContentDTO.proposedByEmployeeId());
        assertEquals(contentDTO.registeredByEmployeeId(), foundContentDTO.registeredByEmployeeId());
    }

    @Test
    void testFindAndError() {
        //Throw exception always that does not find the entity
        // Assert
        assertThrows(ResponseStatusException.class, () -> contentService.findById(1L));
    }

    @Test
    void testFindAllSeries() {
        // Arrange
        when(contentRepository.findAllSeries()).thenReturn(new ArrayList<>());
        // Act
        List<ContentDTO> contentDTOList = contentService.findAllSeries();
        // Assert
        assertNotNull(contentDTOList);
    }

    @Test
    void testFindAllMovies() {
        // Arrange
        when(contentRepository.findAllMovies()).thenReturn(new ArrayList<>());
        // Act
        List<ContentDTO> contentDTOList = contentService.findAllMovies();
        // Assert
        assertNotNull(contentDTOList);
    }

    @Test
    void testUpdate() {
        // Arrange
        when(contentRepository.findById(any())).thenReturn(Optional.ofNullable(contentDBO));
        when(contentRepository.save(any())).thenReturn(contentDBO);
        // Act
        ContentDTO updatedContentDTO = contentService.update(contentDTO);

        // Assert
        assertEquals(contentDTO.id(), updatedContentDTO.id());
        assertEquals(contentDTO.title(), updatedContentDTO.title());
    }

    @Test
    void testDelete() {
        // Arrange
        when(contentRepository.findById(1L)).thenReturn(Optional.of(new ContentDBO()));
        // Act
        assertDoesNotThrow(() -> contentService.delete(1L));
    }

}

