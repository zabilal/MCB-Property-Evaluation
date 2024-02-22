package gloomme.crater365.correspondence.dto;

import gloomme.crater365.correspondence.entity.CorrespondenceLogs;
import gloomme.crater365.correspondence.entity.Reviews;
import gloomme.crater365.correspondence.enums.Stage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorrespondenceDTO {

    private String craterId;

    private String organizationCraterId;

    private String title;

    private String status;

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

    private String userCraterId;

    private List<CorrespondenceLogs> logs;

    private List<Reviews> reviews;
}
