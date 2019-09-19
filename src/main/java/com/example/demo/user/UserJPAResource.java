package com.example.demo.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class UserJPAResource {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUser() {
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa//user/{id}")
	public User retrieveAllUser(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		
		// INI - HATEOAS - Quitamos por error al introducir swagger
//		EntityModel<User> model = new EntityModel<>(user);
//		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUser());
//		model.add(linkTo.withRel("ir-all-users"));
		// FIN - HATEOAS

		return user.get();
	}
	
	@DeleteMapping("/jpa/user/{id}")
	public void deleteUser(@PathVariable Integer id) {
		userRepository.deleteById(id);
	}
	
	@PostMapping("/jpa/user")
	public ResponseEntity<Object> createUser2(@Valid @RequestBody User user) {
		// Guardamos el user
		User savedUser = userRepository.save(user);
		
		// obtenemos la url
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(savedUser.getId())
		.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllUserPost(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		
		return user.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable Integer id, @RequestBody Post post) {
		Optional<User> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		
		User user = userOptional.get();
		
		post.setUser(user);
		
		postRepository.save(post);
		
		// obtenemos la url
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(post.getId())
		.toUri();
		
		return ResponseEntity.created(location).build();
	}

}
