package api.application.repository;

import api.application.entities.dbo.ContentDBO;
import api.application.entities.dbo.EmployeeDBO;
import api.application.entities.dto.EmployeeContentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeDBO, Long> {
    Optional<EmployeeDBO> findOneByEmail(String email);

    @Query("SELECT e,c FROM ContentDBO c " +
            "left join EmployeeDBO e on e.id = c.proposedByEmployee " +
            "ORDER BY c.rating DESC")
    Object[] findBestEmployeeMovie();
}
