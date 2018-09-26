package edu.learn.mazahaireuloom.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import reactor.core.publisher.Mono;

import java.security.Principal;

@ControllerAdvice
public class SecurityControllerAdvice {

    /*CsrfToken needs spring security dependency*/
    /*@ModelAttribute
    Mono<CsrfToken> csrfToken(ServerWebExchange exchange) {
        Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
        return csrfToken.doOnSuccess(token -> exchange.getAttributes()
                .put(CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME, token));
    }*/

    @ModelAttribute("currentUser")
    Mono<Principal> currentUser(Mono<Principal> currentUser){
        return currentUser;
    }
}