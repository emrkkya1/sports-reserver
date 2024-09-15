package org.ohaust.springwebdemo.repository;


import org.ohaust.springwebdemo.model.DateInfo;
import org.ohaust.springwebdemo.model.DateModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends MongoRepository<DateModel, String> {
    DateModel findByDateInfo(DateInfo dateInfo);
}
