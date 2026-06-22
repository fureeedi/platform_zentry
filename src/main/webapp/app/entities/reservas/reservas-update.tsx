import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm, isNumber } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getServicioConjuntos } from 'app/entities/servicio-conjunto/servicio-conjunto.reducer';
import { getEntities as getVinculados } from 'app/entities/vinculado/vinculado.reducer';
import { Estado } from 'app/shared/model/enumerations/estado.model';

import { createEntity, getEntity, reset, updateEntity } from './reservas.reducer';

export const ReservasUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const servicioConjuntos = useAppSelector(state => state.servicioConjunto.entities);
  const vinculados = useAppSelector(state => state.vinculado.entities);
  const reservasEntity = useAppSelector(state => state.reservas.entity);
  const loading = useAppSelector(state => state.reservas.loading);
  const updating = useAppSelector(state => state.reservas.updating);
  const updateSuccess = useAppSelector(state => state.reservas.updateSuccess);
  const estadoValues = Object.keys(Estado);

  const handleClose = () => {
    navigate(`/reservas${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getServicioConjuntos({}));
    dispatch(getVinculados({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.cuposApartados !== undefined && typeof values.cuposApartados !== 'number') {
      values.cuposApartados = Number(values.cuposApartados);
    }

    const entity = {
      ...reservasEntity,
      ...values,
      servicioConjunto: servicioConjuntos.find(it => it.id.toString() === values.servicioConjunto?.toString()),
      vinculado: vinculados.find(it => it.id.toString() === values.vinculado?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          estado: 'APROBADO',
          ...reservasEntity,
          servicioConjunto: reservasEntity?.servicioConjunto?.id,
          vinculado: reservasEntity?.vinculado?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="zentryApp.reservas.home.createOrEditLabel" data-cy="ReservasCreateUpdateHeading">
            Crear o editar Reservas
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="reservas-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Fecha Solicitud"
                id="reservas-fechaSolicitud"
                name="fechaSolicitud"
                data-cy="fechaSolicitud"
                type="date"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Fecha Reserva"
                id="reservas-fechaReserva"
                name="fechaReserva"
                data-cy="fechaReserva"
                type="date"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Hora Inicio"
                id="reservas-horaInicio"
                name="horaInicio"
                data-cy="horaInicio"
                type="time"
                placeholder="HH:mm"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Horafin"
                id="reservas-horafin"
                name="horafin"
                data-cy="horafin"
                type="time"
                placeholder="HH:mm"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Cupos Apartados"
                id="reservas-cuposApartados"
                name="cuposApartados"
                data-cy="cuposApartados"
                type="text"
                validate={{
                  min: { value: 1, message: 'Este campo debe ser mayor que 1.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField label="Estado" id="reservas-estado" name="estado" data-cy="estado" type="select">
                {estadoValues.map(estado => (
                  <option value={estado} key={estado}>
                    {estado}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="reservas-servicioConjunto"
                name="servicioConjunto"
                data-cy="servicioConjunto"
                label="Servicio Conjunto"
                type="select"
                required
              >
                <option value="" key="0" />
                {servicioConjuntos
                  ? servicioConjuntos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField id="reservas-vinculado" name="vinculado" data-cy="vinculado" label="Vinculado" type="select" required>
                <option value="" key="0" />
                {vinculados
                  ? vinculados.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.numeroDocumento}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/reservas" replace variant="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Volver</span>
              </Button>
              &nbsp;
              <Button variant="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Guardar
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ReservasUpdate;
