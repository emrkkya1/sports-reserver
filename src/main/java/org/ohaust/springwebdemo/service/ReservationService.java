package org.ohaust.springwebdemo.service;

import org.ohaust.springwebdemo.model.ReservableDate;
import org.ohaust.springwebdemo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository repository;

    public ReservationService() {

    }




}
