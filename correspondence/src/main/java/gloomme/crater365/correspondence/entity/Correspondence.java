package gloomme.crater365.correspondence.entity;

import gloomme.crater365.correspondence.enums.Stage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Data
public class Correspondence extends BaseEntity{

    private String craterId;

    private String organizationCraterId;

    private String title;

    private String status;

    @Enumerated(EnumType.STRING)
    private Stage stage;

    private String initiatorId;

    private String reviewerId;

    private String bapproverId;

    private String fapproverId;

    private String departmentId;

    private String source;

    private String destinationBUID;

    private String functionId;

    private String destLibId;

    private LocalDate dateOfReciept;

    private String documentType;

    private String documentOwnerId;

    private String fileUrl;
}
