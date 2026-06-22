import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './conjunto-residencial.reducer';

export const ConjuntoResidencialDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const conjuntoResidencialEntity = useAppSelector(state => state.conjuntoResidencial.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="conjuntoResidencialDetailsHeading">Conjunto Residencial</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{conjuntoResidencialEntity.id}</dd>
          <dt>
            <span id="nombreConjunto">Nombre Conjunto</span>
          </dt>
          <dd>{conjuntoResidencialEntity.nombreConjunto}</dd>
          <dt>
            <span id="direccionConjunto">Direccion Conjunto</span>
          </dt>
          <dd>{conjuntoResidencialEntity.direccionConjunto}</dd>
        </dl>
        <Button as={Link as any} to="/conjunto-residencial" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/conjunto-residencial/${conjuntoResidencialEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConjuntoResidencialDetail;
