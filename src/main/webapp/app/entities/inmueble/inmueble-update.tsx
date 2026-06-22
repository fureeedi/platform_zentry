import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getConjuntoResidencials } from 'app/entities/conjunto-residencial/conjunto-residencial.reducer';
import { TipoInmueble } from 'app/shared/model/enumerations/tipo-inmueble.model';

import { createEntity, getEntity, reset, updateEntity } from './inmueble.reducer';

export const InmuebleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const conjuntoResidencials = useAppSelector(state => state.conjuntoResidencial.entities);
  const inmuebleEntity = useAppSelector(state => state.inmueble.entity);
  const loading = useAppSelector(state => state.inmueble.loading);
  const updating = useAppSelector(state => state.inmueble.updating);
  const updateSuccess = useAppSelector(state => state.inmueble.updateSuccess);
  const tipoInmuebleValues = Object.keys(TipoInmueble);

  const handleClose = () => {
    navigate(`/inmueble${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getConjuntoResidencials({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.piso !== undefined && typeof values.piso !== 'number') {
      values.piso = Number(values.piso);
    }

    const entity = {
      ...inmuebleEntity,
      ...values,
      conjuntoResidencial: conjuntoResidencials.find(it => it.id.toString() === values.conjuntoResidencial?.toString()),
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
          tipoInmueble: 'APARTAMENTO',
          ...inmuebleEntity,
          conjuntoResidencial: inmuebleEntity?.conjuntoResidencial?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="zentryApp.inmueble.home.createOrEditLabel" data-cy="InmuebleCreateUpdateHeading">
            Crear o editar Inmueble
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="inmueble-id" label="ID" validate={{ required: true }} />}
              <ValidatedField label="Tipo Inmueble" id="inmueble-tipoInmueble" name="tipoInmueble" data-cy="tipoInmueble" type="select">
                {tipoInmuebleValues.map(tipoInmueble => (
                  <option value={tipoInmueble} key={tipoInmueble}>
                    {tipoInmueble}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Torre"
                id="inmueble-torre"
                name="torre"
                data-cy="torre"
                type="text"
                validate={{
                  maxLength: { value: 10, message: 'Este campo no puede superar más de 10 caracteres.' },
                }}
              />
              <ValidatedField
                label="Numero Inmueble"
                id="inmueble-numeroInmueble"
                name="numeroInmueble"
                data-cy="numeroInmueble"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 10, message: 'Este campo no puede superar más de 10 caracteres.' },
                }}
              />
              <ValidatedField label="Piso" id="inmueble-piso" name="piso" data-cy="piso" type="text" />
              <ValidatedField
                id="inmueble-conjuntoResidencial"
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
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/inmueble" replace variant="info">
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

export default InmuebleUpdate;
