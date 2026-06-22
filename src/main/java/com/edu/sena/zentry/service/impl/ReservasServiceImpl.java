package com.edu.sena.zentry.service.impl;

import com.edu.sena.zentry.domain.Reservas;
import com.edu.sena.zentry.repository.ReservasRepository;
import com.edu.sena.zentry.service.ReservasService;
import com.edu.sena.zentry.service.dto.ReservasDTO;
import com.edu.sena.zentry.service.mapper.ReservasMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.edu.sena.zentry.domain.Reservas}.
 */
@Service
public class ReservasServiceImpl implements ReservasService {

    private static final Logger LOG = LoggerFactory.getLogger(ReservasServiceImpl.class);

    private final ReservasRepository reservasRepository;

    private final ReservasMapper reservasMapper;

    public ReservasServiceImpl(ReservasRepository reservasRepository, ReservasMapper reservasMapper) {
        this.reservasRepository = reservasRepository;
        this.reservasMapper = reservasMapper;
    }

    @Override
    public ReservasDTO save(ReservasDTO reservasDTO) {
        LOG.debug("Request to save Reservas : {}", reservasDTO);
        Reservas reservas = reservasMapper.toEntity(reservasDTO);
        reservas = reservasRepository.save(reservas);
        return reservasMapper.toDto(reservas);
    }

    @Override
    public ReservasDTO update(ReservasDTO reservasDTO) {
        LOG.debug("Request to update Reservas : {}", reservasDTO);
        Reservas reservas = reservasMapper.toEntity(reservasDTO);
        reservas = reservasRepository.save(reservas);
        return reservasMapper.toDto(reservas);
    }

    @Override
    public Optional<ReservasDTO> partialUpdate(ReservasDTO reservasDTO) {
        LOG.debug("Request to partially update Reservas : {}", reservasDTO);

        return reservasRepository
            .findById(reservasDTO.getId())
            .map(existingReservas -> {
                reservasMapper.partialUpdate(existingReservas, reservasDTO);

                return existingReservas;
            })
            .map(reservasRepository::save)
            .map(reservasMapper::toDto);
    }

    @Override
    public Page<ReservasDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Reservases");
        return reservasRepository.findAll(pageable).map(reservasMapper::toDto);
    }

    public Page<ReservasDTO> findAllWithEagerRelationships(Pageable pageable) {
        return reservasRepository.findAllWithEagerRelationships(pageable).map(reservasMapper::toDto);
    }

    @Override
    public Optional<ReservasDTO> findOne(String id) {
        LOG.debug("Request to get Reservas : {}", id);
        return reservasRepository.findOneWithEagerRelationships(id).map(reservasMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Reservas : {}", id);
        reservasRepository.deleteById(id);
    }
}
