package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.common.Dto.ChallengeDto;
import ch.heigvd.wordoff.common.Dto.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Dto.Slots.L2SlotDto;
import ch.heigvd.wordoff.common.Dto.Slots.LastSlotDto;
import ch.heigvd.wordoff.common.Dto.Slots.SlotDto;
import ch.heigvd.wordoff.common.Dto.Slots.SwapSlotDto;
import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class JsonConversionTest {
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void canSerializeAndDeserializeChallengeDto() throws Exception {
        ChallengeDto challengeDto = new ChallengeDto(Arrays.asList(
                new SlotDto(1L, (short)1),
                new SlotDto(1L, (short)2),
                new SwapSlotDto(1L, (short)3),
                new L2SlotDto(1L, (short)4),
                new SwapSlotDto(1L, (short)5),
                new SlotDto(1L, (short)6),
                new LastSlotDto(1L, (short)7)),
                new SwapRackDto(Arrays.asList(new TileDto(7, 'B', 1))));
        challengeDto.addTile(new TileDto(2, 'X', 0));
        challengeDto.addTile(new TileDto(13, 'E', 1));

        String json = mapper.writeValueAsString(challengeDto);

        ChallengeDto extractedChallenge = mapper.readValue(json, ChallengeDto.class);

        assertThat(challengeDto).isEqualTo(extractedChallenge);
    }
}
