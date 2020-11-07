package ejemplo.uno.demo.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ejemplo.uno.demo.entities.Mark;
import ejemplo.uno.demo.repositories.MarksRepository;

@Service
public class MarksService {
	@Autowired
	private MarksRepository marksRepository;

	@Autowired
	private HttpSession httpSession;

	public List<Mark> getMarks() {
		List<Mark> lista = new ArrayList<>();
		marksRepository.findAll().forEach(lista::add);
		return lista;
	}

	public void addMark(Mark mark) {
		marksRepository.save(mark);
	}

	public void deleteMark(Long id) {
		marksRepository.deleteById(id);
	}

	public Mark getMark(Long id) {
		Set<Mark> consultedList = (Set<Mark>) httpSession.getAttribute("consultedList");
		if (consultedList == null) {
			consultedList = new HashSet<Mark>();
		}
		Mark obtainedmark = marksRepository.findById(id).get();
		consultedList.add(obtainedmark);
		httpSession.setAttribute("consultedList", consultedList);
		return obtainedmark;
	}

	public void setMarkResend(boolean resend, Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String dni = auth.getName();

		Mark mark = marksRepository.findById(id).get();

		if (mark.getUser().getDni().equals(dni)) {
			marksRepository.updateResend(resend, id);
		}
	}
}
