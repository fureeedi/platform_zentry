package com.edu.sena.zentry.service.mapper;

import com.edu.sena.zentry.domain.AdministradorConjunto;
import com.edu.sena.zentry.domain.TipoDocumento;
import com.edu.sena.zentry.domain.User;
import com.edu.sena.zentry.domain.Vinculado;
import com.edu.sena.zentry.service.dto.AdministradorConjuntoDTO;
import com.edu.sena.zentry.service.dto.TipoDocumentoDTO;
import com.edu.sena.zentry.service.dto.UserDTO;
import com.edu.sena.zentry.service.dto.VinculadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vinculado} and its DTO {@link VinculadoDTO}.
 */
@Mapper(componentModel = "spring")
public interface VinculadoMapper extends EntityMapper<VinculadoDTO, Vinculado> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "tipoDocumento", source = "tipoDocumento", qualifiedByName = "tipoDocumentoNombreTipoDocumento")
    @Mapping(target = "administradorConjunto", source = "administradorConjunto", qualifiedByName = "administradorConjuntoNumeroDocumento")
    VinculadoDTO toDto(Vinculado s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("tipoDocumentoNombreTipoDocumento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreTipoDocumento", source = "nombreTipoDocumento")
    TipoDocumentoDTO toDtoTipoDocumentoNombreTipoDocumento(TipoDocumento tipoDocumento);

    @Named("administradorConjuntoNumeroDocumento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroDocumento", source = "numeroDocumento")
    AdministradorConjuntoDTO toDtoAdministradorConjuntoNumeroDocumento(AdministradorConjunto administradorConjunto);
}
