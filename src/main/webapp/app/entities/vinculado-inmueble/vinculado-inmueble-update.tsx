import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getInmuebles } from 'app/entities/inmueble/inmueble.reducer';
import { getEntities as getVinculados } from 'app/entities/vinculado/vinculado.reducer';
import { TipoVinculo } from 'app/shared/model/enumerations/tipo-vinculo.model';

import { createEntity, getEntity, reset, updateEntity } from './vinculado-inmueble.reducer';

export const VinculadoInmuebleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vinculados = useAppSelector(state => state.vinculado.entities);
  const inmuebles = useAppSelector(state => state.inmueble.entities);
  const vinculadoInmuebleEntity = useAppSelector(state => state.vinculadoInmueble.entity);
  const loading = useAppSelector(state => state.vinculadoInmueble.loading);
  const updating = useAppSelector(state => state.vinculadoInmueble.updating);
  const updateSuccess = useAppSelector(state => state.vinculadoInmueble.updateSuccess);
  const tipoVinculoValues = Object.keys(TipoVinculo);

  const handleClose = () => {
    navigate(`/vinculado-inmueble${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVinculados({}));
    dispatch(getInmuebles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...vinculadoInmuebleEntity,
      ...values,
      vinculado: vinculados.find(it => it.id.toString() === values.vinculado?.toString()),
      inmueble: inmuebles.find(it => it.id.toString() === values.inmueble?.toString()),
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
          tipoVinculo: 'PROPIETARIO',
          ...vinculadoInmuebleEntity,
          vinculado: vinculadoInmuebleEntity?.vinculado?.id,
          inmueble: vinculadoInmuebleEntity?.inmueble?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="zentryApp.vinculadoInmueble.home.createOrEditLabel" data-cy="VinculadoInmuebleCreateUpdateHeading">
            Crear o editar Vinculado Inmueble
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="vinculado-inmueble-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Tipo Vinculo"
                id="vinculado-inmueble-tipoVinculo"
                name="tipoVinculo"
                data-cy="tipoVinculo"
                type="select"
              >
                {tipoVinculoValues.map(tipoVinculo => (
                  <option value={tipoVinculo} key={tipoVinculo}>
                    {tipoVinculo}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="vinculado-inmueble-vinculado"
                name="vinculado"
                data-cy="vinculado"
                label="Vinculado"
                type="select"
                required
              >
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
              <ValidatedField id="vinculado-inmueble-inmueble" name="inmueble" data-cy="inmueble" label="Inmueble" type="select" required>
                <option value="" key="0" />
                {inmuebles
                  ? inmuebles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.numeroInmueble}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/vinculado-inmueble" replace variant="info">
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

export default VinculadoInmuebleUpdate;
