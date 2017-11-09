package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.common.Dto.*;
import ch.heigvd.wordoff.common.Dto.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Dto.Slots.L2SlotDto;
import ch.heigvd.wordoff.common.Dto.Slots.LastSlotDto;
import ch.heigvd.wordoff.common.Dto.Slots.SlotDto;
import ch.heigvd.wordoff.common.Dto.Slots.SwapSlotDto;
import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;
import ch.heigvd.wordoff.common.Protocol;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class JsonConversionTest {
    private final ObjectMapper mapper = new ObjectMapper();

    private LoginDto loginDto;
    private ErrorDto errorDto;

    private PlayerDto ai;
    private UserSummaryDto userSummary;
    private UserDto user;

    private PlayerRackDto playerRackDto;
    private ChallengeDto challengeDto;
    private AnswerDto answerDto;
    private SideDto sideDto;
    private OtherSideDto otherSideDto;
    private GameDto gameDto;
    private GameSummaryDto gameSummaryDto;

    @Before
    public void setUp() throws Exception {
        loginDto = new LoginDto("testLogin", "testPass".toCharArray());
        errorDto = new ErrorDto(Protocol.USER_ALREADY_EXISTS, "The user already exists!");

        ai = new PlayerDto(1L, "AI");
        userSummary = new UserSummaryDto(2L, "testUser");
        user = new UserDto(2L, "testUser");

        challengeDto = new ChallengeDto(Arrays.asList(
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

        playerRackDto = new PlayerRackDto(Arrays.asList(
            new TileDto(1, '#', 0),
            new TileDto(24, 'L', 3),
            new TileDto(12, 'E', 1),
            new TileDto(21, 'K', 8),
            new TileDto(11, 'E', 1),
            new TileDto(5, 'A', 1),
            new TileDto(8, 'B', 4)
        ));

        sideDto = new SideDto(1L, userSummary, challengeDto, playerRackDto, 0);
        otherSideDto = new SideDto(2L, ai, challengeDto, playerRackDto, 0);

        answerDto = new AnswerDto(1L, (short)1, challengeDto);

        gameDto = new GameDto(1L, sideDto, otherSideDto, true, "fr", new Date());
        gameSummaryDto = new GameSummaryDto(1L, ai);
    }

    @Test
    public void canSerializeAndDeserializeChallengeDto() throws Exception {
        ChallengeDto dto = challengeDto;

        String json = mapper.writeValueAsString(dto);

        ChallengeDto clone = mapper.readValue(json, ChallengeDto.class);

        assertThat(dto).isEqualTo(clone);
    }

    @Test
    public void canSerializeAndDeserializeGameDto() throws Exception {
        GameDto dto = gameDto;

        String json = mapper.writeValueAsString(dto);

        GameDto clone = mapper.readValue(json, GameDto.class);

        assertThat(clone).isEqualTo(dto);
    }

    @Test
    public void canSerializeAndDeserializeGameSummaryDto() throws Exception {
        GameSummaryDto dto = gameSummaryDto;

        String json = mapper.writeValueAsString(dto);

        GameSummaryDto clone = mapper.readValue(json, GameSummaryDto.class);

        assertThat(dto).isEqualTo(clone);
    }

    @Test
    public void canSerializeAndDeserializeUserDto() throws Exception {
        UserDto dto = user;

        String json = mapper.writeValueAsString(dto);

        UserDto clone = mapper.readValue(json, UserDto.class);

        assertThat(dto).isEqualTo(clone);
    }

    @Test
    public void canSerializeAndDeserializeUserSummaryDto() throws Exception {
        UserSummaryDto dto = userSummary;

        String json = mapper.writeValueAsString(dto);

        UserSummaryDto clone = mapper.readValue(json, UserSummaryDto.class);

        assertThat(dto).isEqualTo(clone);
    }

    @Test
    public void canSerializeAndDeserializeLoginDto() throws Exception {
        LoginDto dto = loginDto;

        String json = mapper.writeValueAsString(dto);

        LoginDto clone = mapper.readValue(json, LoginDto.class);

        assertThat(dto).isEqualTo(clone);
    }

    @Test
    public void canSerializeAndDeserializeErrorDto() throws Exception {
        ErrorDto dto = errorDto;

        String json = mapper.writeValueAsString(dto);

        ErrorDto clone = mapper.readValue(json, ErrorDto.class);

        assertThat(dto).isEqualTo(clone);
    }

    @Test
    public void canSerializeAndDeserializeAnswerDto() throws Exception {
        AnswerDto dto = answerDto;

        String json = mapper.writeValueAsString(dto);

        AnswerDto clone = mapper.readValue(json, AnswerDto.class);

        assertThat(dto).isEqualTo(clone);
    }
}
