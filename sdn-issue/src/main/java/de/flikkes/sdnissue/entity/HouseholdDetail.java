package de.flikkes.sdnissue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdDetail {
    private Long id;
    private String name;
    private int persons;
    private HouseholdType type;
}
