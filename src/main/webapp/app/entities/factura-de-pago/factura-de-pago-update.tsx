import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedBlobField, ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getConjuntoResidencials } from 'app/entities/conjunto-residencial/conjunto-residencial.reducer';
import { getEntities as getVinculados } from 'app/entities/vinculado/vinculado.reducer';

import { createEntity, getEntity, reset, updateEntity } from './factura-de-pago.reducer';

export const FacturaDePagoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const conjuntoResidencials = useAppSelector(state => state.conjuntoResidencial.entities);
  const vinculados = useAppSelector(state => state.vinculado.entities);
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

    dispatch(getConjuntoResidencials({}));
    dispatch(getVinculados({}));
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
      conjuntoResidencial: conjuntoResidencials.find(it => it.id.toString() === values.conjuntoResidencial?.toString()),
      vinculado: vinculados.find(it => it.id.toString() === values.vinculado?.toString()),
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
          conjuntoResidencial: facturaDePagoEntity?.conjuntoResidencial?.id,
          vinculado: facturaDePagoEntity?.vinculado?.id,
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
              <ValidatedField
                id="factura-de-pago-conjuntoResidencial"
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
              <ValidatedField id="factura-de-pago-vinculado" name="vinculado" data-cy="vinculado" label="Vinculado" type="select" required>
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
