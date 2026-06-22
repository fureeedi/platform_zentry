import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './servicio-conjunto.reducer';

export const ServicioConjuntoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const servicioConjuntoEntity = useAppSelector(state => state.servicioConjunto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="servicioConjuntoDetailsHeading">Servicio Conjunto</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{servicioConjuntoEntity.id}</dd>
          <dt>
            <span id="disponible">Disponible</span>
          </dt>
          <dd>{servicioConjuntoEntity.disponible}</dd>
          <dt>
            <span id="aforoMaximo">Aforo Maximo</span>
          </dt>
          <dd>{servicioConjuntoEntity.aforoMaximo}</dd>
          <dt>Conjunto Residencial</dt>
          <dd>{servicioConjuntoEntity.conjuntoResidencial ? servicioConjuntoEntity.conjuntoResidencial.nombreConjunto : ''}</dd>
          <dt>Servicio</dt>
          <dd>{servicioConjuntoEntity.servicio ? servicioConjuntoEntity.servicio.nombreZonaComun : ''}</dd>
        </dl>
        <Button as={Link as any} to="/servicio-conjunto" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/servicio-conjunto/${servicioConjuntoEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServicioConjuntoDetail;
