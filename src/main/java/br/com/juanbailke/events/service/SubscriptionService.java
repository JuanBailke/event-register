package br.com.juanbailke.events.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.juanbailke.events.dto.SubscriptionResponse;
import br.com.juanbailke.events.exception.EventNotFoundException;
import br.com.juanbailke.events.exception.SubscriptionConflictException;
import br.com.juanbailke.events.exception.UserIndicadorNotFoundException;
import br.com.juanbailke.events.model.Event;
import br.com.juanbailke.events.model.Subscription;
import br.com.juanbailke.events.model.User;
import br.com.juanbailke.events.repository.EventRepo;
import br.com.juanbailke.events.repository.SubscriptionRepo;
import br.com.juanbailke.events.repository.UserRepo;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId) {
        Event event = eventRepo.findByPrettyName(eventName);
        if(event == null){
            throw new EventNotFoundException("Evento " + eventName + " não encontrado!");
        }
        User userRecuperado = userRepo.findByEmail(user.getEmail());
        if (userRecuperado == null) {
            userRecuperado = userRepo.save(user);
        }

        User indicador = userRepo.findById(userId).orElse(null);
        if (indicador == null){
            throw new UserIndicadorNotFoundException("Usuário " +userId +" indicador não encontrado!");
        }

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscriber(userRecuperado);
        subscription.setIndication(indicador);

        Subscription tmpSubscription = subscriptionRepo.findByEventAndSubscriber(event, userRecuperado);
        if(tmpSubscription != null) {
            throw new SubscriptionConflictException("Usuário " + userRecuperado.getName() + " já está inscrito no evento " + event.getTitle() + "!");
        }

        Subscription savedSubscription = subscriptionRepo.save(subscription);
        return new SubscriptionResponse(savedSubscription.getSubscriptionNumber(), "http://codecraft.com/subscription/"+savedSubscription.getEvent().getPrettyName()+"/"+savedSubscription.getSubscriber().getId());
    }
}
