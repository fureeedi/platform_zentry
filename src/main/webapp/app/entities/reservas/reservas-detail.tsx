import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { TextFormat } from 'react-jhipster';
import { Link, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reservas.reducer';
import { cambiarEstado } from './reservas.reducer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { Authority } from 'app/shared/jhipster/constants';

export const ReservasDetail = () => {
  const dispatch = useAppDispatch();
  const updateSuccess = useAppSelector(state => state.reservas.updateSuccess);

  const { id } = useParams<'id'>();

  useEffect(() => {
    if (id) {
      dispatch(getEntity(id));
    }
  }, [dispatch, id, updateSuccess]);

  const reservasEntity = useAppSelector(state => state.reservas.entity);
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [Authority.ADMIN]));
  const isAdministradorConjunto = useAppSelector(state =>
    hasAnyAuthority(state.authentication.account.authorities, [Authority.ADMINISTRADOR_CONJUNTO]),
  );
  return (
    <div className="entity-detail-card">
      <div className="entity-detail-header">
        <div>
          <h2>Reserva</h2>
          {isAdmin && <span className="entity-id">ID #{reservasEntity.id}</span>}
        </div>

        <div>
          {isAdmin && (
            <>
              &nbsp;
              <Button as={Link as any} to={`/reservas/${reservasEntity.id}/edit`} replace variant="primary">
                <FontAwesomeIcon icon="pencil-alt" /> Editar
              </Button>
            </>
          )}
        </div>
      </div>

      <div className="entity-detail-body">
        <div className="entity-detail-section">
          <h4>Información de la reserva</h4>

          <Row>
            <Col md={6} lg={4}>
              <div className="info-card">
                <span className="info-title">Fecha Solicitud</span>
                <span className="info-value">
                  {reservasEntity.fechaSolicitud ? (
                    <TextFormat value={reservasEntity.fechaSolicitud} type="date" format={APP_LOCAL_DATE_FORMAT} />
                  ) : null}
                </span>
              </div>
            </Col>

            <Col md={6} lg={4}>
              <div className="info-card">
                <span className="info-title">Fecha Reserva</span>
                <span className="info-value">
                  {reservasEntity.fechaReserva ? (
                    <TextFormat value={reservasEntity.fechaReserva} type="date" format={APP_LOCAL_DATE_FORMAT} />
                  ) : null}
                </span>
              </div>
            </Col>

            <Col md={6} lg={4}>
              <div className="info-card">
                <span className="info-title">Hora Inicio</span>
                <span className="info-value">{reservasEntity.horaInicio}</span>
              </div>
            </Col>

            <Col md={6} lg={4}>
              <div className="info-card">
                <span className="info-title">Hora Finalización</span>
                <span className="info-value">{reservasEntity.horafin}</span>
              </div>
            </Col>

            <Col md={6} lg={4}>
              <div className="info-card">
                <span className="info-title">Cupos Apartados</span>
                <span className="info-value">{reservasEntity.cuposApartados}</span>
              </div>
            </Col>

            <Col md={6} lg={4}>
              <div className="info-card">
                <span className="info-title">Estado</span>

                <div className="mt-2">
                  <span className={`status-badge ${reservasEntity.estado?.toUpperCase()}`}>{reservasEntity.estado}</span>
                </div>
              </div>
            </Col>
          </Row>
        </div>

        <div className="entity-detail-section">
          <h4>Información relacionada</h4>

          <Row>
            <Col md={6}>
              <div className="info-card">
                <span className="info-title">Servicio Conjunto</span>
                <span className="info-value">{reservasEntity.servicioConjunto?.servicio?.nombreZonaComun}</span>
              </div>
            </Col>

            {isAdministradorConjunto && (
              <Col md={6}>
                <div className="info-card">
                  <span className="info-title">Vinculado</span>
                  <span className="info-value">
                    {reservasEntity.vinculado
                      ? `${reservasEntity.vinculado.nombres} ${reservasEntity.vinculado.apellidos} - ${reservasEntity.vinculado.numeroDocumento}`
                      : ''}
                  </span>
                </div>
              </Col>
            )}

            <div className="entity-detail-actions">
              <Button className="me-4" as={Link as any} to="/reservas" replace variant="info">
                <FontAwesomeIcon icon="arrow-left" /> Volver
              </Button>

              {isAdministradorConjunto && reservasEntity.estado === 'PENDIENTE' && (
                <>
                  <Button
                    variant="success"
                    className="me-4"
                    onClick={() =>
                      dispatch(
                        cambiarEstado({
                          id: reservasEntity.id!,
                          estado: 'APROBADO',
                        }),
                      )
                    }
                  >
                    <FontAwesomeIcon icon="check" /> Aprobar
                  </Button>

                  <Button
                    variant="danger"
                    className="me-4"
                    onClick={() =>
                      dispatch(
                        cambiarEstado({
                          id: reservasEntity.id!,
                          estado: 'RECHAZADO',
                        }),
                      )
                    }
                  >
                    <FontAwesomeIcon icon="times-circle" /> Rechazar
                  </Button>
                </>
              )}
            </div>
          </Row>
        </div>
      </div>
    </div>
  );
};

export default ReservasDetail;
