import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './servicio.reducer';

export const ServicioDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const servicioEntity = useAppSelector(state => state.servicio.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="servicioDetailsHeading">Servicio</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{servicioEntity.id}</dd>
          <dt>
            <span id="disponibilidad">Disponibilidad</span>
          </dt>
          <dd>{servicioEntity.disponibilidad}</dd>
          <dt>
            <span id="nombreZonaComun">Nombre Zona Comun</span>
          </dt>
          <dd>{servicioEntity.nombreZonaComun}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{servicioEntity.descripcion}</dd>
        </dl>
        <Button as={Link as any} to="/servicio" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/servicio/${servicioEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServicioDetail;
