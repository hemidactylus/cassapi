package net.myspring.cassapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
@Data
//@AllArgsConstructor
public class Taxon {
    @PrimaryKey
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;

    @Indexed("parent_index")
    private UUID parent;
    @Indexed("name_index")
    private String name;

    private String level;
    private String description;

    public Taxon(UUID id, UUID parent, String name, String level, String description) {
        if(id == null) {
            this.id = UUID.randomUUID();
        }else{
            this.id = id;
        }
        this.parent = parent;
        this.name = name;
        this.level = level;
        this.description = description;
    }

}
