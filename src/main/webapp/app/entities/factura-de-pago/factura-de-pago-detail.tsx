import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { TextFormat, byteSize, openFile } from 'react-jhipster';
import { Link, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './factura-de-pago.reducer';

export const FacturaDePagoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const facturaDePagoEntity = useAppSelector(state => state.facturaDePago.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="facturaDePagoDetailsHeading">Factura De Pago</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{facturaDePagoEntity.id}</dd>
          <dt>
            <span id="fechaEnvio">Fecha Envio</span>
          </dt>
          <dd>
            {facturaDePagoEntity.fechaEnvio ? (
              <TextFormat value={facturaDePagoEntity.fechaEnvio} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="imagenFactura">Imagen Factura</span>
          </dt>
          <dd>
            {facturaDePagoEntity.imagenFactura ? (
              <div>
                {facturaDePagoEntity.imagenFacturaContentType ? (
                  <a onClick={openFile(facturaDePagoEntity.imagenFacturaContentType, facturaDePagoEntity.imagenFactura)}>
                    <img
                      src={`data:${facturaDePagoEntity.imagenFacturaContentType};base64,${facturaDePagoEntity.imagenFactura}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {facturaDePagoEntity.imagenFacturaContentType}, {byteSize(facturaDePagoEntity.imagenFactura)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>Conjunto Residencial</dt>
          <dd>{facturaDePagoEntity.conjuntoResidencial ? facturaDePagoEntity.conjuntoResidencial.nombreConjunto : ''}</dd>
          <dt>Vinculado</dt>
          <dd>{facturaDePagoEntity.vinculado ? facturaDePagoEntity.vinculado.numeroDocumento : ''}</dd>
        </dl>
        <Button as={Link as any} to="/factura-de-pago" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/factura-de-pago/${facturaDePagoEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FacturaDePagoDetail;
