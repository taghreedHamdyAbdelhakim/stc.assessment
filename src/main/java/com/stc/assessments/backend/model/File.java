package com.stc.assessments.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table (name = "files")
public class File {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Type (type="org.hibernate.type.BinaryType")
    @Column(nullable = true ,name = "binary_file")

    private byte[] binary;

    @OneToOne
    @JoinColumn (name = "item_id")
    private Item item;
}
