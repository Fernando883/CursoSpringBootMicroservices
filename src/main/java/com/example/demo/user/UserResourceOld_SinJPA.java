package com.example.demo.user;

import java.net.URI;
import java.util.List;

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
public class UserResourceOld_SinJPA {
	
	@Autowired
	private UserDaoService service;
	
	@GetMapping("/users")
	public List<UserOld_SinJPA> retrieveAllUser() {
		return service.findAll();
	}
	
	@GetMapping("/user/{id}")
	public UserOld_SinJPA retrieveAllUser(@PathVariable Integer id) {
		UserOld_SinJPA user = service.findOne(id);
		if(user == null) {
			throw new UserNotFoundException("id-"+id);
		}
		
		// INI - HATEOAS - Quitamos por error al introducir swagger
//		EntityModel<User> model = new EntityModel<>(user);
//		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUser());
//		model.add(linkTo.withRel("ir-all-users"));
		// FIN - HATEOAS

		return user;
	}
	
	@PostMapping("/user")
	public void createUser(@Valid @RequestBody UserOld_SinJPA user) {
		UserOld_SinJPA savedUser = service.save(user);
	}
	
	@PostMapping("/user2")
	public ResponseEntity<Object> createUser2(@Valid @RequestBody UserOld_SinJPA user) {
		// Guardamos el user
		UserOld_SinJPA savedUser = service.save(user);
		
		// obtenemos la url
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(savedUser.getId())
		.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable Integer id) {
		UserOld_SinJPA userDelete = service.deleteById(id);
		if(userDelete == null) {
			throw new UserNotFoundException("id-"+id);
		}
	}
	
	@DeleteMapping("/user1/{id}")
	public Boolean deleteUserOwn(@PathVariable Integer id) {
		Boolean delete = service.deleteByIdOwn(id);
		if(!delete) {
			throw new UserNotFoundException("id-"+id);
		}
		return delete;
	}
	

}
