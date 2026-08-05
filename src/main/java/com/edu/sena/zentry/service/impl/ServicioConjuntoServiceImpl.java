package com.edu.sena.zentry.service.impl;

import com.edu.sena.zentry.domain.AdministradorConjunto;
import com.edu.sena.zentry.domain.ServicioConjunto;
import com.edu.sena.zentry.domain.User;
import com.edu.sena.zentry.repository.AdministradorConjuntoRepository;
import com.edu.sena.zentry.repository.ServicioConjuntoRepository;
import com.edu.sena.zentry.repository.UserRepository;
import com.edu.sena.zentry.security.SecurityUtils;
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

    private final AdministradorConjuntoRepository administradorConjuntoRepository;

    private final UserRepository userRepository;

    public ServicioConjuntoServiceImpl(
        ServicioConjuntoRepository servicioConjuntoRepository,
        ServicioConjuntoMapper servicioConjuntoMapper,
        AdministradorConjuntoRepository administradorConjuntoRepository,
        UserRepository userRepository
    ) {
        this.servicioConjuntoRepository = servicioConjuntoRepository;
        this.servicioConjuntoMapper = servicioConjuntoMapper;
        this.administradorConjuntoRepository = administradorConjuntoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ServicioConjuntoDTO save(ServicioConjuntoDTO servicioConjuntoDTO) {
        LOG.debug("Request to save ServicioConjunto : {}", servicioConjuntoDTO);

        String login = SecurityUtils.getCurrentUserLogin().orElseThrow();

        User user = userRepository.findOneByLogin(login).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        AdministradorConjunto administradorConjunto = administradorConjuntoRepository
            .findOneByUser(user)
            .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

        ServicioConjunto servicioConjunto = servicioConjuntoMapper.toEntity(servicioConjuntoDTO);

        // Asignar automáticamente el conjunto residencial del administrador
        servicioConjunto.setConjuntoResidencial(administradorConjunto.getConjuntoResidencial());

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
