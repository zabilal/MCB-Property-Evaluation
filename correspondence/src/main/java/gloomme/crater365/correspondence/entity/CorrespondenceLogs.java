package gloomme.crater365.correspondence.entity;

import gloomme.crater365.correspondence.enums.LogTypes;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "correspondence_logs")
public class CorrespondenceLogs extends BaseEntity{
    @Column(name = "log_id")
    private String craterId;

    @ManyToOne
    private Correspondence correspondence;

    @Column(name = "user_id")
    private String userCraterId;

    @Column(name = "log_type")
    @Enumerated(EnumType.STRING)
    private LogTypes logType;

    @Column(name = "file_path")
    private String filePath;

    @Column
    private String OrganizationCraterId;
}
