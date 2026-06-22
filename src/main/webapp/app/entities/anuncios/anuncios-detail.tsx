import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { TextFormat, byteSize, openFile } from 'react-jhipster';
import { Link, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './anuncios.reducer';

export const AnunciosDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const anunciosEntity = useAppSelector(state => state.anuncios.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="anunciosDetailsHeading">Anuncios</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{anunciosEntity.id}</dd>
          <dt>
            <span id="titulo">Titulo</span>
          </dt>
          <dd>{anunciosEntity.titulo}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{anunciosEntity.descripcion}</dd>
          <dt>
            <span id="fecha">Fecha</span>
          </dt>
          <dd>{anunciosEntity.fecha ? <TextFormat value={anunciosEntity.fecha} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="imagen">Imagen</span>
          </dt>
          <dd>
            {anunciosEntity.imagen ? (
              <div>
                {anunciosEntity.imagenContentType ? (
                  <a onClick={openFile(anunciosEntity.imagenContentType, anunciosEntity.imagen)}>
                    <img src={`data:${anunciosEntity.imagenContentType};base64,${anunciosEntity.imagen}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {anunciosEntity.imagenContentType}, {byteSize(anunciosEntity.imagen)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>Conjunto Residencial</dt>
          <dd>{anunciosEntity.conjuntoResidencial ? anunciosEntity.conjuntoResidencial.nombreConjunto : ''}</dd>
          <dt>Administrador Conjunto</dt>
          <dd>{anunciosEntity.administradorConjunto ? anunciosEntity.administradorConjunto.numeroDocumento : ''}</dd>
        </dl>
        <Button as={Link as any} to="/anuncios" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/anuncios/${anunciosEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnunciosDetail;
