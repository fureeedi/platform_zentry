package com.edu.sena.zentry.service.impl;

import com.edu.sena.zentry.domain.Servicio;
import com.edu.sena.zentry.repository.ServicioRepository;
import com.edu.sena.zentry.service.ServicioService;
import com.edu.sena.zentry.service.dto.ServicioDTO;
import com.edu.sena.zentry.service.mapper.ServicioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.edu.sena.zentry.domain.Servicio}.
 */
@Service
public class ServicioServiceImpl implements ServicioService {

    private static final Logger LOG = LoggerFactory.getLogger(ServicioServiceImpl.class);

    private final ServicioRepository servicioRepository;

    private final ServicioMapper servicioMapper;

    public ServicioServiceImpl(ServicioRepository servicioRepository, ServicioMapper servicioMapper) {
        this.servicioRepository = servicioRepository;
        this.servicioMapper = servicioMapper;
    }

    @Override
    public ServicioDTO save(ServicioDTO servicioDTO) {
        LOG.debug("Request to save Servicio : {}", servicioDTO);
        Servicio servicio = servicioMapper.toEntity(servicioDTO);
        servicio = servicioRepository.save(servicio);
        return servicioMapper.toDto(servicio);
    }

    @Override
    public ServicioDTO update(ServicioDTO servicioDTO) {
        LOG.debug("Request to update Servicio : {}", servicioDTO);
        Servicio servicio = servicioMapper.toEntity(servicioDTO);
        servicio = servicioRepository.save(servicio);
        return servicioMapper.toDto(servicio);
    }

    @Override
    public Optional<ServicioDTO> partialUpdate(ServicioDTO servicioDTO) {
        LOG.debug("Request to partially update Servicio : {}", servicioDTO);

        return servicioRepository
            .findById(servicioDTO.getId())
            .map(existingServicio -> {
                servicioMapper.partialUpdate(existingServicio, servicioDTO);

                return existingServicio;
            })
            .map(servicioRepository::save)
            .map(servicioMapper::toDto);
    }

    @Override
    public Page<ServicioDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Servicios");
        return servicioRepository.findAll(pageable).map(servicioMapper::toDto);
    }

    @Override
    public Optional<ServicioDTO> findOne(String id) {
        LOG.debug("Request to get Servicio : {}", id);
        return servicioRepository.findById(id).map(servicioMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Servicio : {}", id);
        servicioRepository.deleteById(id);
    }
}
