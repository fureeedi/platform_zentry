import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './conjunto-residencial.reducer';

export const ConjuntoResidencialUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const conjuntoResidencialEntity = useAppSelector(state => state.conjuntoResidencial.entity);
  const loading = useAppSelector(state => state.conjuntoResidencial.loading);
  const updating = useAppSelector(state => state.conjuntoResidencial.updating);
  const updateSuccess = useAppSelector(state => state.conjuntoResidencial.updateSuccess);

  const handleClose = () => {
    navigate(`/conjunto-residencial${location.search}`);
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
      ...conjuntoResidencialEntity,
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
          ...conjuntoResidencialEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="zentryApp.conjuntoResidencial.home.createOrEditLabel" data-cy="ConjuntoResidencialCreateUpdateHeading">
            Crear o editar Conjunto Residencial
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && (
                <ValidatedField name="id" required readOnly id="conjunto-residencial-id" label="ID" validate={{ required: true }} />
              )}
              <ValidatedField
                label="Nombre Conjunto"
                id="conjunto-residencial-nombreConjunto"
                name="nombreConjunto"
                data-cy="nombreConjunto"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Direccion Conjunto"
                id="conjunto-residencial-direccionConjunto"
                name="direccionConjunto"
                data-cy="direccionConjunto"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <Button
                as={Link as any}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/conjunto-residencial"
                replace
                variant="info"
              >
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

export default ConjuntoResidencialUpdate;
