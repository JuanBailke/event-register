package br.com.juanbailke.events.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.juanbailke.events.model.Event;

public interface EventRepo extends CrudRepository<Event, Integer> {

    public Event findByPrettyName(String prettyName);
}
