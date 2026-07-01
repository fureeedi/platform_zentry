import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { ValidatedBlobField, ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './factura-de-pago.reducer';

export const FacturaDePagoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const facturaDePagoEntity = useAppSelector(state => state.facturaDePago.entity);
  const loading = useAppSelector(state => state.facturaDePago.loading);
  const updating = useAppSelector(state => state.facturaDePago.updating);
  const updateSuccess = useAppSelector(state => state.facturaDePago.updateSuccess);

  const handleClose = () => {
    navigate(`/factura-de-pago${location.search}`);
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
      ...facturaDePagoEntity,
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
          ...facturaDePagoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="zentryApp.facturaDePago.home.createOrEditLabel" data-cy="FacturaDePagoCreateUpdateHeading">
            Crear o editar Factura De Pago
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="factura-de-pago-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Fecha Envio"
                id="factura-de-pago-fechaEnvio"
                name="fechaEnvio"
                data-cy="fechaEnvio"
                type="date"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedBlobField
                label="Imagen Factura"
                id="factura-de-pago-imagenFactura"
                name="imagenFactura"
                data-cy="imagenFactura"
                isImage
                accept="image/*"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/factura-de-pago" replace variant="info">
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

export default FacturaDePagoUpdate;
