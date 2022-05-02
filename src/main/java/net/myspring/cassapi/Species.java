package net.myspring.cassapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.Map;
import java.util.UUID;

@Table(value = "species")
@Data
@AllArgsConstructor
public class Species {

    @PrimaryKeyColumn(name = "parent", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID parent;

    @PrimaryKeyColumn(name = "name", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    @Indexed("species_name_index")
    @Column("name")
    private String name;

    @Column("common_names")
    private Map<String, String> commonNames;

    @Column("description")
    private String description;

    @Column("photo_url")
    private String photoUrl;

}
