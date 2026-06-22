import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { TextFormat } from 'react-jhipster';
import { Link, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reservas.reducer';

export const ReservasDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reservasEntity = useAppSelector(state => state.reservas.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reservasDetailsHeading">Reservas</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{reservasEntity.id}</dd>
          <dt>
            <span id="fechaSolicitud">Fecha Solicitud</span>
          </dt>
          <dd>
            {reservasEntity.fechaSolicitud ? (
              <TextFormat value={reservasEntity.fechaSolicitud} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaReserva">Fecha Reserva</span>
          </dt>
          <dd>
            {reservasEntity.fechaReserva ? (
              <TextFormat value={reservasEntity.fechaReserva} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="horaInicio">Hora Inicio</span>
          </dt>
          <dd>{reservasEntity.horaInicio}</dd>
          <dt>
            <span id="horafin">Horafin</span>
          </dt>
          <dd>{reservasEntity.horafin}</dd>
          <dt>
            <span id="cuposApartados">Cupos Apartados</span>
          </dt>
          <dd>{reservasEntity.cuposApartados}</dd>
          <dt>
            <span id="estado">Estado</span>
          </dt>
          <dd>{reservasEntity.estado}</dd>
          <dt>Servicio Conjunto</dt>
          <dd>{reservasEntity.servicioConjunto ? reservasEntity.servicioConjunto.id : ''}</dd>
          <dt>Vinculado</dt>
          <dd>{reservasEntity.vinculado ? reservasEntity.vinculado.numeroDocumento : ''}</dd>
        </dl>
        <Button as={Link as any} to="/reservas" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/reservas/${reservasEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReservasDetail;
