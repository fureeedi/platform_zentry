import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vinculado-inmueble.reducer';

export const VinculadoInmuebleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vinculadoInmuebleEntity = useAppSelector(state => state.vinculadoInmueble.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vinculadoInmuebleDetailsHeading">Vinculado Inmueble</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vinculadoInmuebleEntity.id}</dd>
          <dt>
            <span id="tipoVinculo">Tipo Vinculo</span>
          </dt>
          <dd>{vinculadoInmuebleEntity.tipoVinculo}</dd>
          <dt>Vinculado</dt>
          <dd>{vinculadoInmuebleEntity.vinculado ? vinculadoInmuebleEntity.vinculado.numeroDocumento : ''}</dd>
          <dt>Inmueble</dt>
          <dd>{vinculadoInmuebleEntity.inmueble ? vinculadoInmuebleEntity.inmueble.numeroInmueble : ''}</dd>
        </dl>
        <Button as={Link as any} to="/vinculado-inmueble" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/vinculado-inmueble/${vinculadoInmuebleEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VinculadoInmuebleDetail;
