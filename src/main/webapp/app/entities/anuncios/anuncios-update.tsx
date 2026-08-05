import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { ValidatedBlobField, ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
// import { getEntities as getAdministradorConjuntos } from 'app/entities/administrador-conjunto/administrador-conjunto.reducer';
// import { getEntities as getConjuntoResidencials } from 'app/entities/conjunto-residencial/conjunto-residencial.reducer';
// import { convertDateTimeFromServer } from 'app/shared/util/date-utils';

import { createEntity, getEntity, reset, updateEntity } from './anuncios.reducer';

export const AnunciosUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  // const conjuntoResidencials = useAppSelector(state => state.conjuntoResidencial.entities);
  // const administradorConjuntos = useAppSelector(state => state.administradorConjunto.entities);
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

    // dispatch(getConjuntoResidencials({}));
    // dispatch(getAdministradorConjuntos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    // values.fecha = convertDateTimeToServer(values.fecha);

    const entity = {
      ...anunciosEntity,
      ...values,
      // conjuntoResidencial: conjuntoResidencials.find(it => it.id.toString() === values.conjuntoResidencial?.toString()),
      // administradorConjunto: administradorConjuntos.find(it => it.id.toString() === values.administradorConjunto?.toString()),
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
          // fecha: displayDefaultDateTime(),
        }
      : {
          ...anunciosEntity,
          // fecha: convertDateTimeFromServer(anunciosEntity.fecha),
          // conjuntoResidencial: anunciosEntity?.conjuntoResidencial?.id,
          // administradorConjunto: anunciosEntity?.administradorConjunto?.id,
        };

  return (
    <div>
      <Row>
        <Col>
          <div className="entity-detail-card">
            <div className="entity-detail-header">
              <h2 id="zentryApp.anuncios.home.createOrEditLabel" data-cy="AnunciosCreateUpdateHeading">
                {isNew ? 'Crear Anuncio' : 'Editar Anuncio'}
              </h2>
            </div>

            {loading ? (
              <p>Loading...</p>
            ) : (
              <div className="entity-detail-body">
                <div className="">
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
                    <ValidatedBlobField label="Imagen" id="anuncios-imagen" name="imagen" data-cy="imagen" isImage accept="image/*" />
                    <div className="entity-detail-actions">
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
                    </div>
                  </ValidatedForm>
                </div>
              </div>
            )}
          </div>
        </Col>
      </Row>
    </div>
  );
};

export default AnunciosUpdate;
