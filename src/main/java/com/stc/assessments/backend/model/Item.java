package com.stc.assessments.backend.model;

import com.stc.assessments.backend.config.PostgreSQLEnumType;
import com.stc.assessments.backend.model.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table (name = "items")
@TypeDef(name = "item_type", typeClass = PostgreSQLEnumType.class)
public class Item {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Column(nullable = false,unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "permission_group_id")
    private PermissionGroup permissionGroup;
    @ManyToOne
    @JoinColumn(name="parent_id")
    private Item parent;

    public ItemType getType ( ) {
        return type;
    }

    public void setType ( String strType ) {

        this.type = ItemType.valueOf ( strType );
    }
}

