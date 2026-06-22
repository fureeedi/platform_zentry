package com.edu.sena.zentry.service.mapper;

import com.edu.sena.zentry.domain.AdministradorConjunto;
import com.edu.sena.zentry.domain.Anuncios;
import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.service.dto.AdministradorConjuntoDTO;
import com.edu.sena.zentry.service.dto.AnunciosDTO;
import com.edu.sena.zentry.service.dto.ConjuntoResidencialDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Anuncios} and its DTO {@link AnunciosDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnunciosMapper extends EntityMapper<AnunciosDTO, Anuncios> {
    @Mapping(target = "conjuntoResidencial", source = "conjuntoResidencial", qualifiedByName = "conjuntoResidencialNombreConjunto")
    @Mapping(target = "administradorConjunto", source = "administradorConjunto", qualifiedByName = "administradorConjuntoNumeroDocumento")
    AnunciosDTO toDto(Anuncios s);

    @Named("conjuntoResidencialNombreConjunto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreConjunto", source = "nombreConjunto")
    ConjuntoResidencialDTO toDtoConjuntoResidencialNombreConjunto(ConjuntoResidencial conjuntoResidencial);

    @Named("administradorConjuntoNumeroDocumento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroDocumento", source = "numeroDocumento")
    AdministradorConjuntoDTO toDtoAdministradorConjuntoNumeroDocumento(AdministradorConjunto administradorConjunto);
}
