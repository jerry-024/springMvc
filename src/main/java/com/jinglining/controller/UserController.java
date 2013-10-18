package com.jinglining.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jinglining.pojo.User;
import com.jinglining.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Inject
	private UserService userService;
	@RequestMapping(value="/list")
	public String list(Model model){
		
		model.addAttribute("userList", userService.findAll());
		return "user/list";
		
	}
	@RequestMapping(value="/{id:\\d+}",method=RequestMethod.GET)
	public String show(@PathVariable int id,Model model){
		model.addAttribute("user", userService.findById(id));
		return "user/edit";
	}
	@RequestMapping(value="/{id:\\d+}",method=RequestMethod.DELETE)
	public String del(@PathVariable int id){
		
		userService.del(id);
		return "user/list";
	}
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(@Valid User user,BindingResult bindingResult){
		if(bindingResult.hasErrors()) {

			return "user/add";

		}

		userService.save(user);

		return "redirect:/user/list";

	}


		
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
		User u = new User();
		model.addAttribute("user", u);
		return "user/add";
	}
}
