package br.com.juanbailke.events.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.juanbailke.events.model.User;

public interface UserRepo extends CrudRepository<User, Integer> {

    public User findByEmail(String email);
}
