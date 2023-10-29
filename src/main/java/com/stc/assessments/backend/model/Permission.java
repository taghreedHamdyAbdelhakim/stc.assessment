package com.stc.assessments.backend.model;
import com.stc.assessments.backend.model.enums.PermissionLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String  userEmail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PermissionLevel permissionLevel;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private PermissionGroup group;


    public void setPermissionLevel ( String strPermissionLevel ) {
        this.permissionLevel = PermissionLevel.valueOf ( strPermissionLevel );
    }
}
