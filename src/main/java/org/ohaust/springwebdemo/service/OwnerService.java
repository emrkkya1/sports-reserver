package org.ohaust.springwebdemo.service;

import lombok.RequiredArgsConstructor;
import org.ohaust.springwebdemo.model.OwnerModel;
import org.ohaust.springwebdemo.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerModel createOwner(OwnerModel ownerModel) {
        return ownerRepository.save(ownerModel);
    }

    public OwnerModel getOwnerById(String ownerId) {
        Optional<OwnerModel> owner = ownerRepository.findById(ownerId);
        return owner.orElse(null);
    }
}

