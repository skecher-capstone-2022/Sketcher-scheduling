package sketcher.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sketcher.scheduling.domain.User;

public interface UserRepository extends JpaRepository<User , String> {
}
