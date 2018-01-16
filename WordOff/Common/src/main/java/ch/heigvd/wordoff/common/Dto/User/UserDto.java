/*
 * File: UserDto.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto.User;

import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;

import java.util.Objects;

public class UserDto extends PlayerDto implements IResource<UserDto> {
    private int level;
    private String profilImage;

    // Necessary for Jackson deserialization
    public UserDto() {}

    public UserDto(Long id, String name) {
        super(id, name);
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
        if(id != null) {
            endpoint = "/users/" + id;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getProfilImage() {
        return profilImage;
    }

    public void setProfilImage(String profilImage) {
        this.profilImage = profilImage;
    }

    private static Class<UserDto> resourceType = UserDto.class;

    @Override
    public Class<UserDto> getResourceType() {
        return resourceType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof UserDto)) {
            return false;
        }
        UserDto c = (UserDto) o;
        return super.equals(o) &&
                Objects.equals(level, c.level) &&
                Objects.equals(profilImage, c.profilImage);
    }

    /**
     * Information about the state of the relation between you and this user.
     */
    private RelationDto relation;

    public RelationDto getRelation() {
        return relation;
    }

    public void setRelation(RelationDto relation) {
        this.relation = relation;
    }

    /**
     * Endpoint to refresh (GET) informations.
     */
    private String endpoint;

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
