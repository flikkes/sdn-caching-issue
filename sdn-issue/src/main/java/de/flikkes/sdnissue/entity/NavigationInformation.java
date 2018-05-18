package de.flikkes.sdnissue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NavigationInformation {
    private Long id;
    private double xStartPosition;
    private double yStartPosition;
    private double xEndPosition;
    private double yEndPosition;
    private boolean registeredAtNavigationService;
}
