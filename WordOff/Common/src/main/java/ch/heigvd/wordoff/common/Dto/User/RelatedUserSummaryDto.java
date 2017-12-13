package ch.heigvd.wordoff.common.Dto.User;

/**
 * Summary information about a user with the relation status.
 */
public class RelatedUserSummaryDto extends UserSummaryDto {
    /**
     * Information about the state of the relation between you and this user.
     */
    private RelationDto relation;

    public RelatedUserSummaryDto() {
    }

    public RelationDto getRelation() {
        return relation;
    }

    public void setRelation(RelationDto relation) {
        this.relation = relation;
    }
}
