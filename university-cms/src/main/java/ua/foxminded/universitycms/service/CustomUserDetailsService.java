package ua.foxminded.universitycms.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ua.foxminded.universitycms.models.User;
import ua.foxminded.universitycms.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	private UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			logger.info("AUTHENTIFICAITED : {}", user.get().getUsername().trim());
			return new org.springframework.security.core.userdetails.User(user.get().getUsername().trim(),
					user.get().getPassword().trim(),
					user.get().getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName().trim()))
							.collect(Collectors.toList()));
		} else {
			throw new UsernameNotFoundException("Invalid username or password");
		}
	}
}
