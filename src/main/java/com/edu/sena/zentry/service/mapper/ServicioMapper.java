package com.edu.sena.zentry.service.mapper;

import com.edu.sena.zentry.domain.Servicio;
import com.edu.sena.zentry.service.dto.ServicioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Servicio} and its DTO {@link ServicioDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServicioMapper extends EntityMapper<ServicioDTO, Servicio> {}
