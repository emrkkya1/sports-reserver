package org.ohaust.springwebdemo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("fields")
public class FieldModel {
    @Id
    private String id;
    @Indexed
    private String fieldName;
    @Indexed
    private String fieldType;
    @Indexed
    private String ownerId;
    @Indexed
    private String city;
    @Indexed
    private String province;
    /*@Indexed
    private List<AvailableDate> availableDates;*/

}
