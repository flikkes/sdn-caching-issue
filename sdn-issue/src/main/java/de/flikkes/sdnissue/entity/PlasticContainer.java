package de.flikkes.sdnissue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlasticContainer extends GarbageContainer {
    private GarbageType type;
    private GarbagePosition position;
}
