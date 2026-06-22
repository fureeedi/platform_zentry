package com.edu.sena.zentry.service.impl;

import com.edu.sena.zentry.domain.Anuncios;
import com.edu.sena.zentry.repository.AnunciosRepository;
import com.edu.sena.zentry.service.AnunciosService;
import com.edu.sena.zentry.service.dto.AnunciosDTO;
import com.edu.sena.zentry.service.mapper.AnunciosMapper;
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

    public AnunciosServiceImpl(AnunciosRepository anunciosRepository, AnunciosMapper anunciosMapper) {
        this.anunciosRepository = anunciosRepository;
        this.anunciosMapper = anunciosMapper;
    }

    @Override
    public AnunciosDTO save(AnunciosDTO anunciosDTO) {
        LOG.debug("Request to save Anuncios : {}", anunciosDTO);
        Anuncios anuncios = anunciosMapper.toEntity(anunciosDTO);
        anuncios = anunciosRepository.save(anuncios);
        return anunciosMapper.toDto(anuncios);
    }

    @Override
    public AnunciosDTO update(AnunciosDTO anunciosDTO) {
        LOG.debug("Request to update Anuncios : {}", anunciosDTO);
        Anuncios anuncios = anunciosMapper.toEntity(anunciosDTO);
        anuncios = anunciosRepository.save(anuncios);
        return anunciosMapper.toDto(anuncios);
    }

    @Override
    public Optional<AnunciosDTO> partialUpdate(AnunciosDTO anunciosDTO) {
        LOG.debug("Request to partially update Anuncios : {}", anunciosDTO);

        return anunciosRepository
            .findById(anunciosDTO.getId())
            .map(existingAnuncios -> {
                anunciosMapper.partialUpdate(existingAnuncios, anunciosDTO);

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
