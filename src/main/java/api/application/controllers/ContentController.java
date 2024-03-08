package api.application.controllers;

import api.application.entities.dto.ContentDTO;
import api.application.entities.filter.ContentFilterDBO;
import api.application.services.ContentService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService movieService) {
        this.contentService = movieService;
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContentDTO.class)))
    @ApiResponse(responseCode = "404", description = "Movie or serie not found")
    public ContentDTO findById(@PathVariable Long id) {
        return contentService.findById(id);
    }

    @GetMapping()
    public List<ContentDTO> findAll(@RequestParam(value = "year", required = false) Integer year,
                                    @RequestParam(value = "rating", required = false) Float rating,
                                    @RequestParam(value = "genre", required = false) String genre) {
        ContentFilterDBO contentFilterDB = ContentFilterDBO.builder()
                .year(year)
                .rating(rating)
                .genre(genre)
                .build();
        return contentService.findAllWithFilters(contentFilterDB);
    }

    @GetMapping("/series")
    public List<ContentDTO> findAllSeries() {
        return contentService.findAllSeries();
    }

    @GetMapping("/movies")
    public List<ContentDTO> findAllMovies() {
        return contentService.findAllMovies();
    }

    @PostMapping()
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ContentDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Registered or proposed by employee not found")
    public ContentDTO create(@RequestBody @Valid ContentDTO contentDTO) {
        return contentService.create(contentDTO);
    }

    @PutMapping
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ContentDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Not found")
    public ContentDTO update(@RequestBody @Valid ContentDTO contentDTO) {
        return contentService.update(contentDTO);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContentDTO.class)))
    @ApiResponse(responseCode = "404", description = "Movie or serie not found")
    public void delete(@PathVariable Long id) {
        contentService.delete(id);
    }

}