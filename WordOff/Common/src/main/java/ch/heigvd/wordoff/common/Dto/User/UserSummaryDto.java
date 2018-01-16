package ch.heigvd.wordoff.common.Dto.User;

import ch.heigvd.wordoff.common.Dto.Endpoint.IEndpoint;
import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;

import java.util.Objects;

/**
 * Summary information about a user.
 */
public class UserSummaryDto extends PlayerDto implements IResource<UserDto>, IEndpoint {
    /**
     * Endpoint to GET the full user information.
     */
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

    private static Class<UserDto> resourceType = UserDto.class;

    @Override
    public Class<UserDto> getResourceType() {
        return resourceType;
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
