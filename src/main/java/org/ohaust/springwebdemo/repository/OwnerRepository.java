package org.ohaust.springwebdemo.repository;

import org.ohaust.springwebdemo.model.OwnerModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends MongoRepository<OwnerModel,String> {

}
