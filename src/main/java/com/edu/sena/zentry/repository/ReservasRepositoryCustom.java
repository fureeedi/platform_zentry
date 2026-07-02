package com.edu.sena.zentry.repository;

import com.edu.sena.zentry.domain.Reservas;
import com.edu.sena.zentry.domain.enumeration.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservasRepositoryCustom {
    Page<Reservas> buscarPorFiltros(Estado estado, String servicioId, Pageable pageable);
}
