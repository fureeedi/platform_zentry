package com.edu.sena.zentry.service.mapper;

import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.domain.Inmueble;
import com.edu.sena.zentry.service.dto.ConjuntoResidencialDTO;
import com.edu.sena.zentry.service.dto.InmuebleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inmueble} and its DTO {@link InmuebleDTO}.
 */
@Mapper(componentModel = "spring")
public interface InmuebleMapper extends EntityMapper<InmuebleDTO, Inmueble> {
    @Mapping(target = "conjuntoResidencial", source = "conjuntoResidencial", qualifiedByName = "conjuntoResidencialNombreConjunto")
    InmuebleDTO toDto(Inmueble s);

    @Named("conjuntoResidencialNombreConjunto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreConjunto", source = "nombreConjunto")
    ConjuntoResidencialDTO toDtoConjuntoResidencialNombreConjunto(ConjuntoResidencial conjuntoResidencial);
}
