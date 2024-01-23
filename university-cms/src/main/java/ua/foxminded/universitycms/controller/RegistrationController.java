package ua.foxminded.universitycms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.universitycms.models.UserDto;
import ua.foxminded.universitycms.service.UserService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
	private static Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	private UserService userService;

	public RegistrationController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String registrationForm(Model model) {
		model.addAttribute("user", new UserDto());
		return "registration";
	}

	@PostMapping
	public String registration(@ModelAttribute("user") UserDto userDto, BindingResult result, Model model)
			throws Exception {
		if (userService.findByName(userDto.getName()) != null) {
			result.rejectValue("name", "There is already an account registered with the same login");
			logger.info("There is already an account registered with the same login");
		}
		if (result.hasErrors()) {
			model.addAttribute("user", userDto);
			return "registration";
		}
		userService.saveUserDto(userDto);
		return "redirect:/registration?success";
	}
}
