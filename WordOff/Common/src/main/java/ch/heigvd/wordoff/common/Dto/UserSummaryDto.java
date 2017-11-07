package ch.heigvd.wordoff.common.Dto;

public class UserSummaryDto extends PlayerDto implements ISummaryDto {
    private String endpoint;

    public UserSummaryDto(Long id, String name) {
        super(id, name);
        endpoint = "/users/" + id;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }
}
