package br.com.juanbailke.events.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.juanbailke.events.model.Event;
import br.com.juanbailke.events.model.Subscription;
import br.com.juanbailke.events.model.User;

public interface SubscriptionRepo extends CrudRepository<Subscription, Integer> {

    public Subscription findByEventAndSubscriber(Event event, User user);

}
