package api.application.services;

import api.application.entities.dto.EmployeeContentDTO;
import api.application.entities.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO create(EmployeeDTO employee);
    EmployeeDTO findById(Long id);
    List<EmployeeDTO> findAll();
    EmployeeDTO update(EmployeeDTO employeeDTO);
    void delete(Long id);
    EmployeeContentDTO findBestEmployeeMovie();
}
