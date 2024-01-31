package org.example.service.impl;

import org.example.model.dto.GameViewDto;
import org.example.model.entity.Game;
import org.example.model.entity.Order;
import org.example.model.entity.User;
import org.example.repository.GameRepository;
import org.example.repository.OrderRepository;
import org.example.service.OrderService;
import org.example.service.UserService;
import org.example.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final GameRepository gameRepository;
    private final UserService userService;
    private final ValidationUtil validationUtil;

    public OrderServiceImpl(OrderRepository orderRepository, GameRepository gameRepository, UserService userService, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.orderRepository = orderRepository;
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.validationUtil = validationUtil;
    }


    @Override
    public void addGameToCart(GameViewDto gameViewDto) {
        if (!validationUtil.isValid(gameViewDto)) {
            System.out.println("Invalid data");
            return;
        }
        if (userService.getLoggedInUser() == null) {
            System.out.println("No user is logged in. Please login");
            return;
        }
        User loggedInUser = this.userService.getLoggedInUser();
        boolean isGameBought = loggedInUser.getGames().stream().anyMatch(game -> game.getTitle().equals(gameViewDto.getTitle()));
        if (isGameBought) {
            System.out.println("Game already bought");
            return;
        }
        Game game = gameRepository.findByTitle(gameViewDto.getTitle());
        Order order;
        if (loggedInUser.getOrder() == null) {
            order = new Order();
            order.setBuyer(loggedInUser);
        } else {
            order = loggedInUser.getOrder();
        }
        order.getGames().add(game);
        this.orderRepository.save(order);
        loggedInUser.setOrder(order);
        System.out.printf("%s added to cart",gameViewDto.getTitle());
    }

    @Override
    public void removeItem(GameViewDto gameViewDto) {
        if (!validationUtil.isValid(gameViewDto)) {
            System.out.println("Invalid data");
            return;
        }
        if (userService.getLoggedInUser() == null) {
            System.out.println("No user is logged in. Please login");
            return;
        }
        User loggedInUser = this.userService.getLoggedInUser();
        Order order = loggedInUser.getOrder();
        Set<Game>games = order.getGames();
        Game game = games.stream().filter
                (g-> g.getTitle().equals(gameViewDto.getTitle())).findAny().orElse(null);
        games.remove(game);
        this.orderRepository.save(order);
       if(order.getGames().isEmpty()){
           loggedInUser.setOrder(null);
           this.orderRepository.delete(order);
       }
        System.out.printf("%s removed from cart",game.getTitle());
    }



}
