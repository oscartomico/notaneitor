package ejemplo.uno.demo.controllers;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ejemplo.uno.demo.entities.Mark;
import ejemplo.uno.demo.entities.User;
import ejemplo.uno.demo.services.MarksService;
import ejemplo.uno.demo.services.UsersService;

@Controller
public class MarksController {
	
	@Autowired //Inyectar el servicio
	private MarksService marksService;

	@Autowired 
	private UsersService usersService;
	
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/mark/list/update")
	public String updateList(Model model, Principal principal){
		User user = usersService.findUserByDni(principal.getName());
		model.addAttribute("markList", marksService.getMarksForUser(user));
		return "mark/list :: tableMarks";
	}
		
	@RequestMapping("/mark/list")
	public String getList(Model model, Principal principal){		
		User user = usersService.findUserByDni(principal.getName());
		model.addAttribute("markList", marksService.getMarksForUser(user));
		return "mark/list";
	}

	@RequestMapping(value="/mark/add", method=RequestMethod.POST )
	public String setMark(@ModelAttribute Mark mark){
		marksService.addMark(mark);
		return "redirect:/mark/list";
	}

	@RequestMapping("/mark/details/{id}" )
	public String getDetail(Model model, @PathVariable Long id){
		model.addAttribute("mark", marksService.getMark(id));
		return "mark/details";
	}


	@RequestMapping("/mark/delete/{id}" )
	public String deleteMark(@PathVariable Long id){
		marksService.deleteMark(id);
		return "redirect:/mark/list";
	}
	
	@RequestMapping(value="/mark/add")
	public String getMark(Model model){
 		// Enviar la lista de usuarios
		model.addAttribute("usersList", usersService.getUsers());
		return "mark/add";
	}
	
	@RequestMapping(value="/mark/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id){
		model.addAttribute("mark", marksService.getMark(id));
		model.addAttribute("usersList", usersService.getUsers());
		return "mark/edit";
	}

	@RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
	public String setEdit(@PathVariable Long id, @ModelAttribute Mark mark){
		Mark original = marksService.getMark(id);
		// modificar solo score y description
		original.setScore(mark.getScore());
		original.setDescription(mark.getDescription());
		marksService.addMark(original);
		return "redirect:/mark/details/"+id;
	}
	
	@RequestMapping(value="/mark/{id}/resend", method=RequestMethod.GET)
	public String setResendTrue(@PathVariable Long id) {
		marksService.setMarkResend(true, id);
		return "redirect:/mark/list";
	}
	
	@RequestMapping(value="/mark/{id}/noresend", method=RequestMethod.GET)
	public String setResendFalse(@PathVariable Long id) {
		marksService.setMarkResend(false, id);
		return "redirect:/mark/list";
	}
}
