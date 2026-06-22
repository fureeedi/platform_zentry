import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inmueble.reducer';

export const InmuebleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inmuebleEntity = useAppSelector(state => state.inmueble.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inmuebleDetailsHeading">Inmueble</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{inmuebleEntity.id}</dd>
          <dt>
            <span id="tipoInmueble">Tipo Inmueble</span>
          </dt>
          <dd>{inmuebleEntity.tipoInmueble}</dd>
          <dt>
            <span id="torre">Torre</span>
          </dt>
          <dd>{inmuebleEntity.torre}</dd>
          <dt>
            <span id="numeroInmueble">Numero Inmueble</span>
          </dt>
          <dd>{inmuebleEntity.numeroInmueble}</dd>
          <dt>
            <span id="piso">Piso</span>
          </dt>
          <dd>{inmuebleEntity.piso}</dd>
          <dt>Conjunto Residencial</dt>
          <dd>{inmuebleEntity.conjuntoResidencial ? inmuebleEntity.conjuntoResidencial.nombreConjunto : ''}</dd>
        </dl>
        <Button as={Link as any} to="/inmueble" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/inmueble/${inmuebleEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InmuebleDetail;
