package ch.heigvd.wordoff.common.Dto;

import java.util.Objects;

public class UserSummaryDto extends PlayerDto implements ISummaryDto {
    private String endpoint;

    // Necessary for Jackson deserialization
    protected UserSummaryDto() {}

    public UserSummaryDto(Long id, String name) {
        super(id, name);
        endpoint = "/users/" + id;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof UserSummaryDto)) {
            return false;
        }
        UserSummaryDto c = (UserSummaryDto) o;
        return super.equals(o) &&
                Objects.equals(endpoint, c.endpoint);
    }
}
