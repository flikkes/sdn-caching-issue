package de.flikkes.sdnissue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarbagePosition {
    private Long id;
    private String displayName;
    private GarbageCoordinates coordinates;
    private Collection<GarbageCoordinates> trace;
    private AddressDetail address;
}
