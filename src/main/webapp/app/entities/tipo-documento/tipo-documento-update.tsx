import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './tipo-documento.reducer';

export const TipoDocumentoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tipoDocumentoEntity = useAppSelector(state => state.tipoDocumento.entity);
  const loading = useAppSelector(state => state.tipoDocumento.loading);
  const updating = useAppSelector(state => state.tipoDocumento.updating);
  const updateSuccess = useAppSelector(state => state.tipoDocumento.updateSuccess);

  const handleClose = () => {
    navigate(`/tipo-documento${location.search}`);
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
      ...tipoDocumentoEntity,
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
          ...tipoDocumentoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="zentryApp.tipoDocumento.home.createOrEditLabel" data-cy="TipoDocumentoCreateUpdateHeading">
            Crear o editar Tipo Documento
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="tipo-documento-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Nombre Tipo Documento"
                id="tipo-documento-nombreTipoDocumento"
                name="nombreTipoDocumento"
                data-cy="nombreTipoDocumento"
                type="text"
                validate={{
                  maxLength: { value: 50, message: 'Este campo no puede superar más de 50 caracteres.' },
                }}
              />
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/tipo-documento" replace variant="info">
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

export default TipoDocumentoUpdate;
