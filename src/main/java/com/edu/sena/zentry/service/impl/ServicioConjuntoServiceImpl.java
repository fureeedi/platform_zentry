package com.edu.sena.zentry.service.impl;

import com.edu.sena.zentry.domain.ServicioConjunto;
import com.edu.sena.zentry.repository.ServicioConjuntoRepository;
import com.edu.sena.zentry.service.ServicioConjuntoService;
import com.edu.sena.zentry.service.dto.ServicioConjuntoDTO;
import com.edu.sena.zentry.service.mapper.ServicioConjuntoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.edu.sena.zentry.domain.ServicioConjunto}.
 */
@Service
public class ServicioConjuntoServiceImpl implements ServicioConjuntoService {

    private static final Logger LOG = LoggerFactory.getLogger(ServicioConjuntoServiceImpl.class);

    private final ServicioConjuntoRepository servicioConjuntoRepository;

    private final ServicioConjuntoMapper servicioConjuntoMapper;

    public ServicioConjuntoServiceImpl(
        ServicioConjuntoRepository servicioConjuntoRepository,
        ServicioConjuntoMapper servicioConjuntoMapper
    ) {
        this.servicioConjuntoRepository = servicioConjuntoRepository;
        this.servicioConjuntoMapper = servicioConjuntoMapper;
    }

    @Override
    public ServicioConjuntoDTO save(ServicioConjuntoDTO servicioConjuntoDTO) {
        LOG.debug("Request to save ServicioConjunto : {}", servicioConjuntoDTO);
        ServicioConjunto servicioConjunto = servicioConjuntoMapper.toEntity(servicioConjuntoDTO);
        servicioConjunto = servicioConjuntoRepository.save(servicioConjunto);
        return servicioConjuntoMapper.toDto(servicioConjunto);
    }

    @Override
    public ServicioConjuntoDTO update(ServicioConjuntoDTO servicioConjuntoDTO) {
        LOG.debug("Request to update ServicioConjunto : {}", servicioConjuntoDTO);
        ServicioConjunto servicioConjunto = servicioConjuntoMapper.toEntity(servicioConjuntoDTO);
        servicioConjunto = servicioConjuntoRepository.save(servicioConjunto);
        return servicioConjuntoMapper.toDto(servicioConjunto);
    }

    @Override
    public Optional<ServicioConjuntoDTO> partialUpdate(ServicioConjuntoDTO servicioConjuntoDTO) {
        LOG.debug("Request to partially update ServicioConjunto : {}", servicioConjuntoDTO);

        return servicioConjuntoRepository
            .findById(servicioConjuntoDTO.getId())
            .map(existingServicioConjunto -> {
                servicioConjuntoMapper.partialUpdate(existingServicioConjunto, servicioConjuntoDTO);

                return existingServicioConjunto;
            })
            .map(servicioConjuntoRepository::save)
            .map(servicioConjuntoMapper::toDto);
    }

    @Override
    public Page<ServicioConjuntoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ServicioConjuntos");
        return servicioConjuntoRepository.findAll(pageable).map(servicioConjuntoMapper::toDto);
    }

    public Page<ServicioConjuntoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return servicioConjuntoRepository.findAllWithEagerRelationships(pageable).map(servicioConjuntoMapper::toDto);
    }

    @Override
    public Optional<ServicioConjuntoDTO> findOne(String id) {
        LOG.debug("Request to get ServicioConjunto : {}", id);
        return servicioConjuntoRepository.findOneWithEagerRelationships(id).map(servicioConjuntoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete ServicioConjunto : {}", id);
        servicioConjuntoRepository.deleteById(id);
    }
}
