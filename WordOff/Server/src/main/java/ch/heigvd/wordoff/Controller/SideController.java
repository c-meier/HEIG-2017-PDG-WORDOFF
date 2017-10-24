package ch.heigvd.wordoff.Controller;

import ch.heigvd.wordoff.Repository.SideRepository;
import ch.heigvd.wordoff.common.Model.Side;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "/side", produces = "application/json")
public class SideController {

    @Autowired
    private SideRepository repository;

    @RequestMapping(value = "/{id}")
    public Side getSide(@PathVariable("id") Long sideId) {
        Side side = repository.findOne(sideId);
        return side;
    }
}
