import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './administrador-conjunto.reducer';

export const AdministradorConjuntoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const administradorConjuntoEntity = useAppSelector(state => state.administradorConjunto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="administradorConjuntoDetailsHeading">Administrador Conjunto</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{administradorConjuntoEntity.id}</dd>
          <dt>
            <span id="nombres">Nombres</span>
          </dt>
          <dd>{administradorConjuntoEntity.nombres}</dd>
          <dt>
            <span id="apellidos">Apellidos</span>
          </dt>
          <dd>{administradorConjuntoEntity.apellidos}</dd>
          <dt>
            <span id="numeroDocumento">Numero Documento</span>
          </dt>
          <dd>{administradorConjuntoEntity.numeroDocumento}</dd>
          <dt>
            <span id="telefono">Telefono</span>
          </dt>
          <dd>{administradorConjuntoEntity.telefono}</dd>
          <dt>
            <span id="correo">Correo</span>
          </dt>
          <dd>{administradorConjuntoEntity.correo}</dd>
          <dt>
            <span id="activo">Activo</span>
          </dt>
          <dd>{administradorConjuntoEntity.activo ? 'true' : 'false'}</dd>
          <dt>User</dt>
          <dd>{administradorConjuntoEntity.user ? administradorConjuntoEntity.user.login : ''}</dd>
          <dt>Conjunto Residencial</dt>
          <dd>{administradorConjuntoEntity.conjuntoResidencial ? administradorConjuntoEntity.conjuntoResidencial.nombreConjunto : ''}</dd>
          <dt>Tipo Documento</dt>
          <dd>{administradorConjuntoEntity.tipoDocumento ? administradorConjuntoEntity.tipoDocumento.nombreTipoDocumento : ''}</dd>
        </dl>
        <Button as={Link as any} to="/administrador-conjunto" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/administrador-conjunto/${administradorConjuntoEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdministradorConjuntoDetail;
