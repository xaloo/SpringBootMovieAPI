package api.application.entities.dto;

import api.application.entities.dbo.ContentDBO;
import api.application.entities.dbo.EmployeeDBO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EmployeeContentDTO(EmployeeDTO employee, ContentDTO movieSerie) {

    public static EmployeeContentDTO fromDBOs(EmployeeDBO employeeDBO, ContentDBO contentDBO) {
        return new EmployeeContentDTO(
                EmployeeDTO.fromDBO(employeeDBO),
                ContentDTO.fromDBO((contentDBO))
        );
    }

}
