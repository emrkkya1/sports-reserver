package org.ohaust.springwebdemo.controller;

import lombok.RequiredArgsConstructor;
import org.ohaust.springwebdemo.model.OwnerModel;
import org.ohaust.springwebdemo.service.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerService ownerService;

    @PostMapping
    public ResponseEntity<OwnerModel> createOwner(@RequestBody OwnerModel ownerModel) {
        OwnerModel createdOwner = ownerService.createOwner(ownerModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOwner);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<OwnerModel> getOwnerById(@PathVariable("ownerId") String ownerId) {
        OwnerModel owner = ownerService.getOwnerById(ownerId);
        if (owner != null) {
            return new ResponseEntity<>(owner, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
