package com.edu.sena.zentry.service.mapper;

import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.service.dto.ConjuntoResidencialDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConjuntoResidencial} and its DTO {@link ConjuntoResidencialDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConjuntoResidencialMapper extends EntityMapper<ConjuntoResidencialDTO, ConjuntoResidencial> {}
