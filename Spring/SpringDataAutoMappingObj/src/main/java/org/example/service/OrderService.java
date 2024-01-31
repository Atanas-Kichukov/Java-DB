package org.example.service;

import org.example.model.dto.GameViewDto;

public interface OrderService {


    void addGameToCart(GameViewDto gameViewDto);

    void removeItem(GameViewDto gameViewDto);


}
