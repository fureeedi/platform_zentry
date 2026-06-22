import React, { useEffect, useState } from 'react';
import { Button, Table } from 'react-bootstrap';
import { JhiItemCount, JhiPagination, getPaginationState } from 'react-jhipster';
import { Link, useLocation, useNavigate } from 'react-router';

import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';

import { getEntities } from './vinculado.reducer';

export const Vinculado = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const vinculadoList = useAppSelector(state => state.vinculado.entities);
  const loading = useAppSelector(state => state.vinculado.loading);
  const totalItems = useAppSelector(state => state.vinculado.totalItems);

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
      <h2 id="vinculado-heading" data-cy="VinculadoHeading">
        Vinculados
        <div className="d-flex justify-content-end">
          <Button className="me-2" variant="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/vinculado/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Vinculado
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vinculadoList?.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('nombres')}>
                  Nombres <FontAwesomeIcon icon={getSortIconByFieldName('nombres')} />
                </th>
                <th className="hand" onClick={sort('apellidos')}>
                  Apellidos <FontAwesomeIcon icon={getSortIconByFieldName('apellidos')} />
                </th>
                <th className="hand" onClick={sort('numeroDocumento')}>
                  Numero Documento <FontAwesomeIcon icon={getSortIconByFieldName('numeroDocumento')} />
                </th>
                <th className="hand" onClick={sort('telefono')}>
                  Telefono <FontAwesomeIcon icon={getSortIconByFieldName('telefono')} />
                </th>
                <th className="hand" onClick={sort('correo')}>
                  Correo <FontAwesomeIcon icon={getSortIconByFieldName('correo')} />
                </th>
                <th className="hand" onClick={sort('activo')}>
                  Activo <FontAwesomeIcon icon={getSortIconByFieldName('activo')} />
                </th>
                <th>
                  User <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Tipo Documento <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Administrador Conjunto <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vinculadoList.map(vinculado => (
                <tr key={`entity-${vinculado.id}`} data-cy="entityTable">
                  <td>
                    <Button as={Link as any} to={`/vinculado/${vinculado.id}`} variant="link" size="sm">
                      {vinculado.id}
                    </Button>
                  </td>
                  <td>{vinculado.nombres}</td>
                  <td>{vinculado.apellidos}</td>
                  <td>{vinculado.numeroDocumento}</td>
                  <td>{vinculado.telefono}</td>
                  <td>{vinculado.correo}</td>
                  <td>{vinculado.activo ? 'true' : 'false'}</td>
                  <td>{vinculado.user ? vinculado.user.login : ''}</td>
                  <td>
                    {vinculado.tipoDocumento ? (
                      <Link to={`/tipo-documento/${vinculado.tipoDocumento.id}`}>{vinculado.tipoDocumento.nombreTipoDocumento}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {vinculado.administradorConjunto ? (
                      <Link to={`/administrador-conjunto/${vinculado.administradorConjunto.id}`}>
                        {vinculado.administradorConjunto.numeroDocumento}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button as={Link as any} to={`/vinculado/${vinculado.id}`} variant="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button
                        as={Link as any}
                        to={`/vinculado/${vinculado.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        variant="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/vinculado/${vinculado.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
          !loading && <div className="alert alert-warning">Ningún Vinculados encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={vinculadoList && vinculadoList.length > 0 ? '' : 'd-none'}>
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

export default Vinculado;
