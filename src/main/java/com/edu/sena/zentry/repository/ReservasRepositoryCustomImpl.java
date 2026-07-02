package com.edu.sena.zentry.repository;

import com.edu.sena.zentry.domain.Reservas;
import com.edu.sena.zentry.domain.enumeration.Estado;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ReservasRepositoryCustomImpl implements ReservasRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public ReservasRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Reservas> buscarPorFiltros(Estado estado, String servicioId, Pageable pageable) {
        Query query = new Query();
        List<Criteria> criterios = new ArrayList<>();
        if (estado != null) {
            criterios.add(Criteria.where("estado").is(estado));
        }

        if (servicioId != null && !servicioId.isBlank()) {
            criterios.add(Criteria.where("servicioConjunto.$id").is(new ObjectId(servicioId)));
        }

        if (!criterios.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criterios));
        }

        long total = mongoTemplate.count(query, Reservas.class);
        query.with(pageable);
        List<Reservas> reservas = mongoTemplate.find(query, Reservas.class);

        return new PageImpl<>(reservas, pageable, total);
    }
}
