import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './servicio.reducer';

export const ServicioUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const servicioEntity = useAppSelector(state => state.servicio.entity);
  const loading = useAppSelector(state => state.servicio.loading);
  const updating = useAppSelector(state => state.servicio.updating);
  const updateSuccess = useAppSelector(state => state.servicio.updateSuccess);

  const handleClose = () => {
    navigate(`/servicio${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...servicioEntity,
      ...values,
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
          ...servicioEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="zentryApp.servicio.home.createOrEditLabel" data-cy="ServicioCreateUpdateHeading">
            Crear o editar Servicio
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="servicio-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Disponibilidad"
                id="servicio-disponibilidad"
                name="disponibilidad"
                data-cy="disponibilidad"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 200, message: 'Este campo no puede superar más de 200 caracteres.' },
                }}
              />
              <ValidatedField
                label="Nombre Zona Comun"
                id="servicio-nombreZonaComun"
                name="nombreZonaComun"
                data-cy="nombreZonaComun"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 50, message: 'Este campo no puede superar más de 50 caracteres.' },
                }}
              />
              <ValidatedField
                label="Descripcion"
                id="servicio-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
                validate={{
                  maxLength: { value: 200, message: 'Este campo no puede superar más de 200 caracteres.' },
                }}
              />
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/servicio" replace variant="info">
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

export default ServicioUpdate;
