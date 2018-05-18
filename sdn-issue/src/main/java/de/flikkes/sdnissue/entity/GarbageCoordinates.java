package de.flikkes.sdnissue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarbageCoordinates {
    private Long id;
    private double xPosition;
    private double yPosition;
    String displayName;
}
