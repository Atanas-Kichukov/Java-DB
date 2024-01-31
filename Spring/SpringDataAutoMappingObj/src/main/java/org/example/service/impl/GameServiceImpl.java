package org.example.service.impl;

import org.example.model.dto.GameAddDto;
import org.example.model.entity.Game;
import org.example.repository.GameRepository;
import org.example.service.GameService;
import org.example.service.UserService;
import org.example.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, ValidationUtil validationUtil,UserService userService) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
    }

    @Override
    public void addGame(GameAddDto gameAddDto) {
        if(!userService.hasLoggedUser()){
            System.out.println("No user is logged in");
            return;
        }

        Set<ConstraintViolation<GameAddDto>> violations = validationUtil.violations(gameAddDto);

        if(!violations.isEmpty()){
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }
        Game game = modelMapper.map(gameAddDto,Game.class);
            gameRepository.save(game);
        System.out.printf("Added %s%n",gameAddDto.getTitle());
    }

    @Override
    public void editGame(long id, List<String[]> values) {
        Game game = gameRepository.findById(id).orElse(null);

        if(game == null){
            System.out.println("Invalid game, please enter a new one");
            return;
        }
        System.out.printf("Edited %s%n",game.getTitle());
        values.forEach(element ->{
            switch (element[0]){
                case "price" -> game.setPrice(new BigDecimal(element[1]));
                case "size" -> game.setSize(Double.parseDouble(element[1]));
                case "title" -> game.setTitle(element[1]);
                case "trailer" -> game.setTrailer(element[1]);
                case "thumbnailURL" -> game.setImageThumbnail(element[1]);
                case "description" -> game.setDescription(element[1]);
                case "releaseDate" -> game.setReleaseDate(LocalDate.parse(element[1]));
            }
        });
       gameRepository.save(game);
    }

    @Override
    public void deleteGame(long id) {
        Game game = gameRepository.findById(id).orElse(null);
        if(game == null){
            System.out.println("No game found, enter new Id");
            return;
        }
        System.out.printf("Game \"%s\" was deleted%n",game.getTitle());
        gameRepository.delete(game);
    }

    @Override
    public List<String> viewTitleAndPriceOfAllGames() {
        List<Game> games = gameRepository.findAll();
        return games.stream()
                .map(game -> String.format("%s %.2f%n", game.getTitle(), game.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public StringBuilder viewGameInformation(String title) {
        Game game = gameRepository.findByTitle(title);
        StringBuilder sb = new StringBuilder();
        if(game == null){
            sb.append("No game found, enter a new title");
            return sb;
        }
        sb.append("Title : ").append(game.getTitle());
        sb.append(System.lineSeparator());
        sb.append("Price : ").append(game.getPrice());
        sb.append(System.lineSeparator());
        sb.append("Description : ").append(game.getDescription());
        sb.append(System.lineSeparator());
        sb.append("Release date : ").append(game.getReleaseDate());
        return sb;
    }

    @Override
    public Game findByTitle(String gameName) {
        return gameRepository.findByTitle(gameName);
    }


}
