package com.edu.sena.zentry.service.impl;

import com.edu.sena.zentry.domain.AdministradorConjunto;
import com.edu.sena.zentry.domain.Anuncios;
import com.edu.sena.zentry.domain.User;
import com.edu.sena.zentry.repository.AdministradorConjuntoRepository;
import com.edu.sena.zentry.repository.AnunciosRepository;
import com.edu.sena.zentry.repository.UserRepository;
import com.edu.sena.zentry.security.SecurityUtils;
import com.edu.sena.zentry.service.AnunciosService;
import com.edu.sena.zentry.service.dto.AnunciosDTO;
import com.edu.sena.zentry.service.mapper.AnunciosMapper;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.edu.sena.zentry.domain.Anuncios}.
 */
@Service
public class AnunciosServiceImpl implements AnunciosService {

    private static final Logger LOG = LoggerFactory.getLogger(AnunciosServiceImpl.class);

    private final AnunciosRepository anunciosRepository;

    private final AnunciosMapper anunciosMapper;

    private final UserRepository userRepository;

    private final AdministradorConjuntoRepository administradorConjuntoRepository;

    public AnunciosServiceImpl(
        AnunciosRepository anunciosRepository,
        AnunciosMapper anunciosMapper,
        UserRepository userRepository,
        AdministradorConjuntoRepository administradorConjuntoRepository
    ) {
        this.anunciosRepository = anunciosRepository;
        this.anunciosMapper = anunciosMapper;
        this.userRepository = userRepository;
        this.administradorConjuntoRepository = administradorConjuntoRepository;
    }

    @Override
    public AnunciosDTO save(AnunciosDTO anunciosDTO) {
        LOG.debug("Request to save Anuncios : {}", anunciosDTO);
        Anuncios anuncios = anunciosMapper.toEntity(anunciosDTO);

        // Obtiene usuario autenticado
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow();

        // Obtiene usuario que inició sesión
        User user = userRepository.findOneByLogin(login).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtiene el administrador conjunto asociado al usuario autenticado
        AdministradorConjunto administradorConjunto = administradorConjuntoRepository
            .findOneByUser(user)
            .orElseThrow(() -> new RuntimeException("Administrador del conjunto no encontrado"));

        // Asigna el administrador y el conjunto residencial al anuncio
        anuncios.setAdministradorConjunto(administradorConjunto);
        anuncios.setConjuntoResidencial(administradorConjunto.getConjuntoResidencial());

        // Asignar fecha y hora
        anuncios.setFecha(ZonedDateTime.now());

        anuncios = anunciosRepository.save(anuncios);
        return anunciosMapper.toDto(anuncios);
    }

    @Override
    public AnunciosDTO update(AnunciosDTO anunciosDTO) {
        LOG.debug("Request to update Anuncios : {}", anunciosDTO);
        Anuncios anuncios = anunciosMapper.toEntity(anunciosDTO);

        // Obtiene usuario autenticado
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow();

        // Obtiene usuario que inició sesión
        User user = userRepository.findOneByLogin(login).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtiene el administrador conjunto asociado al usuario autenticado
        AdministradorConjunto administradorConjunto = administradorConjuntoRepository
            .findOneByUser(user)
            .orElseThrow(() -> new RuntimeException("Administrador del conjunto no encontrado"));

        // Asigna el administrador y el conjunto residencial al anuncio
        anuncios.setAdministradorConjunto(administradorConjunto);
        anuncios.setConjuntoResidencial(administradorConjunto.getConjuntoResidencial());

        anuncios = anunciosRepository.save(anuncios);
        return anunciosMapper.toDto(anuncios);
    }

    @Override
    public Optional<AnunciosDTO> partialUpdate(AnunciosDTO anunciosDTO) {
        LOG.debug("Request to partially update Anuncios : {}", anunciosDTO);

        // Obtiene el usuario autenticado
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow();

        // Recupera el usuario que inició sesión
        User user = userRepository.findOneByLogin(login).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtiene el administrador asociado al usuario autenticado
        AdministradorConjunto administradorConjunto = administradorConjuntoRepository
            .findOneByUser(user)
            .orElseThrow(() -> new RuntimeException("Administrador del conjunto no encontrado"));

        return anunciosRepository
            .findById(anunciosDTO.getId())
            .map(existingAnuncios -> {
                anunciosMapper.partialUpdate(existingAnuncios, anunciosDTO);

                // Mantiene el administrador y el conjunto del usuario autenticado
                existingAnuncios.setAdministradorConjunto(administradorConjunto);
                existingAnuncios.setConjuntoResidencial(administradorConjunto.getConjuntoResidencial());

                return existingAnuncios;
            })
            .map(anunciosRepository::save)
            .map(anunciosMapper::toDto);
    }

    @Override
    public Page<AnunciosDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Anuncioses");
        return anunciosRepository.findAll(pageable).map(anunciosMapper::toDto);
    }

    public Page<AnunciosDTO> findAllWithEagerRelationships(Pageable pageable) {
        return anunciosRepository.findAllWithEagerRelationships(pageable).map(anunciosMapper::toDto);
    }

    @Override
    public Optional<AnunciosDTO> findOne(String id) {
        LOG.debug("Request to get Anuncios : {}", id);
        return anunciosRepository.findOneWithEagerRelationships(id).map(anunciosMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Anuncios : {}", id);
        anunciosRepository.deleteById(id);
    }
}
