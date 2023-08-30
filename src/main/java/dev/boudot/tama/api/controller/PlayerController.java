package dev.boudot.tama.api.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.boudot.tama.api.GameObjects.Player;
import dev.boudot.tama.api.controller.requestBody.PlayerNameBody;
import dev.boudot.tama.api.service.PlayerService;

@Controller
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping()
    @ResponseBody
    public String getPlayerNames() {
        return playerService
            .getPlayer()
            .stream()
            .map(player -> player.getUserName())
            .collect(Collectors.toList())
            .toString();
    }

    @GetMapping("{playerName}")
    @ResponseBody
    public String getPlayerInfo(@PathVariable String playerName) {
        if ( playerName == null || playerName.length() < 1 ) {
            return "The player name is wrong";
        }

        // Player player = playerService.getPlayer(playerName);

        // if ( player == null ) {
        //     return "No player with this name exists";
        // }

        // return player.toString();
        return playerService.getPlayer(playerName);
    }

    @PostMapping()
    @ResponseBody
    public String createPlayer(@RequestBody PlayerNameBody requestBody) {
        if ( !requestBody.isLegal() ) {
            return "A name is required to create a player";
        }

        String name = requestBody.name;

        Player player; // = playerService.getPlayer(name);

        // if ( player != null ) {
        //     return "A player with the same name already exist";
        // }

        player = new Player(name);
  
        playerService.savePlayer(player);

        return "The player has been created";
    }

    @DeleteMapping()
    @ResponseBody
    public String deletePlayer(@RequestBody PlayerNameBody requestBody) {
        if ( !requestBody.isLegal() ) {
            return "A name is required to create a player";
        }

        String name = requestBody.name;

        // Player player = playerService.getPlayer(name);

        // if ( player == null ) {
        //     return "No player with this name exists";
        // }

        playerService.deletePlayer(name);

        return "The player has been deleted";
    }

}
