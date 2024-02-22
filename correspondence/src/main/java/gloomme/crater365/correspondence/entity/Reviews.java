package gloomme.crater365.correspondence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Reviews extends BaseEntity{
    private String craterId;
    @ManyToOne
    private Correspondence correspondence;
    private String fileUrl;
    private String review;
    private String reviewedBy;
    private String organizationCraterId;

}
