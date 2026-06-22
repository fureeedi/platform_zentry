import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getAdministradorConjuntos } from 'app/entities/administrador-conjunto/administrador-conjunto.reducer';
import { getEntities as getTipoDocumentos } from 'app/entities/tipo-documento/tipo-documento.reducer';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';

import { createEntity, getEntity, reset, updateEntity } from './vinculado.reducer';

export const VinculadoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const tipoDocumentos = useAppSelector(state => state.tipoDocumento.entities);
  const administradorConjuntos = useAppSelector(state => state.administradorConjunto.entities);
  const vinculadoEntity = useAppSelector(state => state.vinculado.entity);
  const loading = useAppSelector(state => state.vinculado.loading);
  const updating = useAppSelector(state => state.vinculado.updating);
  const updateSuccess = useAppSelector(state => state.vinculado.updateSuccess);

  const handleClose = () => {
    navigate(`/vinculado${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getTipoDocumentos({}));
    dispatch(getAdministradorConjuntos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...vinculadoEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user?.toString()),
      tipoDocumento: tipoDocumentos.find(it => it.id.toString() === values.tipoDocumento?.toString()),
      administradorConjunto: administradorConjuntos.find(it => it.id.toString() === values.administradorConjunto?.toString()),
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
          ...vinculadoEntity,
          user: vinculadoEntity?.user?.id,
          tipoDocumento: vinculadoEntity?.tipoDocumento?.id,
          administradorConjunto: vinculadoEntity?.administradorConjunto?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="zentryApp.vinculado.home.createOrEditLabel" data-cy="VinculadoCreateUpdateHeading">
            Crear o editar Vinculado
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="vinculado-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Nombres"
                id="vinculado-nombres"
                name="nombres"
                data-cy="nombres"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 50, message: 'Este campo no puede superar más de 50 caracteres.' },
                }}
              />
              <ValidatedField
                label="Apellidos"
                id="vinculado-apellidos"
                name="apellidos"
                data-cy="apellidos"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 50, message: 'Este campo no puede superar más de 50 caracteres.' },
                }}
              />
              <ValidatedField
                label="Numero Documento"
                id="vinculado-numeroDocumento"
                name="numeroDocumento"
                data-cy="numeroDocumento"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 20, message: 'Este campo no puede superar más de 20 caracteres.' },
                }}
              />
              <ValidatedField
                label="Telefono"
                id="vinculado-telefono"
                name="telefono"
                data-cy="telefono"
                type="text"
                validate={{
                  maxLength: { value: 15, message: 'Este campo no puede superar más de 15 caracteres.' },
                }}
              />
              <ValidatedField
                label="Correo"
                id="vinculado-correo"
                name="correo"
                data-cy="correo"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 100, message: 'Este campo no puede superar más de 100 caracteres.' },
                }}
              />
              <ValidatedField label="Activo" id="vinculado-activo" name="activo" data-cy="activo" check type="checkbox" />
              <ValidatedField id="vinculado-user" name="user" data-cy="user" label="User" type="select" required>
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField
                id="vinculado-tipoDocumento"
                name="tipoDocumento"
                data-cy="tipoDocumento"
                label="Tipo Documento"
                type="select"
                required
              >
                <option value="" key="0" />
                {tipoDocumentos
                  ? tipoDocumentos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombreTipoDocumento}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField
                id="vinculado-administradorConjunto"
                name="administradorConjunto"
                data-cy="administradorConjunto"
                label="Administrador Conjunto"
                type="select"
                required
              >
                <option value="" key="0" />
                {administradorConjuntos
                  ? administradorConjuntos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.numeroDocumento}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/vinculado" replace variant="info">
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

export default VinculadoUpdate;
