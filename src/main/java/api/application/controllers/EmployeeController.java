package api.application.controllers;

import api.application.entities.dto.EmployeeContentDTO;
import api.application.entities.dto.EmployeeDTO;
import api.application.services.EmployeeService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = EmployeeDTO.class)))
    @ApiResponse(responseCode = "404", description = "Employee not found")
    public EmployeeDTO findById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @GetMapping()
    public List<EmployeeDTO> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/bestMovie")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = EmployeeDTO.class)))
    @ApiResponse(responseCode = "404", description = "Employee with the best movie or serie does not exist.")
    public EmployeeContentDTO findBestEmployeeMovie() {
        return employeeService.findBestEmployeeMovie();
    }

    @PostMapping()
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = EmployeeDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    public EmployeeDTO create(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return employeeService.create(employeeDTO);
    }

    @PutMapping
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = EmployeeDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    public EmployeeDTO update(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return employeeService.update(employeeDTO);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = EmployeeDTO.class)))
    @ApiResponse(responseCode = "404", description = "Employee not found")
    void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }
}
