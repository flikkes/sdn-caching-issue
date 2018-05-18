package de.flikkes.sdnissue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetail {
    private Long id;
    private String displayName;
    private HouseholdDetail household;
    private StreetDetail street;
}
