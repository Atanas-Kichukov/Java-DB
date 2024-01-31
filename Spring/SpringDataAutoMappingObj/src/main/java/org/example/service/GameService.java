package org.example.service;

import org.example.model.dto.GameAddDto;
import org.example.model.entity.Game;

import java.util.List;

public interface GameService {

    void addGame(GameAddDto gameAddDto);


    void editGame(long id, List<String[]> values);

    void deleteGame(long id);

    List<String> viewTitleAndPriceOfAllGames();

    StringBuilder viewGameInformation(String title);

    Game findByTitle(String gameName);
}
