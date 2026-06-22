package com.edu.sena.zentry.service.mapper;

import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.domain.Servicio;
import com.edu.sena.zentry.domain.ServicioConjunto;
import com.edu.sena.zentry.service.dto.ConjuntoResidencialDTO;
import com.edu.sena.zentry.service.dto.ServicioConjuntoDTO;
import com.edu.sena.zentry.service.dto.ServicioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServicioConjunto} and its DTO {@link ServicioConjuntoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServicioConjuntoMapper extends EntityMapper<ServicioConjuntoDTO, ServicioConjunto> {
    @Mapping(target = "conjuntoResidencial", source = "conjuntoResidencial", qualifiedByName = "conjuntoResidencialNombreConjunto")
    @Mapping(target = "servicio", source = "servicio", qualifiedByName = "servicioNombreZonaComun")
    ServicioConjuntoDTO toDto(ServicioConjunto s);

    @Named("conjuntoResidencialNombreConjunto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreConjunto", source = "nombreConjunto")
    ConjuntoResidencialDTO toDtoConjuntoResidencialNombreConjunto(ConjuntoResidencial conjuntoResidencial);

    @Named("servicioNombreZonaComun")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreZonaComun", source = "nombreZonaComun")
    ServicioDTO toDtoServicioNombreZonaComun(Servicio servicio);
}
