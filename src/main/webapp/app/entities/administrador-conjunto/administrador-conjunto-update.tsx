import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getConjuntoResidencials } from 'app/entities/conjunto-residencial/conjunto-residencial.reducer';
import { getEntities as getTipoDocumentos } from 'app/entities/tipo-documento/tipo-documento.reducer';

import { createEntity, getEntity, reset, updateEntity } from './administrador-conjunto.reducer';

export const AdministradorConjuntoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const conjuntoResidencials = useAppSelector(state => state.conjuntoResidencial.entities);
  const tipoDocumentos = useAppSelector(state => state.tipoDocumento.entities);
  const administradorConjuntoEntity = useAppSelector(state => state.administradorConjunto.entity);
  const loading = useAppSelector(state => state.administradorConjunto.loading);
  const updating = useAppSelector(state => state.administradorConjunto.updating);
  const updateSuccess = useAppSelector(state => state.administradorConjunto.updateSuccess);

  const handleClose = () => {
    navigate(`/administrador-conjunto${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getConjuntoResidencials({}));
    dispatch(getTipoDocumentos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...administradorConjuntoEntity,
      ...values,
      conjuntoResidencial: conjuntoResidencials.find(it => it.id.toString() === values.conjuntoResidencial?.toString()),
      tipoDocumento: tipoDocumentos.find(it => it.id.toString() === values.tipoDocumento?.toString()),
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
          ...administradorConjuntoEntity,
          conjuntoResidencial: administradorConjuntoEntity?.conjuntoResidencial?.id,
          tipoDocumento: administradorConjuntoEntity?.tipoDocumento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="zentryApp.administradorConjunto.home.createOrEditLabel" data-cy="AdministradorConjuntoCreateUpdateHeading">
            Crear o editar Administrador Conjunto
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
                <ValidatedField name="id" required readOnly id="administrador-conjunto-id" label="ID" validate={{ required: true }} />
              )}
              <ValidatedField
                label="Nombres"
                id="administrador-conjunto-nombres"
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
                id="administrador-conjunto-apellidos"
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
                id="administrador-conjunto-numeroDocumento"
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
                id="administrador-conjunto-telefono"
                name="telefono"
                data-cy="telefono"
                type="text"
                validate={{
                  maxLength: { value: 15, message: 'Este campo no puede superar más de 15 caracteres.' },
                }}
              />
              <ValidatedField
                label="Correo"
                id="administrador-conjunto-correo"
                name="correo"
                data-cy="correo"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Usuario"
                id="administrador-conjunto-login"
                name="login"
                data-cy="login"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Contraseña"
                id="administrador-conjunto-password"
                name="password"
                data-cy="password"
                type="password"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField label="Activo" id="administrador-conjunto-activo" name="activo" data-cy="activo" check type="checkbox" />
              <ValidatedField
                id="administrador-conjunto-conjuntoResidencial"
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
              <ValidatedField
                id="administrador-conjunto-tipoDocumento"
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
              <Button
                as={Link as any}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/administrador-conjunto"
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

export default AdministradorConjuntoUpdate;
