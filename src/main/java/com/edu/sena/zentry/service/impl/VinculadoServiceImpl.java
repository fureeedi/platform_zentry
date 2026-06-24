package com.edu.sena.zentry.service.impl;

import com.edu.sena.zentry.domain.Authority;
import com.edu.sena.zentry.domain.User;
import com.edu.sena.zentry.domain.Vinculado;
import com.edu.sena.zentry.repository.AuthorityRepository;
import com.edu.sena.zentry.repository.UserRepository;
import com.edu.sena.zentry.repository.VinculadoRepository;
import com.edu.sena.zentry.service.VinculadoService;
import com.edu.sena.zentry.service.dto.VinculadoDTO;
import com.edu.sena.zentry.service.mapper.VinculadoMapper;
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
 * Service Implementation for managing {@link com.edu.sena.zentry.domain.Vinculado}.
 */
@Service
public class VinculadoServiceImpl implements VinculadoService {

    private static final Logger LOG = LoggerFactory.getLogger(VinculadoServiceImpl.class);

    private final VinculadoRepository vinculadoRepository;

    private final VinculadoMapper vinculadoMapper;

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    public VinculadoServiceImpl(
        VinculadoRepository vinculadoRepository,
        VinculadoMapper vinculadoMapper,
        UserRepository userRepository,
        AuthorityRepository authorityRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.vinculadoRepository = vinculadoRepository;
        this.vinculadoMapper = vinculadoMapper;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public VinculadoDTO save(VinculadoDTO vinculadoDTO) {
        LOG.debug("Request to save Vinculado : {}", vinculadoDTO);

        //crear user vinculado
        User user = new User();
        user.setLogin(vinculadoDTO.getLogin().toLowerCase());
        user.setEmail(vinculadoDTO.getCorreo());
        user.setActivated(true);
        user.setLangKey("es");
        user.setPassword(passwordEncoder.encode(vinculadoDTO.getPassword()));

        //asignar rol a vinculado
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById("ROLE_CLIENTE").ifPresent(authorities::add);
        authorityRepository.findById("ROLE_USER").ifPresent(authorities::add);
        user.setAuthorities(authorities);

        //guardar user vinculado
        user = userRepository.save(user);

        Vinculado vinculado = vinculadoMapper.toEntity(vinculadoDTO);
        vinculado.setUser(user);
        vinculado = vinculadoRepository.save(vinculado);
        return vinculadoMapper.toDto(vinculado);
    }

    @Override
    public VinculadoDTO update(VinculadoDTO vinculadoDTO) {
        LOG.debug("Request to update Vinculado : {}", vinculadoDTO);
        Vinculado vinculado = vinculadoMapper.toEntity(vinculadoDTO);
        vinculado = vinculadoRepository.save(vinculado);
        return vinculadoMapper.toDto(vinculado);
    }

    @Override
    public Optional<VinculadoDTO> partialUpdate(VinculadoDTO vinculadoDTO) {
        LOG.debug("Request to partially update Vinculado : {}", vinculadoDTO);

        return vinculadoRepository
            .findById(vinculadoDTO.getId())
            .map(existingVinculado -> {
                vinculadoMapper.partialUpdate(existingVinculado, vinculadoDTO);

                return existingVinculado;
            })
            .map(vinculadoRepository::save)
            .map(vinculadoMapper::toDto);
    }

    @Override
    public Page<VinculadoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Vinculados");
        return vinculadoRepository.findAll(pageable).map(vinculadoMapper::toDto);
    }

    public Page<VinculadoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return vinculadoRepository.findAllWithEagerRelationships(pageable).map(vinculadoMapper::toDto);
    }

    @Override
    public Optional<VinculadoDTO> findOne(String id) {
        LOG.debug("Request to get Vinculado : {}", id);
        return vinculadoRepository.findOneWithEagerRelationships(id).map(vinculadoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Vinculado : {}", id);
        vinculadoRepository.deleteById(id);
    }
}
