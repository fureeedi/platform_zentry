import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vinculado.reducer';

export const VinculadoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vinculadoEntity = useAppSelector(state => state.vinculado.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vinculadoDetailsHeading">Vinculado</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vinculadoEntity.id}</dd>
          <dt>
            <span id="nombres">Nombres</span>
          </dt>
          <dd>{vinculadoEntity.nombres}</dd>
          <dt>
            <span id="apellidos">Apellidos</span>
          </dt>
          <dd>{vinculadoEntity.apellidos}</dd>
          <dt>
            <span id="numeroDocumento">Numero Documento</span>
          </dt>
          <dd>{vinculadoEntity.numeroDocumento}</dd>
          <dt>
            <span id="telefono">Telefono</span>
          </dt>
          <dd>{vinculadoEntity.telefono}</dd>
          <dt>
            <span id="correo">Correo</span>
          </dt>
          <dd>{vinculadoEntity.correo}</dd>
          <dt>
            <span id="activo">Activo</span>
          </dt>
          <dd>{vinculadoEntity.activo ? 'true' : 'false'}</dd>
          <dt>User</dt>
          <dd>{vinculadoEntity.user ? vinculadoEntity.user.login : ''}</dd>
          <dt>Tipo Documento</dt>
          <dd>{vinculadoEntity.tipoDocumento ? vinculadoEntity.tipoDocumento.nombreTipoDocumento : ''}</dd>
          <dt>Administrador Conjunto</dt>
          <dd>{vinculadoEntity.administradorConjunto ? vinculadoEntity.administradorConjunto.numeroDocumento : ''}</dd>
        </dl>
        <Button as={Link as any} to="/vinculado" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/vinculado/${vinculadoEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VinculadoDetail;
