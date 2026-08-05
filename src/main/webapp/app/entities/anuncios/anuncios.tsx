import React, { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import { JhiPagination, TextFormat, getPaginationState } from 'react-jhipster';
import { Link, useLocation, useNavigate } from 'react-router';

// import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Authority } from 'app/shared/jhipster/constants';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
// import { IAnuncios } from "app/shared/model/anuncios.model";

import { getEntities } from './anuncios.reducer';
import { IAnuncios } from 'app/shared/model/anuncios.model';
// import anuncios from "app/entities/anuncios/index";

export const Anuncios = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const anunciosList = useAppSelector(state => state.anuncios.entities);
  const loading = useAppSelector(state => state.anuncios.loading);
  const totalItems = useAppSelector(state => state.anuncios.totalItems);
  const account = useAppSelector(state => state.authentication.account);

  const isAdministradorConjunto = hasAnyAuthority(account.authorities, [Authority.ADMINISTRADOR_CONJUNTO]);
  // const isAdmin = hasAnyAuthority(account.authorities, [Authority.ADMIN]);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  /* const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  }; */

  const handlePagination = (currentPage: number) =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  /* const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  }; */

  return (
    <div className="entity-page">
      <div className="entity-page-header">
        <div className="d-flex justify-content-between align-items-center mb-4">
          <h2 id="anuncios-heading" data-cy="AnunciosHeading">
            Anuncios
          </h2>
          <div className="flex-grow-1 d-flex justify-content-end">
            <Button className="me-2" variant="info" onClick={handleSyncList} disabled={loading}>
              <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
            </Button>
            {isAdministradorConjunto && (
              <Link to="/anuncios/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
                <FontAwesomeIcon icon="plus" />
                &nbsp; Crear nuevo Anuncio
              </Link>
            )}
          </div>
        </div>
      </div>
      <div className="anuncios-grid">
        {anunciosList?.length > 0
          ? anunciosList.map((anuncios: IAnuncios) => (
              <div className="anuncio-card" key={`entity-${anuncios.id}`} data-cy="entityCard">
                {anuncios.imagen ? (
                  <>
                    <div className="anuncio-card-image-container">
                      <img
                        src={`data:${anuncios.imagenContentType};base64,${anuncios.imagen}`}
                        alt={anuncios.titulo}
                        className="anuncio-card-image"
                      />
                    </div>

                    <div className={anuncios.imagen ? 'anuncio-card-info' : 'anuncio-card-info-full'}>
                      <div className="anuncio-card-info">
                        <div className="info-card-anuncio">
                          <span className="info-title">Título</span>
                          <span>{anuncios.titulo}</span>
                        </div>

                        <div className="info-card-anuncio">
                          <span className="info-title">Descripción</span>
                          <span>{anuncios.descripcion}</span>
                        </div>

                        <div className="info-card-anuncio">
                          <span className="info-title">Fecha Publicación</span>
                          <span>
                            {anuncios.fecha ? <TextFormat value={anuncios.fecha as any} type="date" format={APP_DATE_FORMAT} /> : null}
                          </span>
                        </div>

                        <div className="btn-group flex-btn-group-container mt-3">
                          <Button as={Link as any} to={`/anuncios/${anuncios.id}`} variant="info" size="sm">
                            <FontAwesomeIcon icon="eye" /> Vista
                          </Button>

                          {isAdministradorConjunto && (
                            <>
                              <Button
                                as={Link as any}
                                to={`/anuncios/${anuncios.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                                variant="primary"
                                size="sm"
                              >
                                <FontAwesomeIcon icon="pencil-alt" /> Editar
                              </Button>

                              <Button
                                onClick={() =>
                                  (window.location.href = `/anuncios/${anuncios.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                                }
                                variant="danger"
                                size="sm"
                              >
                                <FontAwesomeIcon icon="trash" /> Eliminar
                              </Button>
                            </>
                          )}
                        </div>
                      </div>
                    </div>
                  </>
                ) : (
                  <div className="anuncio-card-info-full">
                    <div className="info-card-anuncio">
                      <span className="info-title">Título</span>
                      <span>{anuncios.titulo}</span>
                    </div>

                    <div className="info-card-anuncio">
                      <span className="info-title">Descripción</span>
                      <span>{anuncios.descripcion}</span>
                    </div>

                    <div className="info-card-anuncio">
                      <span className="info-title">Fecha Publicación</span>
                      <span>
                        {anuncios.fecha ? <TextFormat value={anuncios.fecha as any} type="date" format={APP_DATE_FORMAT} /> : null}
                      </span>
                    </div>

                    <div className="btn-group flex-btn-group-container mt-3">
                      <Button as={Link as any} to={`/anuncios/${anuncios.id}`} variant="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> Vista
                      </Button>

                      {isAdministradorConjunto && (
                        <>
                          <Button
                            as={Link as any}
                            to={`/anuncios/${anuncios.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                            variant="primary"
                            size="sm"
                          >
                            <FontAwesomeIcon icon="pencil-alt" /> Editar
                          </Button>

                          <Button
                            onClick={() =>
                              (window.location.href = `/anuncios/${anuncios.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                            }
                            variant="danger"
                            size="sm"
                          >
                            <FontAwesomeIcon icon="trash" /> Eliminar
                          </Button>
                        </>
                      )}
                    </div>
                  </div>
                )}
              </div>
            ))
          : !loading && <div className="alert alert-warning">Ningún Anuncios encontrado</div>}
      </div>
      {totalItems ? (
        <div className="pagination-container">
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Anuncios;
