package api.application.services;

import api.application.entities.dto.ContentDTO;
import api.application.entities.filter.ContentFilterDBO;

import java.util.List;

public interface ContentService {
    ContentDTO create(ContentDTO contentDTO);
    ContentDTO findById(Long id);
    List<ContentDTO> findAllWithFilters(ContentFilterDBO contentFilterDBO);
    List<ContentDTO> findAllSeries();
    List<ContentDTO> findAllMovies();
    ContentDTO update(ContentDTO contentDTO);
    void delete(Long id);
}
