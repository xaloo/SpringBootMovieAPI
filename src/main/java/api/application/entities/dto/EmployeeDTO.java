package api.application.entities.dto;

import api.application.entities.dbo.EmployeeDBO;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.lang.Nullable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EmployeeDTO(@Nullable Long id,
                          @NotNull @Size(max = 255) String name,
                          @NotNull @Email @Size(max = 255) String email,
                          @Size(max = 255) String password,
                          @NotNull @Size(max = 50) String role,
                          @NotNull Integer age) {

    public static EmployeeDTO fromDBO(EmployeeDBO employeeDBO) {
        return new EmployeeDTO(
                employeeDBO.getId(),
                employeeDBO.getName(),
                employeeDBO.getEmail(),
                null,
                employeeDBO.getRole(),
                employeeDBO.getAge()
        );
    }
}
