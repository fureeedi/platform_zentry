package com.edu.sena.zentry.service.impl;

import com.edu.sena.zentry.domain.AdministradorConjunto;
import com.edu.sena.zentry.domain.Authority;
import com.edu.sena.zentry.domain.User;
import com.edu.sena.zentry.repository.AdministradorConjuntoRepository;
import com.edu.sena.zentry.repository.AuthorityRepository;
import com.edu.sena.zentry.repository.UserRepository;
import com.edu.sena.zentry.service.AdministradorConjuntoService;
import com.edu.sena.zentry.service.dto.AdministradorConjuntoDTO;
import com.edu.sena.zentry.service.mapper.AdministradorConjuntoMapper;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.edu.sena.zentry.domain.AdministradorConjunto}.
 */
@Service
public class AdministradorConjuntoServiceImpl implements AdministradorConjuntoService {

    private static final Logger LOG = LoggerFactory.getLogger(AdministradorConjuntoServiceImpl.class);

    private final AdministradorConjuntoRepository administradorConjuntoRepository;

    private final AdministradorConjuntoMapper administradorConjuntoMapper;

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    public AdministradorConjuntoServiceImpl(
        AdministradorConjuntoRepository administradorConjuntoRepository,
        AdministradorConjuntoMapper administradorConjuntoMapper,
        UserRepository userRepository,
        AuthorityRepository authorityRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.administradorConjuntoRepository = administradorConjuntoRepository;
        this.administradorConjuntoMapper = administradorConjuntoMapper;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AdministradorConjuntoDTO save(AdministradorConjuntoDTO administradorConjuntoDTO) {
        LOG.debug("Request to save AdministradorConjunto : {}", administradorConjuntoDTO);

        // crear user administrador conjunto
        User user = new User();
        user.setLogin(administradorConjuntoDTO.getLogin().toLowerCase());
        user.setEmail(administradorConjuntoDTO.getCorreo());
        user.setActivated(true);
        user.setLangKey("es");
        user.setPassword(passwordEncoder.encode(administradorConjuntoDTO.getPassword()));

        //asignar rol a administrador conjunto
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById("ROLE_ADMINISTRADOR_CONJUNTO").ifPresent(authorities::add);
        authorityRepository.findById("ROLE_USER").ifPresent(authorities::add);
        user.setAuthorities(authorities);

        //guardar user
        user = userRepository.save(user);

        AdministradorConjunto administradorConjunto = administradorConjuntoMapper.toEntity(administradorConjuntoDTO);
        administradorConjunto.setUser(user);
        administradorConjunto = administradorConjuntoRepository.save(administradorConjunto);
        return administradorConjuntoMapper.toDto(administradorConjunto);
    }

    @Override
    public AdministradorConjuntoDTO update(AdministradorConjuntoDTO administradorConjuntoDTO) {
        LOG.debug("Request to update AdministradorConjunto : {}", administradorConjuntoDTO);
        AdministradorConjunto administradorConjunto = administradorConjuntoMapper.toEntity(administradorConjuntoDTO);
        administradorConjunto = administradorConjuntoRepository.save(administradorConjunto);
        return administradorConjuntoMapper.toDto(administradorConjunto);
    }

    @Override
    public Optional<AdministradorConjuntoDTO> partialUpdate(AdministradorConjuntoDTO administradorConjuntoDTO) {
        LOG.debug("Request to partially update AdministradorConjunto : {}", administradorConjuntoDTO);

        return administradorConjuntoRepository
            .findById(administradorConjuntoDTO.getId())
            .map(existingAdministradorConjunto -> {
                administradorConjuntoMapper.partialUpdate(existingAdministradorConjunto, administradorConjuntoDTO);

                return existingAdministradorConjunto;
            })
            .map(administradorConjuntoRepository::save)
            .map(administradorConjuntoMapper::toDto);
    }

    @Override
    public Page<AdministradorConjuntoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all AdministradorConjuntos");
        return administradorConjuntoRepository.findAll(pageable).map(administradorConjuntoMapper::toDto);
    }

    public Page<AdministradorConjuntoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return administradorConjuntoRepository.findAllWithEagerRelationships(pageable).map(administradorConjuntoMapper::toDto);
    }

    @Override
    public Optional<AdministradorConjuntoDTO> findOne(String id) {
        LOG.debug("Request to get AdministradorConjunto : {}", id);
        return administradorConjuntoRepository.findOneWithEagerRelationships(id).map(administradorConjuntoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete AdministradorConjunto : {}", id);
        administradorConjuntoRepository.deleteById(id);
    }
}
