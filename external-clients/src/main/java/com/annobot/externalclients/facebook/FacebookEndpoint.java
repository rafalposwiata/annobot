package com.annobot.externalclients.facebook;

import com.annobot.externalclients.facebook.model.Callback;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/facebook")
public class FacebookEndpoint {

    private FacebookService facebookService;

    public FacebookEndpoint(FacebookService facebookService) {
        this.facebookService = facebookService;
    }

    @GetMapping("/webhook/{botName}")
    public ResponseEntity verification(@PathVariable("botName") String botName,
                                       @RequestParam("hub.verify_token") String verifyToken,
                                       @RequestParam("hub.mode") String mode,
                                       @RequestParam("hub.challenge") String challenge) {
        if (facebookService.verify(botName, verifyToken, mode)) {
            return ResponseEntity.ok(challenge);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ResponseBody
    @PostMapping("/webhook/{botName}")
    public ResponseEntity receiveMessage(@PathVariable("botName") String botName,
                                         @RequestBody Callback callback) {
        return facebookService.receiveMessage(botName, callback);
    }
}
