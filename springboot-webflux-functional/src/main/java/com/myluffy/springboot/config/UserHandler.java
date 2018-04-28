package com.myluffy.springboot.config;

import com.myluffy.springboot.entity.User;
import com.myluffy.springboot.service.IUserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;


@Component
public class UserHandler {
    private IUserService userService;

    public UserHandler(IUserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        System.out.println("=====");
        // parse path-variable
        final long l = Long.parseLong(request.pathVariable("id"));
        // 获取数据
        final Mono<User> user = userService.getUserById(l);

        // build notFound response
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return user
                .flatMap(customer -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(customer)))
                .switchIfEmpty(notFound);

    }

    public Mono<ServerResponse> getUsers(ServerRequest request) {
        final Flux<User> allUsers = userService.getAllUsers();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(allUsers, User.class);
    }

    public Mono<ServerResponse> postUser(ServerRequest request) {
        Mono<User> customer = request.bodyToMono(User.class);
        return ServerResponse.ok().build(userService.saveUser(customer));
    }

    public Mono<ServerResponse> putUser(ServerRequest request) {
        // parse path-variable
        final long l = Long.parseLong(request.pathVariable("id"));
        // get customer data from request object
        Mono<User> customer = request.bodyToMono(User.class);

        // get customer for userService
        Mono<User> responseMono = userService.putUser(l, customer);

        return responseMono
                .flatMap(cust -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(cust)));
    }


    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        // parse id from path-variable
        long customerId = Long.valueOf(request.pathVariable("id"));

        // 获取数据
        Mono<String> responseMono = userService.deleteUser(customerId);

        // build response
        return responseMono
                .flatMap(strMono -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(fromObject(strMono)));
    }
}