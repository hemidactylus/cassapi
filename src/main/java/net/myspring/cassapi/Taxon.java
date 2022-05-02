package net.myspring.cassapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.UUID;

@Table(value = "taxa")
@Data
//@AllArgsConstructor
public class Taxon {
    @PrimaryKey("id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;

    @Indexed("taxa_parent_index")
    @Column("parent")
    private UUID parent;
    @Indexed("taxa_name_index")
    @Column("name")
    private String name;

    @Column("level")
    private String level;
    @Column("description")
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
