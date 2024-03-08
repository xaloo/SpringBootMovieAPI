package api.application.repository;

import api.application.entities.dbo.ContentDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ContentRepository extends JpaRepository<ContentDBO, Long>, JpaSpecificationExecutor<ContentDBO> {

    @Query("SELECT c FROM ContentDBO c WHERE c.lengthMinutes is null")
    List<ContentDBO> findAllSeries();
    @Query("SELECT c FROM ContentDBO c WHERE c.seasons is null")
    List<ContentDBO> findAllMovies();

}
