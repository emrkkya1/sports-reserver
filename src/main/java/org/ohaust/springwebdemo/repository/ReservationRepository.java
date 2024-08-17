package org.ohaust.springwebdemo.repository;


import org.ohaust.springwebdemo.model.DateModel;
import org.ohaust.springwebdemo.model.ReservableDateModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends MongoRepository<ReservableDateModel, String> {
    ReservableDateModel findByDateModel(DateModel dateModel);
    @Override
    Optional<ReservableDateModel> findById(String id);
}
