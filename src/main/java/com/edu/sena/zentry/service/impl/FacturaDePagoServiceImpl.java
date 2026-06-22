package com.edu.sena.zentry.service.impl;

import com.edu.sena.zentry.domain.FacturaDePago;
import com.edu.sena.zentry.repository.FacturaDePagoRepository;
import com.edu.sena.zentry.service.FacturaDePagoService;
import com.edu.sena.zentry.service.dto.FacturaDePagoDTO;
import com.edu.sena.zentry.service.mapper.FacturaDePagoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.edu.sena.zentry.domain.FacturaDePago}.
 */
@Service
public class FacturaDePagoServiceImpl implements FacturaDePagoService {

    private static final Logger LOG = LoggerFactory.getLogger(FacturaDePagoServiceImpl.class);

    private final FacturaDePagoRepository facturaDePagoRepository;

    private final FacturaDePagoMapper facturaDePagoMapper;

    public FacturaDePagoServiceImpl(FacturaDePagoRepository facturaDePagoRepository, FacturaDePagoMapper facturaDePagoMapper) {
        this.facturaDePagoRepository = facturaDePagoRepository;
        this.facturaDePagoMapper = facturaDePagoMapper;
    }

    @Override
    public FacturaDePagoDTO save(FacturaDePagoDTO facturaDePagoDTO) {
        LOG.debug("Request to save FacturaDePago : {}", facturaDePagoDTO);
        FacturaDePago facturaDePago = facturaDePagoMapper.toEntity(facturaDePagoDTO);
        facturaDePago = facturaDePagoRepository.save(facturaDePago);
        return facturaDePagoMapper.toDto(facturaDePago);
    }

    @Override
    public FacturaDePagoDTO update(FacturaDePagoDTO facturaDePagoDTO) {
        LOG.debug("Request to update FacturaDePago : {}", facturaDePagoDTO);
        FacturaDePago facturaDePago = facturaDePagoMapper.toEntity(facturaDePagoDTO);
        facturaDePago = facturaDePagoRepository.save(facturaDePago);
        return facturaDePagoMapper.toDto(facturaDePago);
    }

    @Override
    public Optional<FacturaDePagoDTO> partialUpdate(FacturaDePagoDTO facturaDePagoDTO) {
        LOG.debug("Request to partially update FacturaDePago : {}", facturaDePagoDTO);

        return facturaDePagoRepository
            .findById(facturaDePagoDTO.getId())
            .map(existingFacturaDePago -> {
                facturaDePagoMapper.partialUpdate(existingFacturaDePago, facturaDePagoDTO);

                return existingFacturaDePago;
            })
            .map(facturaDePagoRepository::save)
            .map(facturaDePagoMapper::toDto);
    }

    @Override
    public Page<FacturaDePagoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all FacturaDePagos");
        return facturaDePagoRepository.findAll(pageable).map(facturaDePagoMapper::toDto);
    }

    public Page<FacturaDePagoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return facturaDePagoRepository.findAllWithEagerRelationships(pageable).map(facturaDePagoMapper::toDto);
    }

    @Override
    public Optional<FacturaDePagoDTO> findOne(String id) {
        LOG.debug("Request to get FacturaDePago : {}", id);
        return facturaDePagoRepository.findOneWithEagerRelationships(id).map(facturaDePagoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete FacturaDePago : {}", id);
        facturaDePagoRepository.deleteById(id);
    }
}
