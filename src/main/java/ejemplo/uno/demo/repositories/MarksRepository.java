package ejemplo.uno.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import ejemplo.uno.demo.entities.Mark;

public interface MarksRepository extends CrudRepository<Mark, Long>{

}
