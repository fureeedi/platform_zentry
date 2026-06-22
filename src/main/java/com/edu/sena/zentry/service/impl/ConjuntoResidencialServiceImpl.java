package com.edu.sena.zentry.service.impl;

import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.repository.ConjuntoResidencialRepository;
import com.edu.sena.zentry.service.ConjuntoResidencialService;
import com.edu.sena.zentry.service.dto.ConjuntoResidencialDTO;
import com.edu.sena.zentry.service.mapper.ConjuntoResidencialMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.edu.sena.zentry.domain.ConjuntoResidencial}.
 */
@Service
public class ConjuntoResidencialServiceImpl implements ConjuntoResidencialService {

    private static final Logger LOG = LoggerFactory.getLogger(ConjuntoResidencialServiceImpl.class);

    private final ConjuntoResidencialRepository conjuntoResidencialRepository;

    private final ConjuntoResidencialMapper conjuntoResidencialMapper;

    public ConjuntoResidencialServiceImpl(
        ConjuntoResidencialRepository conjuntoResidencialRepository,
        ConjuntoResidencialMapper conjuntoResidencialMapper
    ) {
        this.conjuntoResidencialRepository = conjuntoResidencialRepository;
        this.conjuntoResidencialMapper = conjuntoResidencialMapper;
    }

    @Override
    public ConjuntoResidencialDTO save(ConjuntoResidencialDTO conjuntoResidencialDTO) {
        LOG.debug("Request to save ConjuntoResidencial : {}", conjuntoResidencialDTO);
        ConjuntoResidencial conjuntoResidencial = conjuntoResidencialMapper.toEntity(conjuntoResidencialDTO);
        conjuntoResidencial = conjuntoResidencialRepository.save(conjuntoResidencial);
        return conjuntoResidencialMapper.toDto(conjuntoResidencial);
    }

    @Override
    public ConjuntoResidencialDTO update(ConjuntoResidencialDTO conjuntoResidencialDTO) {
        LOG.debug("Request to update ConjuntoResidencial : {}", conjuntoResidencialDTO);
        ConjuntoResidencial conjuntoResidencial = conjuntoResidencialMapper.toEntity(conjuntoResidencialDTO);
        conjuntoResidencial = conjuntoResidencialRepository.save(conjuntoResidencial);
        return conjuntoResidencialMapper.toDto(conjuntoResidencial);
    }

    @Override
    public Optional<ConjuntoResidencialDTO> partialUpdate(ConjuntoResidencialDTO conjuntoResidencialDTO) {
        LOG.debug("Request to partially update ConjuntoResidencial : {}", conjuntoResidencialDTO);

        return conjuntoResidencialRepository
            .findById(conjuntoResidencialDTO.getId())
            .map(existingConjuntoResidencial -> {
                conjuntoResidencialMapper.partialUpdate(existingConjuntoResidencial, conjuntoResidencialDTO);

                return existingConjuntoResidencial;
            })
            .map(conjuntoResidencialRepository::save)
            .map(conjuntoResidencialMapper::toDto);
    }

    @Override
    public Page<ConjuntoResidencialDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ConjuntoResidencials");
        return conjuntoResidencialRepository.findAll(pageable).map(conjuntoResidencialMapper::toDto);
    }

    @Override
    public Optional<ConjuntoResidencialDTO> findOne(String id) {
        LOG.debug("Request to get ConjuntoResidencial : {}", id);
        return conjuntoResidencialRepository.findById(id).map(conjuntoResidencialMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete ConjuntoResidencial : {}", id);
        conjuntoResidencialRepository.deleteById(id);
    }
}
