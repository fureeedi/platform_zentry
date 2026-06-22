package com.edu.sena.zentry.service.mapper;

import com.edu.sena.zentry.domain.TipoDocumento;
import com.edu.sena.zentry.service.dto.TipoDocumentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoDocumento} and its DTO {@link TipoDocumentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoDocumentoMapper extends EntityMapper<TipoDocumentoDTO, TipoDocumento> {}
