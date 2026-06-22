package com.edu.sena.zentry.service.mapper;

import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.domain.FacturaDePago;
import com.edu.sena.zentry.domain.Vinculado;
import com.edu.sena.zentry.service.dto.ConjuntoResidencialDTO;
import com.edu.sena.zentry.service.dto.FacturaDePagoDTO;
import com.edu.sena.zentry.service.dto.VinculadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FacturaDePago} and its DTO {@link FacturaDePagoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FacturaDePagoMapper extends EntityMapper<FacturaDePagoDTO, FacturaDePago> {
    @Mapping(target = "conjuntoResidencial", source = "conjuntoResidencial", qualifiedByName = "conjuntoResidencialNombreConjunto")
    @Mapping(target = "vinculado", source = "vinculado", qualifiedByName = "vinculadoNumeroDocumento")
    FacturaDePagoDTO toDto(FacturaDePago s);

    @Named("conjuntoResidencialNombreConjunto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreConjunto", source = "nombreConjunto")
    ConjuntoResidencialDTO toDtoConjuntoResidencialNombreConjunto(ConjuntoResidencial conjuntoResidencial);

    @Named("vinculadoNumeroDocumento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroDocumento", source = "numeroDocumento")
    VinculadoDTO toDtoVinculadoNumeroDocumento(Vinculado vinculado);
}
