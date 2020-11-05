package ejemplo.uno.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import ejemplo.uno.demo.entities.Mark;
import ejemplo.uno.demo.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {
	
	User findByDni(String dni);
}
