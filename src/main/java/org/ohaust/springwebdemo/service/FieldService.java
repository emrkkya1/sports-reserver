package org.ohaust.springwebdemo.service;

import lombok.RequiredArgsConstructor;
import org.ohaust.springwebdemo.model.FieldModel;
import org.ohaust.springwebdemo.repository.FieldRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FieldService {
    private final FieldRepository fieldRepository;

    public List<FieldModel> getFieldsByOwnerId(String ownerId) {
        return fieldRepository.findByOwnerId(ownerId);
    }

    public FieldModel createField(FieldModel field) {
        // Check if a field with the same name already exists
        Optional<FieldModel> existingField = fieldRepository.findByFieldName(field.getFieldName());
        if (existingField.isPresent()) {
            throw new IllegalArgumentException("A field with the name '" + field.getFieldName() + "' already exists.");
        }
        return fieldRepository.save(field);
    }

    public FieldModel updateAvailableDatesAndHours(String fieldId, List<AvailableDate> availableDates) {
        Optional<FieldModel> fieldOptional = fieldRepository.findById(fieldId);
        if (fieldOptional.isPresent()) {
            FieldModel field = fieldOptional.get();
            field.setAvailableDates(availableDates);
            return fieldRepository.save(field);
        } else {
            throw new IllegalArgumentException("Field with ID '" + fieldId + "' not found.");
        }
    }

    public List<FieldModel> searchFieldsByPartialName(String fieldName) {
        return fieldRepository.findByFieldNameContaining(fieldName);
    }

    public List<FieldModel> getFieldsByCity(String city) {
        return fieldRepository.findByCity(city);
    }

    public List<FieldModel> getFieldsByProvince(String province) {
        return fieldRepository.findByProvince(province);
    }

    public List<FieldModel> getFieldsByFieldType(String fieldType) {
        return fieldRepository.findByFieldType(fieldType);
    }
}
