package com.edu.sena.zentry.service.impl;

import com.edu.sena.zentry.domain.Inmueble;
import com.edu.sena.zentry.repository.InmuebleRepository;
import com.edu.sena.zentry.service.InmuebleService;
import com.edu.sena.zentry.service.dto.InmuebleDTO;
import com.edu.sena.zentry.service.mapper.InmuebleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.edu.sena.zentry.domain.Inmueble}.
 */
@Service
public class InmuebleServiceImpl implements InmuebleService {

    private static final Logger LOG = LoggerFactory.getLogger(InmuebleServiceImpl.class);

    private final InmuebleRepository inmuebleRepository;

    private final InmuebleMapper inmuebleMapper;

    public InmuebleServiceImpl(InmuebleRepository inmuebleRepository, InmuebleMapper inmuebleMapper) {
        this.inmuebleRepository = inmuebleRepository;
        this.inmuebleMapper = inmuebleMapper;
    }

    @Override
    public InmuebleDTO save(InmuebleDTO inmuebleDTO) {
        LOG.debug("Request to save Inmueble : {}", inmuebleDTO);
        Inmueble inmueble = inmuebleMapper.toEntity(inmuebleDTO);
        inmueble = inmuebleRepository.save(inmueble);
        return inmuebleMapper.toDto(inmueble);
    }

    @Override
    public InmuebleDTO update(InmuebleDTO inmuebleDTO) {
        LOG.debug("Request to update Inmueble : {}", inmuebleDTO);
        Inmueble inmueble = inmuebleMapper.toEntity(inmuebleDTO);
        inmueble = inmuebleRepository.save(inmueble);
        return inmuebleMapper.toDto(inmueble);
    }

    @Override
    public Optional<InmuebleDTO> partialUpdate(InmuebleDTO inmuebleDTO) {
        LOG.debug("Request to partially update Inmueble : {}", inmuebleDTO);

        return inmuebleRepository
            .findById(inmuebleDTO.getId())
            .map(existingInmueble -> {
                inmuebleMapper.partialUpdate(existingInmueble, inmuebleDTO);

                return existingInmueble;
            })
            .map(inmuebleRepository::save)
            .map(inmuebleMapper::toDto);
    }

    @Override
    public Page<InmuebleDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Inmuebles");
        return inmuebleRepository.findAll(pageable).map(inmuebleMapper::toDto);
    }

    public Page<InmuebleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return inmuebleRepository.findAllWithEagerRelationships(pageable).map(inmuebleMapper::toDto);
    }

    @Override
    public Optional<InmuebleDTO> findOne(String id) {
        LOG.debug("Request to get Inmueble : {}", id);
        return inmuebleRepository.findOneWithEagerRelationships(id).map(inmuebleMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Inmueble : {}", id);
        inmuebleRepository.deleteById(id);
    }
}
