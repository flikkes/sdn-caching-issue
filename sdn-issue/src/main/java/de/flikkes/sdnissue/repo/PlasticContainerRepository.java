package de.flikkes.sdnissue.repo;

import de.flikkes.sdnissue.entity.PlasticContainer;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface PlasticContainerRepository extends CrudRepository<PlasticContainer, Long> {
    Collection<PlasticContainer> findByPositionAddressHouseholdName(String name);

    @Depth(5)
    @Query("MATCH (p:PlasticContainer)-[:POSITION]-(gp:GarbagePosition)-[:ADDRESS]-(ad:AddressDetail)-[:HOUSEHOLD]-(h:HouseholdDetail) WHERE h.name = {name} RETURN p")
    Collection<PlasticContainer> findByAddressHouseholdName(@Param("name") String name);
}
