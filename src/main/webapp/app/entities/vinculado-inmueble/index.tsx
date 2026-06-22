import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import VinculadoInmueble from './vinculado-inmueble';
import VinculadoInmuebleDeleteDialog from './vinculado-inmueble-delete-dialog';
import VinculadoInmuebleDetail from './vinculado-inmueble-detail';
import VinculadoInmuebleUpdate from './vinculado-inmueble-update';

const VinculadoInmuebleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<VinculadoInmueble />} />
    <Route path="new" element={<VinculadoInmuebleUpdate />} />
    <Route path=":id">
      <Route index element={<VinculadoInmuebleDetail />} />
      <Route path="edit" element={<VinculadoInmuebleUpdate />} />
      <Route path="delete" element={<VinculadoInmuebleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VinculadoInmuebleRoutes;
