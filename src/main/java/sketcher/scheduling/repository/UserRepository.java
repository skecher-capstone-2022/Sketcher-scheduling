package sketcher.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sketcher.scheduling.domain.User;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User , String> {
    Optional<User> findById(String id);
    List<User> findAll();
}
