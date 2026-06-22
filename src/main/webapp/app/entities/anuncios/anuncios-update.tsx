import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedBlobField, ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getAdministradorConjuntos } from 'app/entities/administrador-conjunto/administrador-conjunto.reducer';
import { getEntities as getConjuntoResidencials } from 'app/entities/conjunto-residencial/conjunto-residencial.reducer';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';

import { createEntity, getEntity, reset, updateEntity } from './anuncios.reducer';

export const AnunciosUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const conjuntoResidencials = useAppSelector(state => state.conjuntoResidencial.entities);
  const administradorConjuntos = useAppSelector(state => state.administradorConjunto.entities);
  const anunciosEntity = useAppSelector(state => state.anuncios.entity);
  const loading = useAppSelector(state => state.anuncios.loading);
  const updating = useAppSelector(state => state.anuncios.updating);
  const updateSuccess = useAppSelector(state => state.anuncios.updateSuccess);

  const handleClose = () => {
    navigate(`/anuncios${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getConjuntoResidencials({}));
    dispatch(getAdministradorConjuntos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fecha = convertDateTimeToServer(values.fecha);

    const entity = {
      ...anunciosEntity,
      ...values,
      conjuntoResidencial: conjuntoResidencials.find(it => it.id.toString() === values.conjuntoResidencial?.toString()),
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
      ? {
          fecha: displayDefaultDateTime(),
        }
      : {
          ...anunciosEntity,
          fecha: convertDateTimeFromServer(anunciosEntity.fecha),
          conjuntoResidencial: anunciosEntity?.conjuntoResidencial?.id,
          administradorConjunto: anunciosEntity?.administradorConjunto?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="zentryApp.anuncios.home.createOrEditLabel" data-cy="AnunciosCreateUpdateHeading">
            Crear o editar Anuncios
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="anuncios-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Titulo"
                id="anuncios-titulo"
                name="titulo"
                data-cy="titulo"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 50, message: 'Este campo no puede superar más de 50 caracteres.' },
                }}
              />
              <ValidatedField
                label="Descripcion"
                id="anuncios-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 200, message: 'Este campo no puede superar más de 200 caracteres.' },
                }}
              />
              <ValidatedField
                label="Fecha"
                id="anuncios-fecha"
                name="fecha"
                data-cy="fecha"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedBlobField label="Imagen" id="anuncios-imagen" name="imagen" data-cy="imagen" isImage accept="image/*" />
              <ValidatedField
                id="anuncios-conjuntoResidencial"
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
                id="anuncios-administradorConjunto"
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
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/anuncios" replace variant="info">
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

export default AnunciosUpdate;
