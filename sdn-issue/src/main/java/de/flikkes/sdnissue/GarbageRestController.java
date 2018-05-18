package de.flikkes.sdnissue;

import de.flikkes.sdnissue.entity.*;
import de.flikkes.sdnissue.repo.PlasticContainerRepository;
import org.neo4j.driver.v1.Values;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class GarbageRestController {
    @Autowired
    private PlasticContainerRepository plasticContainerRepository;
    @Autowired
    private Session session;

    @RequestMapping("addplasticcontainer")
    public ResponseEntity addPlayticContainer() {
        GarbageType garbageType = new GarbageType();
        garbageType.setCategory(GarbageCategory.PLASTIC);
        garbageType.setHealth(GarbageHealth.ACCEPTABLE);
        HouseholdDetail householdDetail = new HouseholdDetail();
        householdDetail.setName("Müller");
        householdDetail.setPersons(3);
        householdDetail.setType(HouseholdType.PRIVATE);
        AddressDetail addressDetail = new AddressDetail();
        addressDetail.setDisplayName("Müllerstraße 9");
        addressDetail.setHousehold(householdDetail);
        addressDetail.setStreet(new StreetDetail(
                null,
                "Müllerstraße",
                "9",
                new NavigationInformation(
                        null,
                        1231.423,
                        534.23,
                        23.453,
                        2.5456,
                        true)
        ));
        GarbagePosition garbagePosition = new GarbagePosition();
        garbagePosition.setDisplayName("Bei Müllers");
        garbagePosition.setAddress(addressDetail);
        garbagePosition.setCoordinates(new GarbageCoordinates(null, 234.54, 34545.5, "Vor dem Haus"));
        PlasticContainer plasticContainer = new PlasticContainer(garbageType, garbagePosition);
        plasticContainer.setHouseholdName("Die Müllers");
        plasticContainer.setInternalName("vbbndfghdfgsdgtejd");
        plasticContainer.setLastTimeEmptied(new Date());
        plasticContainerRepository.save(plasticContainer);
        return ResponseEntity.ok(plasticContainer);
    }

    @RequestMapping("getplasticcontainer/{xStartPosition}")
    public ResponseEntity getPlasticContainerByNavigationInfoXStartPosition(@PathVariable double xStartPosition) {
        return ResponseEntity.ok(plasticContainerRepository.findByPositionAddressStreetInformationXStartPosition(xStartPosition));
    }

    @RequestMapping("byhousehold/{householdName}")
    public ResponseEntity getPlasticContainerByPositionHouseholdName(@PathVariable String householdName) {
        session.loadAll(PlasticContainer.class);
//        return ResponseEntity.ok(session.query(PlasticContainer.class,
//                "MATCH (p:PlasticContainer)" +
//                        "-[pos:POSITION]-(gp:GarbagePosition)" +
//                        "-[addr:ADDRESS]-(ad:AddressDetail)" +
//                        "-[hh:HOUSEHOLD]-(h:HouseholdDetail) " +
//                        "WHERE h.name = {name} " +
//                        "RETURN p, pos, gp, addr, ad, hh, h", Values.parameters("name", householdName).asMap()));
        return ResponseEntity.ok(plasticContainerRepository.findByAddressHouseholdName(householdName));
    }
}
