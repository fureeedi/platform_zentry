package com.edu.sena.zentry.service.mapper;

import com.edu.sena.zentry.domain.Reservas;
//import com.edu.sena.zentry.domain.ServicioConjunto;
import com.edu.sena.zentry.domain.Vinculado;
import com.edu.sena.zentry.service.dto.ReservasDTO;
//import com.edu.sena.zentry.service.dto.ServicioConjuntoDTO;
import com.edu.sena.zentry.service.dto.VinculadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reservas} and its DTO {@link ReservasDTO}.
 */
@Mapper(componentModel = "spring", uses = { ServicioConjuntoMapper.class })
public interface ReservasMapper extends EntityMapper<ReservasDTO, Reservas> {
    @Mapping(target = "servicioConjunto", source = "servicioConjunto")
    @Mapping(target = "vinculado", source = "vinculado", qualifiedByName = "vinculadoResumen")
    ReservasDTO toDto(Reservas s);

    /*@Named("servicioConjuntoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "servicio", source = "servicio")
    ServicioConjuntoDTO toDtoServicioConjuntoId(ServicioConjunto servicioConjunto);*/

    @Named("vinculadoResumen")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombres", source = "nombres")
    @Mapping(target = "apellidos", source = "apellidos")
    @Mapping(target = "numeroDocumento", source = "numeroDocumento")
    VinculadoDTO toDtoVinculadoResumen(Vinculado vinculado);
}
