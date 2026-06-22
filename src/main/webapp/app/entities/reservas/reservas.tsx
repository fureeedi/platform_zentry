import React, { useEffect, useState } from 'react';
import { Button, Table } from 'react-bootstrap';
import { JhiItemCount, JhiPagination, TextFormat, getPaginationState } from 'react-jhipster';
import { Link, useLocation, useNavigate } from 'react-router';

import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';

import { getEntities } from './reservas.reducer';

export const Reservas = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const reservasList = useAppSelector(state => state.reservas.entities);
  const loading = useAppSelector(state => state.reservas.loading);
  const totalItems = useAppSelector(state => state.reservas.totalItems);

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

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="reservas-heading" data-cy="ReservasHeading">
        Reservas
        <div className="d-flex justify-content-end">
          <Button className="me-2" variant="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/reservas/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Reservas
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {reservasList?.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('fechaSolicitud')}>
                  Fecha Solicitud <FontAwesomeIcon icon={getSortIconByFieldName('fechaSolicitud')} />
                </th>
                <th className="hand" onClick={sort('fechaReserva')}>
                  Fecha Reserva <FontAwesomeIcon icon={getSortIconByFieldName('fechaReserva')} />
                </th>
                <th className="hand" onClick={sort('horaInicio')}>
                  Hora Inicio <FontAwesomeIcon icon={getSortIconByFieldName('horaInicio')} />
                </th>
                <th className="hand" onClick={sort('horafin')}>
                  Horafin <FontAwesomeIcon icon={getSortIconByFieldName('horafin')} />
                </th>
                <th className="hand" onClick={sort('cuposApartados')}>
                  Cupos Apartados <FontAwesomeIcon icon={getSortIconByFieldName('cuposApartados')} />
                </th>
                <th className="hand" onClick={sort('estado')}>
                  Estado <FontAwesomeIcon icon={getSortIconByFieldName('estado')} />
                </th>
                <th>
                  Servicio Conjunto <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Vinculado <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {reservasList.map(reservas => (
                <tr key={`entity-${reservas.id}`} data-cy="entityTable">
                  <td>
                    <Button as={Link as any} to={`/reservas/${reservas.id}`} variant="link" size="sm">
                      {reservas.id}
                    </Button>
                  </td>
                  <td>
                    {reservas.fechaSolicitud ? (
                      <TextFormat type="date" value={reservas.fechaSolicitud} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {reservas.fechaReserva ? <TextFormat type="date" value={reservas.fechaReserva} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{reservas.horaInicio}</td>
                  <td>{reservas.horafin}</td>
                  <td>{reservas.cuposApartados}</td>
                  <td>{reservas.estado}</td>
                  <td>
                    {reservas.servicioConjunto ? (
                      <Link to={`/servicio-conjunto/${reservas.servicioConjunto.id}`}>{reservas.servicioConjunto.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {reservas.vinculado ? <Link to={`/vinculado/${reservas.vinculado.id}`}>{reservas.vinculado.numeroDocumento}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button as={Link as any} to={`/reservas/${reservas.id}`} variant="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button
                        as={Link as any}
                        to={`/reservas/${reservas.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        variant="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/reservas/${reservas.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        variant="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Ningún Reservas encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={reservasList && reservasList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
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

export default Reservas;
