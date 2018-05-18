package de.flikkes.sdnissue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
public abstract class GarbageContainer {
    private Long id;
    private String householdName;
    private String internalName;
    private Date lastTimeEmptied;
}
