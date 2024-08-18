package org.ohaust.springwebdemo.repository;

import org.ohaust.springwebdemo.model.FieldModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldRepository extends MongoRepository<FieldModel, String> {
    List<FieldModel> findByOwnerId(String ownerId);

    Optional<FieldModel> findByFieldName(String fieldName);

    @Query("{ 'fieldName': { $regex: ?0, $options: 'i' } }")
    List<FieldModel> findByFieldNameContaining(String partialName);

    List<FieldModel> findByCity(String city);

    List<FieldModel> findByProvince(String province);

    List<FieldModel> findByFieldType(String country);
}
