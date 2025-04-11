package br.com.juanbailke.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.juanbailke.events.dto.ErrorMessage;
import br.com.juanbailke.events.dto.SubscriptionResponse;
import br.com.juanbailke.events.exception.EventNotFoundException;
import br.com.juanbailke.events.exception.SubscriptionConflictException;
import br.com.juanbailke.events.exception.UserIndicadorNotFoundException;
import br.com.juanbailke.events.model.Subscription;
import br.com.juanbailke.events.model.User;
import br.com.juanbailke.events.service.SubscriptionService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping({"/subscription/{prettyName}", "/subscription/{prettyName}/{userId}"})
    public ResponseEntity<?> createSubscription(@PathVariable String prettyName, @RequestBody User subscriber, @PathVariable(required = false) Integer userId) {
        try{
            SubscriptionResponse res = subscriptionService.createNewSubscription(prettyName, subscriber, userId);
            if(res != null) {
                return ResponseEntity.ok().body(res);
            }
        }
        catch(EventNotFoundException exception){
            return ResponseEntity.status(404).body(new ErrorMessage(exception.getMessage()));
        }
        catch(SubscriptionConflictException excetion){
            return ResponseEntity.status(409).body(new ErrorMessage(excetion.getMessage()));
        }
        catch(UserIndicadorNotFoundException exception){
            return ResponseEntity.status(404).body(new ErrorMessage(exception.getMessage()));
        }
        return ResponseEntity.badRequest().build();
        
    }
    
}
