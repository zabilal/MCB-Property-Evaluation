package gloomme.crater365.correspondence.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private String craterId;
    private String correspondenceId;
    private String review;
    private String reviewedBy;
    private String organizationCraterId;
}
