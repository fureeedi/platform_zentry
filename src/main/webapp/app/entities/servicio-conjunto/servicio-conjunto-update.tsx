import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm, isNumber } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getConjuntoResidencials } from 'app/entities/conjunto-residencial/conjunto-residencial.reducer';
import { getEntities as getServicios } from 'app/entities/servicio/servicio.reducer';
import { TipoDisponibilidad } from 'app/shared/model/enumerations/tipo-disponibilidad.model';

import { createEntity, getEntity, reset, updateEntity } from './servicio-conjunto.reducer';

export const ServicioConjuntoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const conjuntoResidencials = useAppSelector(state => state.conjuntoResidencial.entities);
  const servicios = useAppSelector(state => state.servicio.entities);
  const servicioConjuntoEntity = useAppSelector(state => state.servicioConjunto.entity);
  const loading = useAppSelector(state => state.servicioConjunto.loading);
  const updating = useAppSelector(state => state.servicioConjunto.updating);
  const updateSuccess = useAppSelector(state => state.servicioConjunto.updateSuccess);
  const tipoDisponibilidadValues = Object.keys(TipoDisponibilidad);

  const handleClose = () => {
    navigate(`/servicio-conjunto${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getConjuntoResidencials({}));
    dispatch(getServicios({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.aforoMaximo !== undefined && typeof values.aforoMaximo !== 'number') {
      values.aforoMaximo = Number(values.aforoMaximo);
    }

    const entity = {
      ...servicioConjuntoEntity,
      ...values,
      conjuntoResidencial: conjuntoResidencials.find(it => it.id.toString() === values.conjuntoResidencial?.toString()),
      servicio: servicios.find(it => it.id.toString() === values.servicio?.toString()),
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
          disponible: 'ABIERTO',
          ...servicioConjuntoEntity,
          conjuntoResidencial: servicioConjuntoEntity?.conjuntoResidencial?.id,
          servicio: servicioConjuntoEntity?.servicio?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="zentryApp.servicioConjunto.home.createOrEditLabel" data-cy="ServicioConjuntoCreateUpdateHeading">
            Crear o editar Servicio Conjunto
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="servicio-conjunto-id" label="ID" validate={{ required: true }} />}
              <ValidatedField label="Disponible" id="servicio-conjunto-disponible" name="disponible" data-cy="disponible" type="select">
                {tipoDisponibilidadValues.map(tipoDisponibilidad => (
                  <option value={tipoDisponibilidad} key={tipoDisponibilidad}>
                    {tipoDisponibilidad}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Aforo Maximo"
                id="servicio-conjunto-aforoMaximo"
                name="aforoMaximo"
                data-cy="aforoMaximo"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  min: { value: 1, message: 'Este campo debe ser mayor que 1.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField
                id="servicio-conjunto-conjuntoResidencial"
                name="conjuntoResidencial"
                data-cy="conjuntoResidencial"
                label="Conjunto Residencial"
                type="select"
                required
              >
                <option value="" key="0" />
                {conjuntoResidencials
                  ? conjuntoResidencials.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombreConjunto}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField id="servicio-conjunto-servicio" name="servicio" data-cy="servicio" label="Servicio" type="select" required>
                <option value="" key="0" />
                {servicios
                  ? servicios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombreZonaComun}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/servicio-conjunto" replace variant="info">
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

export default ServicioConjuntoUpdate;
