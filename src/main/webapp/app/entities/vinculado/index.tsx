import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vinculado from './vinculado';
import VinculadoDeleteDialog from './vinculado-delete-dialog';
import VinculadoDetail from './vinculado-detail';
import VinculadoUpdate from './vinculado-update';

const VinculadoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vinculado />} />
    <Route path="new" element={<VinculadoUpdate />} />
    <Route path=":id">
      <Route index element={<VinculadoDetail />} />
      <Route path="edit" element={<VinculadoUpdate />} />
      <Route path="delete" element={<VinculadoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VinculadoRoutes;
