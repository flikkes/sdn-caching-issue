package de.flikkes.sdnissue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreetDetail {
    private Long id;
    private String street;
    private String houseNumber;
    private NavigationInformation information;
}
