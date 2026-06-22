package com.edu.sena.zentry.service.impl;

import com.edu.sena.zentry.domain.VinculadoInmueble;
import com.edu.sena.zentry.repository.VinculadoInmuebleRepository;
import com.edu.sena.zentry.service.VinculadoInmuebleService;
import com.edu.sena.zentry.service.dto.VinculadoInmuebleDTO;
import com.edu.sena.zentry.service.mapper.VinculadoInmuebleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.edu.sena.zentry.domain.VinculadoInmueble}.
 */
@Service
public class VinculadoInmuebleServiceImpl implements VinculadoInmuebleService {

    private static final Logger LOG = LoggerFactory.getLogger(VinculadoInmuebleServiceImpl.class);

    private final VinculadoInmuebleRepository vinculadoInmuebleRepository;

    private final VinculadoInmuebleMapper vinculadoInmuebleMapper;

    public VinculadoInmuebleServiceImpl(
        VinculadoInmuebleRepository vinculadoInmuebleRepository,
        VinculadoInmuebleMapper vinculadoInmuebleMapper
    ) {
        this.vinculadoInmuebleRepository = vinculadoInmuebleRepository;
        this.vinculadoInmuebleMapper = vinculadoInmuebleMapper;
    }

    @Override
    public VinculadoInmuebleDTO save(VinculadoInmuebleDTO vinculadoInmuebleDTO) {
        LOG.debug("Request to save VinculadoInmueble : {}", vinculadoInmuebleDTO);
        VinculadoInmueble vinculadoInmueble = vinculadoInmuebleMapper.toEntity(vinculadoInmuebleDTO);
        vinculadoInmueble = vinculadoInmuebleRepository.save(vinculadoInmueble);
        return vinculadoInmuebleMapper.toDto(vinculadoInmueble);
    }

    @Override
    public VinculadoInmuebleDTO update(VinculadoInmuebleDTO vinculadoInmuebleDTO) {
        LOG.debug("Request to update VinculadoInmueble : {}", vinculadoInmuebleDTO);
        VinculadoInmueble vinculadoInmueble = vinculadoInmuebleMapper.toEntity(vinculadoInmuebleDTO);
        vinculadoInmueble = vinculadoInmuebleRepository.save(vinculadoInmueble);
        return vinculadoInmuebleMapper.toDto(vinculadoInmueble);
    }

    @Override
    public Optional<VinculadoInmuebleDTO> partialUpdate(VinculadoInmuebleDTO vinculadoInmuebleDTO) {
        LOG.debug("Request to partially update VinculadoInmueble : {}", vinculadoInmuebleDTO);

        return vinculadoInmuebleRepository
            .findById(vinculadoInmuebleDTO.getId())
            .map(existingVinculadoInmueble -> {
                vinculadoInmuebleMapper.partialUpdate(existingVinculadoInmueble, vinculadoInmuebleDTO);

                return existingVinculadoInmueble;
            })
            .map(vinculadoInmuebleRepository::save)
            .map(vinculadoInmuebleMapper::toDto);
    }

    @Override
    public Page<VinculadoInmuebleDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all VinculadoInmuebles");
        return vinculadoInmuebleRepository.findAll(pageable).map(vinculadoInmuebleMapper::toDto);
    }

    public Page<VinculadoInmuebleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return vinculadoInmuebleRepository.findAllWithEagerRelationships(pageable).map(vinculadoInmuebleMapper::toDto);
    }

    @Override
    public Optional<VinculadoInmuebleDTO> findOne(String id) {
        LOG.debug("Request to get VinculadoInmueble : {}", id);
        return vinculadoInmuebleRepository.findOneWithEagerRelationships(id).map(vinculadoInmuebleMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete VinculadoInmueble : {}", id);
        vinculadoInmuebleRepository.deleteById(id);
    }
}
