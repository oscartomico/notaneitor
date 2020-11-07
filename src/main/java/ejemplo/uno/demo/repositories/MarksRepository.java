package ejemplo.uno.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ejemplo.uno.demo.entities.Mark;
import ejemplo.uno.demo.entities.User;

public interface MarksRepository extends CrudRepository<Mark, Long>{
	@Modifying
	@Transactional
	@Query("UPDATE Mark SET resend = ?1 WHERE id = ?2")
	void updateResend(Boolean resend, Long id);
	
	@Query("SELECT mark FROM Mark mark WHERE mark.user = ?1 ORDER BY mark.id ASC")
	List<Mark> findAllByUser(User user);
}
