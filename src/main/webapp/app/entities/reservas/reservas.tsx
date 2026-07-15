import React, { useEffect, useState } from 'react';
import { Button, Table } from 'react-bootstrap';
import { JhiPagination, TextFormat, getPaginationState } from 'react-jhipster';
import { Link, useLocation, useNavigate } from 'react-router';

import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Authority } from 'app/shared/jhipster/constants';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';

import { getEntities, cambiarEstado } from './reservas.reducer';
import { getEntities as getServicios } from 'app/entities/servicio-conjunto/servicio-conjunto.reducer';

export const Reservas = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const [estadoFiltro, setEstadoFiltro] = useState('');
  const [servicioFiltro, setServicioFiltro] = useState('');

  const reservasList = useAppSelector(state => state.reservas.entities);
  const servicios = useAppSelector(state => state.servicioConjunto.entities);
  const loading = useAppSelector(state => state.reservas.loading);
  const totalItems = useAppSelector(state => state.reservas.totalItems);
  const account = useAppSelector(state => state.authentication.account);

  const isAdmin = hasAnyAuthority(account.authorities, [Authority.ADMIN]);
  const isAdministradorConjunto = hasAnyAuthority(account.authorities, [Authority.ADMINISTRADOR_CONJUNTO]);
  const isCliente = hasAnyAuthority(account.authorities, [Authority.CLIENTE]);

  /* const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  }; */

  const sortEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
        estado: estadoFiltro,
        servicioId: servicioFiltro,
      }),
    );
    const endURL =
      `?page=${paginationState.activePage}` +
      `&sort=${paginationState.sort},${paginationState.order}` +
      `&estado=${estadoFiltro}` +
      `&servicioId=${servicioFiltro}`;

    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort, estadoFiltro, servicioFiltro]);

  useEffect(() => {
    dispatch(
      getServicios({
        page: 0,
        size: 100,
        sort: 'id,asc',
      }),
    );
  }, []);

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
    <div className="entity-page">
      <div className="d-flex justify-content-between align-items-center">
        <h2 id="reservas-heading" data-cy="ReservasHeading">
          Reservas
        </h2>
        <div className="d-flex justify-content-end">
          {isAdministradorConjunto && (
            <Button className="me-2" variant="info" onClick={handleSyncList} disabled={loading}>
              <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
            </Button>
          )}
          {isCliente && (
            <Link to="/reservas/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
              <FontAwesomeIcon icon="plus" />
              &nbsp; Crear una Reserva
            </Link>
          )}
        </div>
      </div>
      {isAdministradorConjunto && (
        <>
          <div className="card filter-card mb-3">
            <div className="card-body">
              <div className="row align-items-end">
                <div className="col-md-4">
                  <label className="form-label">Estado</label>

                  <select className="form-select" value={estadoFiltro} onChange={e => setEstadoFiltro(e.target.value)}>
                    <option value="">Todas</option>
                    <option value="PENDIENTE">Pendientes</option>
                    <option value="APROBADO">Aprobadas</option>
                    <option value="RECHAZADO">Rechazadas</option>
                  </select>
                </div>

                <div className="col-md-4">
                  <label className="form-label">Servicio</label>

                  <select className="form-select" value={servicioFiltro} onChange={e => setServicioFiltro(e.target.value)}>
                    <option value="">Todos</option>

                    {servicios.map(servicio => (
                      <option key={servicio.id} value={servicio.id}>
                        {servicio.servicio?.nombreZonaComun}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
            </div>
          </div>
        </>
      )}
      <div className="table-responsive entity-table">
        {reservasList?.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                {isAdmin && (
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                )}
                <th>Fecha Solicitud</th>
                <th>Fecha Reserva</th>
                <th>Hora Inicio</th>
                <th>Hora Finalización</th>
                <th>Cupos Apartados</th>
                <th>Estado estado</th>
                <th>Servicio Conjunto</th>
                {isAdministradorConjunto && <th>Vinculado</th>}
                <th />
              </tr>
            </thead>
            <tbody>
              {reservasList.map(reservas => (
                <tr key={`entity-${reservas.id}`} data-cy="entityTable">
                  {isAdmin && (
                    <td>
                      <Button as={Link as any} to={`/reservas/${reservas.id}`} variant="link" size="sm">
                        {reservas.id}
                      </Button>
                    </td>
                  )}
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
                  <td>
                    {reservas.estado === 'PENDIENTE' && <span className="badge bg-warning text-dark">Pendiente</span>}
                    {reservas.estado === 'APROBADO' && <span className="badge bg-success">Aprobado</span>}
                    {reservas.estado === 'RECHAZADO' && <span className="badge bg-danger">Rechazado</span>}
                  </td>
                  <td>
                    {reservas.servicioConjunto ? (
                      <Link to={`/servicio-conjunto/${reservas.servicioConjunto.id}`}>
                        {reservas.servicioConjunto.servicio?.nombreZonaComun}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  {isAdministradorConjunto && (
                    <td>
                      {reservas.vinculado ? (
                        <Link to={`/vinculado/${reservas.vinculado.id}`}>
                          {`${reservas.vinculado.nombres} ${reservas.vinculado.apellidos} - ${reservas.vinculado.numeroDocumento}`}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                  )}
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        as={Link as any}
                        to={`/reservas/${reservas.id}`}
                        variant="info"
                        size="sm"
                        className="me-1"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      {isAdministradorConjunto && reservas.estado === 'PENDIENTE' && (
                        <>
                          <Button
                            variant="success"
                            size="sm"
                            className="me-1"
                            onClick={() =>
                              dispatch(
                                cambiarEstado({
                                  id: reservas.id!,
                                  estado: 'APROBADO',
                                }),
                              )
                            }
                          >
                            <FontAwesomeIcon icon="check" /> Aprobar
                          </Button>
                          <Button
                            variant="danger"
                            size="sm"
                            className="me-1"
                            onClick={() =>
                              dispatch(
                                cambiarEstado({
                                  id: reservas.id!,
                                  estado: 'RECHAZADO',
                                }),
                              )
                            }
                          >
                            <FontAwesomeIcon icon="times-circle" /> Rechazar
                          </Button>
                        </>
                      )}
                      {isAdmin && (
                        <>
                          <Button
                            as={Link as any}
                            to={`/reservas/${reservas.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                            variant="primary"
                            size="sm"
                            className="me-1"
                            data-cy="entityEditButton"
                          >
                            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                          </Button>
                        </>
                      )}
                      {isAdministradorConjunto && (
                        <>
                          <Button
                            onClick={() =>
                              (window.location.href = `/reservas/${reservas.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                            }
                            variant="warning"
                            size="sm"
                            className="me-1"
                            data-cy="entityDeleteButton"
                          >
                            <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                          </Button>
                        </>
                      )}
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

export default Reservas;
