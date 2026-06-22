package com.edu.sena.zentry.service.mapper;

import com.edu.sena.zentry.domain.Reservas;
import com.edu.sena.zentry.domain.ServicioConjunto;
import com.edu.sena.zentry.domain.Vinculado;
import com.edu.sena.zentry.service.dto.ReservasDTO;
import com.edu.sena.zentry.service.dto.ServicioConjuntoDTO;
import com.edu.sena.zentry.service.dto.VinculadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reservas} and its DTO {@link ReservasDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservasMapper extends EntityMapper<ReservasDTO, Reservas> {
    @Mapping(target = "servicioConjunto", source = "servicioConjunto", qualifiedByName = "servicioConjuntoId")
    @Mapping(target = "vinculado", source = "vinculado", qualifiedByName = "vinculadoNumeroDocumento")
    ReservasDTO toDto(Reservas s);

    @Named("servicioConjuntoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServicioConjuntoDTO toDtoServicioConjuntoId(ServicioConjunto servicioConjunto);

    @Named("vinculadoNumeroDocumento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroDocumento", source = "numeroDocumento")
    VinculadoDTO toDtoVinculadoNumeroDocumento(Vinculado vinculado);
}
