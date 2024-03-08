package api.application.services;

import api.application.entities.dbo.EmployeeDBO;
import api.application.entities.dbo.ContentDBO;
import api.application.entities.dto.ContentDTO;
import api.application.entities.filter.ContentFilterDBO;
import api.application.repository.EmployeeRepository;
import api.application.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class ContentServiceImpl implements ContentService {

    private static final String GENRE_FIELD = "genre";
    private static final String RATING_FIELD = "rating";
    private static final String YEAR_FIELD = "year";

    private final ContentRepository contentRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ContentServiceImpl(ContentRepository movieRepository,
                              EmployeeRepository employeeRepository) {
        this.contentRepository = movieRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ContentDTO create(ContentDTO contentDTO) {
        validateMovieOrSeries(contentDTO);
        Long proposedByEmployeeId = contentDTO.proposedByEmployeeId();
        Long registeredByEmployeeId = contentDTO.registeredByEmployeeId();
        EmployeeDBO proposedByEmployee = employeeRepository.findById(contentDTO.proposedByEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Employee with id %d not found",proposedByEmployeeId)));
        EmployeeDBO registeredByEmployee = employeeRepository.findById(contentDTO.registeredByEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Employee with id %d not found",registeredByEmployeeId)));
        ContentDBO entity = new ContentDBO(
                null,
                contentDTO.title(),
                contentDTO.year(),
                contentDTO.director(),
                contentDTO.genre(),
                contentDTO.minutesLength(),
                contentDTO.seasons(),
                LocalDate.now(),
                contentDTO.rating(),
                proposedByEmployee,
                registeredByEmployee
        );
        contentRepository.save(entity);
        return ContentDTO.fromDBO(entity);
    }

    @Override
    public ContentDTO findById(Long id) {
        ContentDBO movieDBO = contentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Content with id %d not found",id)));
        return ContentDTO.fromDBO(movieDBO);
    }

    @Override
    public List<ContentDTO> findAllWithFilters(ContentFilterDBO contentFilterDBO) {
        List<ContentDBO> contentDBOList = contentRepository.findAll(getSpecification(contentFilterDBO));
        return contentDBOList.stream().map(ContentDTO::fromDBO).toList();
    }

    @Override
    public List<ContentDTO> findAllSeries() {
        List<ContentDBO> contentDBOList = contentRepository.findAllSeries();
        return contentDBOList.stream().map(ContentDTO::fromDBO).toList();
    }

    @Override
    public List<ContentDTO> findAllMovies() {
        List<ContentDBO> contentDBOList = contentRepository.findAllMovies();
        return contentDBOList.stream().map(ContentDTO::fromDBO).toList();
    }

    @Override
    public ContentDTO update(ContentDTO contentDTO) {
        validateMovieOrSeries(contentDTO);
        ContentDBO contentDBO = validateUpdate(contentDTO);
        contentDBO.setTitle(contentDTO.title());
        contentDBO.setYear(contentDTO.year());
        contentDBO.setDirector(contentDTO.director());
        contentDBO.setRating(contentDTO.rating());
        contentDBO.setLengthMinutes(contentDTO.minutesLength());
        contentDBO.setSeasons(contentDTO.seasons());
        return ContentDTO.fromDBO(contentRepository.save(contentDBO));
    }

    @Override
    public void delete(Long id) {
        ContentDBO contentDBO = contentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Content with id %d not found",id)));
        contentRepository.delete(contentDBO);
    }

    private ContentDBO validateUpdate(ContentDTO contentDTO) {
        validateMovieOrSeries(contentDTO);
        if (Objects.isNull(contentDTO.id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Content id must be informed on update operations");
        }
        return contentRepository.findById(contentDTO.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Content with id %d not found",contentDTO.id())));
    }

    private void validateMovieOrSeries(ContentDTO contentDTO) {
        //Check that we have minutesLength (movies) only or seasons only (Series)
        if ((Objects.isNull(contentDTO.minutesLength()) && Objects.isNull(contentDTO.seasons())) ||
                (Objects.nonNull(contentDTO.minutesLength()) && Objects.nonNull(contentDTO.seasons()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Movies should only have the 'minutesLength' value informed " +
                            "and the series should only have the number of 'seasons'");
        }
    }

    private Specification<ContentDBO> getSpecification(ContentFilterDBO filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (Objects.nonNull(filter)) {
                if (Objects.nonNull(filter.year())) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(YEAR_FIELD), filter.year()));
                }
                if (Objects.nonNull(filter.rating())) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(RATING_FIELD), filter.rating()));
                }
                if (Objects.nonNull(filter.genre())) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(GENRE_FIELD), "%" + filter.genre() + "%"));
                }
            }
            return predicate;
        };
    }

}
