package com.edu.sena.zentry.service.mapper;

import com.edu.sena.zentry.domain.AdministradorConjunto;
import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.domain.TipoDocumento;
import com.edu.sena.zentry.domain.User;
import com.edu.sena.zentry.service.dto.AdministradorConjuntoDTO;
import com.edu.sena.zentry.service.dto.ConjuntoResidencialDTO;
import com.edu.sena.zentry.service.dto.TipoDocumentoDTO;
import com.edu.sena.zentry.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdministradorConjunto} and its DTO {@link AdministradorConjuntoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdministradorConjuntoMapper extends EntityMapper<AdministradorConjuntoDTO, AdministradorConjunto> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "conjuntoResidencial", source = "conjuntoResidencial", qualifiedByName = "conjuntoResidencialNombreConjunto")
    @Mapping(target = "tipoDocumento", source = "tipoDocumento", qualifiedByName = "tipoDocumentoNombreTipoDocumento")
    AdministradorConjuntoDTO toDto(AdministradorConjunto s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("conjuntoResidencialNombreConjunto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreConjunto", source = "nombreConjunto")
    ConjuntoResidencialDTO toDtoConjuntoResidencialNombreConjunto(ConjuntoResidencial conjuntoResidencial);

    @Named("tipoDocumentoNombreTipoDocumento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreTipoDocumento", source = "nombreTipoDocumento")
    TipoDocumentoDTO toDtoTipoDocumentoNombreTipoDocumento(TipoDocumento tipoDocumento);
}
