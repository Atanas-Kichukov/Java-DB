package org.example;

import org.example.model.dto.GameAddDto;
import org.example.model.dto.GameViewDto;
import org.example.model.dto.UserLoginDto;
import org.example.model.dto.UserRegisterDto;
import org.example.service.GameService;
import org.example.service.OrderService;
import org.example.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final BufferedReader bufferedReader;
    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;


    public CommandLineRunnerImpl(UserService userService, GameService gameService, OrderService orderService) {
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            System.out.println("Enter your commands:");
            String[] commands = bufferedReader.readLine().split("\\|");
            switch (commands[0]) {
                case "RegisterUser" -> userService
                        .registerUser(new UserRegisterDto(commands[1], commands[2],
                                commands[3], commands[4]));


                case "LoginUser" -> userService
                        .loginUser(new UserLoginDto(commands[1],commands[2]));

                case "Logout" -> userService
                        .logout();

                case "AddGame" -> gameService
                        .addGame(new GameAddDto(commands[1],new BigDecimal(commands[2]),Double.parseDouble(commands[3]),commands[4],
                                commands[5],commands[6], LocalDate.parse(commands[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

                case "EditGame" -> gameService.editGame(Long.parseLong(commands[1]),
                        Arrays.stream(commands).skip(2)
                                .map(el-> el.split("="))
                                .collect(Collectors.toList()));
                case "DeleteGame" -> gameService.deleteGame(Long.parseLong(commands[1]));
                case "AllGames" -> gameService
                        .viewTitleAndPriceOfAllGames().forEach(System.out::println);
                case "DetailGame" -> System.out.println(gameService.viewGameInformation(commands[1]));
                case "AddItem" -> orderService.addGameToCart(new GameViewDto(commands[1]));
                case "RemoveItem" -> orderService.removeItem(new GameViewDto(commands[1]));
                case "BuyItem" -> userService.buyItem();
            }
        }
    }
}
