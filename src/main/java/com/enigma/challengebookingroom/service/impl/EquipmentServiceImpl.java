package com.enigma.challengebookingroom.service.impl;

import com.enigma.challengebookingroom.dto.request.EquipmentRequest;
import com.enigma.challengebookingroom.dto.response.EquipmentResponse;
import com.enigma.challengebookingroom.entity.Equipment;
import com.enigma.challengebookingroom.mapper.EquipmentMapper;
import com.enigma.challengebookingroom.repository.EquipmentRepository;
import com.enigma.challengebookingroom.service.EquipmentService;
import com.enigma.challengebookingroom.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final ValidationUtils validator;
    private final EquipmentMapper equipmentMapper;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Equipment create(EquipmentRequest equipmentRequest) {
        validator.validate(equipmentRequest);
        if(equipmentRepository.existsByEquipmentName(equipmentRequest.getEquipmentName()))
        {
            return equipmentRepository.findByEquipmentName(equipmentRequest.getEquipmentName()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase())
            );
        }
        Equipment equipment = Equipment
                .builder()
                .equipmentName(equipmentRequest.getEquipmentName())
                .build();
        return equipmentRepository.saveAndFlush(equipment);
    }

    @Transactional(readOnly = true)
    @Override
    public Equipment getById(String id) {
        return equipmentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase())
        );
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Equipment update(EquipmentRequest equipmentRequest) {
        Equipment byId = getById(equipmentRequest.getIdEquipment());
        byId.setEquipmentName(equipmentRequest.getEquipmentName());
        return equipmentRepository.saveAndFlush(byId);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Equipment byId = getById(id);
        equipmentRepository.delete(byId);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public EquipmentResponse createResponse(EquipmentRequest equipmentRequest) {
        Equipment equipment = create(equipmentRequest);
        return equipmentMapper.toResponse(equipment);
    }

    @Transactional(readOnly = true)
    @Override
    public EquipmentResponse getResponseById(String id) {
        Equipment byId = getById(id);
        return equipmentMapper.toResponse(byId);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public EquipmentResponse updateResponse(EquipmentRequest equipmentRequest) {
        Equipment update = update(equipmentRequest);
        return equipmentMapper.toResponse(update);
    }
}
