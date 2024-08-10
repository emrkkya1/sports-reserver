package org.ohaust.springwebdemo.repository;


import org.ohaust.springwebdemo.model.Date;
import org.ohaust.springwebdemo.model.ReservableDate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends MongoRepository<ReservableDate,String> {
    ReservableDate findByDate(Date date);
}
