package ch.heigvd.wordoff.server.Utils;

import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import ch.heigvd.wordoff.server.Util.DaoConveter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Project : WordOff
 * Author(s) : Antoine Friant
 * Date : 11.11.17
 */
public class DaoConverterTest {
    @Test
    public void tileToTileDtoConvesion() {
        Tile tile = new Tile(1, 'A', 5);
        TileDto tileDto = DaoConveter.convertToDto(tile);
        assertEquals(tile.getValue(), tileDto.getValue());
        assertEquals(tile.getId(), tileDto.getId());
        assertEquals(tile.getScore(), tileDto.getScore());
    }
}
