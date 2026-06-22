package com.edu.sena.zentry.service.mapper;

import com.edu.sena.zentry.domain.Inmueble;
import com.edu.sena.zentry.domain.Vinculado;
import com.edu.sena.zentry.domain.VinculadoInmueble;
import com.edu.sena.zentry.service.dto.InmuebleDTO;
import com.edu.sena.zentry.service.dto.VinculadoDTO;
import com.edu.sena.zentry.service.dto.VinculadoInmuebleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VinculadoInmueble} and its DTO {@link VinculadoInmuebleDTO}.
 */
@Mapper(componentModel = "spring")
public interface VinculadoInmuebleMapper extends EntityMapper<VinculadoInmuebleDTO, VinculadoInmueble> {
    @Mapping(target = "vinculado", source = "vinculado", qualifiedByName = "vinculadoNumeroDocumento")
    @Mapping(target = "inmueble", source = "inmueble", qualifiedByName = "inmuebleNumeroInmueble")
    VinculadoInmuebleDTO toDto(VinculadoInmueble s);

    @Named("vinculadoNumeroDocumento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroDocumento", source = "numeroDocumento")
    VinculadoDTO toDtoVinculadoNumeroDocumento(Vinculado vinculado);

    @Named("inmuebleNumeroInmueble")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroInmueble", source = "numeroInmueble")
    InmuebleDTO toDtoInmuebleNumeroInmueble(Inmueble inmueble);
}
