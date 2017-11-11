package ch.heigvd.wordoff.common.Dto;

import java.util.Objects;

public class UserSummaryDto extends PlayerDto implements ISummaryDto {
    private String endpoint;

    // Necessary for Jackson deserialization
    public UserSummaryDto() {}

    public UserSummaryDto(Long id, String name) {
        super(id, name);
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
        if(id != null) {
            endpoint = "/users/" + id;
        }
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
