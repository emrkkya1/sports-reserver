package org.ohaust.springwebdemo.controller;

import lombok.RequiredArgsConstructor;
import org.ohaust.springwebdemo.model.FieldModel;
import org.ohaust.springwebdemo.service.FieldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fields")
public class FieldController {
    private final FieldService fieldService;


    @PostMapping
    public ResponseEntity<FieldModel> createField(@RequestBody FieldModel fieldModel) {
        try {
            FieldModel createdField = fieldService.createField(fieldModel);
            return new ResponseEntity<>(createdField, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<FieldModel>> getFieldsByOwnerId(@PathVariable("ownerId") String ownerId) {
        return ResponseEntity.ok(fieldService.getFieldsByOwnerId(ownerId));
    }

    /* @PutMapping("/{fieldId}/available-dates")
    public ResponseEntity<FieldModel> updateAvailableDatesAndHours(@PathVariable("fieldId") String fieldId,
                                                              @RequestBody List<AvailableDate> availableDates) {
        try {
            FieldModel updatedField = fieldService.updateAvailableDatesAndHours(fieldId, availableDates);
            return ResponseEntity.ok(updatedField);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }*/

    @GetMapping("/search/contains")
    public ResponseEntity<List<FieldModel>> searchFieldsByPartialName(@RequestParam String partialName) {
        List<FieldModel> fields = fieldService.searchFieldsByPartialName(partialName);
        if (fields.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fields);
    }

    @GetMapping("/search/city")
    public ResponseEntity<List<FieldModel>> searchFieldsByCity(@RequestParam String city) {
        List<FieldModel> fields = fieldService.getFieldsByCity(city);
        if (fields.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fields);
    }

    // Get fields by province
    @GetMapping("/search/province")
    public ResponseEntity<List<FieldModel>> searchFieldsByProvince(@RequestParam String province) {
        List<FieldModel> fields = fieldService.getFieldsByProvince(province);
        if (fields.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fields);
    }

    @GetMapping("search/type")
    public ResponseEntity<List<FieldModel>> searchFieldsByFieldType(@RequestParam("type") String fieldType) {
        List<FieldModel> fields = fieldService.getFieldsByFieldType(fieldType);
        if (fields.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fields);
    }

}
