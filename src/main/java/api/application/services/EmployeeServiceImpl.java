package api.application.services;

import api.application.entities.dbo.ContentDBO;
import api.application.entities.dbo.EmployeeDBO;
import api.application.entities.dto.EmployeeContentDTO;
import api.application.entities.dto.EmployeeDTO;
import api.application.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDTO create(EmployeeDTO employee) {
        EmployeeDBO employeeDBO = new EmployeeDBO();
        employeeDBO.setName(employee.name());
        employeeDBO.setEmail(employee.email());
        employeeDBO.setPassword(bCryptPasswordEncoder.encode(employee.password()));
        employeeDBO.setRole(employee.role());
        employeeDBO.setAge(employee.age());
        employeeDBO = employeeRepository.save(employeeDBO);
        return EmployeeDTO.fromDBO(employeeDBO);
    }

    @Override
    public EmployeeDTO findById(Long id) {
        EmployeeDBO employeeDBO = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Employee with id %d not found",id)));
        return EmployeeDTO.fromDBO(employeeDBO);
    }

    @Override
    public List<EmployeeDTO> findAll() {
        List<EmployeeDBO> employeeDBOs = employeeRepository.findAll();
        return employeeDBOs.stream().map(EmployeeDTO::fromDBO).toList();
    }

    @Override
    public EmployeeDTO update(EmployeeDTO employeeDTO) {
        EmployeeDBO employeeDBO = validateUpdate(employeeDTO);
        //Update values without password and email that should not be changed
        employeeDBO.setName(employeeDTO.name());
        employeeDBO.setRole(employeeDTO.role());
        employeeDBO.setAge(employeeDTO.age());
        return EmployeeDTO.fromDBO(employeeDBO);
    }

    @Override
    public void delete(Long id) {
        try {
            EmployeeDBO employeeDBO = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Employee with id %d not found",id)));
            employeeRepository.delete(employeeDBO);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format("Cannot delete Employee with id %d. " +
                            "Remove the movies or series associated with this employee first", id), ex);
        }
    }

    @Override
    public EmployeeContentDTO findBestEmployeeMovie() {
        Object[] result = employeeRepository.findBestEmployeeMovie();
        if (Objects.nonNull(result) && result.length > 0 ) {
            Object[] firstResult = (Object[]) result[0];
            EmployeeDBO employee = (EmployeeDBO) firstResult[0];
            ContentDBO content = (ContentDBO) firstResult[1];
            return EmployeeContentDTO.fromDBOs(employee, content);
        } else {
            return null;//There is no best movie or serie
        }
    }

    private EmployeeDBO validateUpdate(EmployeeDTO employeeDTO) {
        if (Objects.isNull(employeeDTO.id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Employee id must be informed on update operations");
        }
        return employeeRepository.findById(employeeDTO.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Employee with id %d not found",employeeDTO.id())));
    }

}
