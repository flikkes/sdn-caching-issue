package de.flikkes.sdnissue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarbageType {
    private Long id;
    private GarbageCategory category;
    private GarbageHealth health;
}
