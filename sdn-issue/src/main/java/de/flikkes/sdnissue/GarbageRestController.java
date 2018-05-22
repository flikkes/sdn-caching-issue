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

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@RestController
public class GarbageRestController {
    @Autowired
    private PlasticContainerRepository plasticContainerRepository;
    @Autowired
    private Session session;

    @RequestMapping("add/{householdName}/{persons}/{street}/{houseNumber}/{xStart}/{yStart}/{xEnd}/{yEnd}/{xPos}/{yPos}")
    public ResponseEntity addPlasticContainer(
            @PathVariable String householdName, @PathVariable int persons, @PathVariable String street,
            @PathVariable String houseNumber, @PathVariable double xStart,
            @PathVariable double yStart, @PathVariable double xEnd,
            @PathVariable double yEnd, @PathVariable double xPos,
            @PathVariable double yPos) {
        GarbageType garbageType = new GarbageType();
        garbageType.setCategory(GarbageCategory.PLASTIC);
        garbageType.setHealth(GarbageHealth.ACCEPTABLE);
        HouseholdDetail householdDetail = new HouseholdDetail();
        householdDetail.setName(householdName);
        householdDetail.setPersons(persons);
        householdDetail.setType(HouseholdType.PRIVATE);
        AddressDetail addressDetail = new AddressDetail();
        addressDetail.setDisplayName(street + " " + houseNumber);
        addressDetail.setHousehold(householdDetail);
        addressDetail.setStreet(new StreetDetail(
                null,
                street,
                houseNumber,
                new NavigationInformation(
                        null,
                        xStart,
                        yStart,
                        xEnd,
                        yEnd,
                        true)
        ));
        GarbagePosition garbagePosition = new GarbagePosition();
        garbagePosition.setDisplayName("At " + householdName + "s");
        garbagePosition.setAddress(addressDetail);
        garbagePosition.setCoordinates(new GarbageCoordinates(null, xPos, yPos, "In front of the house"));
        PlasticContainer plasticContainer = new PlasticContainer(garbageType, garbagePosition);
        plasticContainer.setHouseholdName("The " + householdName + "s");
        plasticContainer.setInternalName(UUID.randomUUID().toString());
        plasticContainer.setLastTimeEmptied(new Date());
        plasticContainerRepository.save(plasticContainer);
        return ResponseEntity.ok(plasticContainer);
    }

    /**
     * Favourite approach. Returns nothing.
     *
     * @param householdName
     * @return
     */
    @RequestMapping("normally-from-repository/{householdName}")
    public ResponseEntity getNormallyByHouseholdName(@PathVariable String householdName) {
        return ResponseEntity.ok(plasticContainerRepository.findByPositionAddressHouseholdName(householdName));
    }

    /**
     * Second favourite approach. At least returns something. Would be nice to have it like this.
     *
     * @param householdName
     * @return
     */
    @RequestMapping("custom-query-from-repository/{householdName}")
    public ResponseEntity getPlasticContainerWithCustomQueryByHouseholdName(@PathVariable String householdName) {
        return ResponseEntity.ok(plasticContainerRepository.findByAddressHouseholdName(householdName));
    }

    /**
     * Wrote this to see if the depth differs from {@link #getPlasticContainerWithCustomQueryByHouseholdName(String)}
     *
     * @param householdName
     * @return
     */
    @RequestMapping("session-query/{householdName}")
    public ResponseEntity getPlayticContainerWithSessionQueryByHouseholdName(@PathVariable String householdName) {
        return ResponseEntity.ok(session.query(PlasticContainer.class,
                "MATCH (p:PlasticContainer)" +
                        "-[pos:POSITION]-(gp:GarbagePosition)" +
                        "-[addr:ADDRESS]-(ad:AddressDetail)" +
                        "-[hh:HOUSEHOLD]-(h:HouseholdDetail) " +
                        "WHERE h.name = {name} " +
                        "RETURN p", Values.parameters("name", householdName).asMap()));
    }

    /**
     * Currently returns the expected result. Obviously makes use of a caching bug with {@link Session}.
     *
     * @param householdName
     * @return
     */
    @RequestMapping("workaround/{householdName}")
    public ResponseEntity getPlasticContainerWithWorkaroundByHouseholdName(@PathVariable String householdName) {
        session.loadAll(PlasticContainer.class, 5);
        session.loadAll(GarbagePosition.class, 5);
        session.loadAll(GarbageType.class, 5);
        session.loadAll(AddressDetail.class, 5);
        session.loadAll(HouseholdDetail.class, 5);
        session.loadAll(StreetDetail.class, 5);
        session.loadAll(GarbageCoordinates.class, 5);
        session.loadAll(NavigationInformation.class, 5);
        return ResponseEntity.ok(plasticContainerRepository.findByAddressHouseholdName(householdName));
    }

    /**
     * Currently cleanest version of the workaround. Returns the expected result.
     *
     * @param householdName
     * @return
     */
    @RequestMapping("workaround2/{householdName}")
    public ResponseEntity getPlasticContainerWithWorkaround2ByHouseholdName(@PathVariable String householdName) {
        Collection<PlasticContainer> c = plasticContainerRepository.findByAddressHouseholdName(householdName);
        return ResponseEntity.ok(session.loadAll(c, 5));
    }
}
