/*
 * File: CreateRelationDto.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto.User;

/**
 * Send information about a new relation to the server.
 */
public class CreateRelationDto {
    /**
     * The username of the target of the relation.
     */
    private String targetUsername;

    /**
     * The status of the new relation.
     */
    private RelationStatus status;

    public CreateRelationDto() {
    }

    public CreateRelationDto(String targetUsername, RelationStatus status) {
        this.targetUsername = targetUsername;
        this.status = status;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public RelationStatus getStatus() {
        return status;
    }

    public void setStatus(RelationStatus status) {
        this.status = status;
    }
}
