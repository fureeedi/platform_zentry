import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AdministradorConjunto from './administrador-conjunto';
import AdministradorConjuntoDeleteDialog from './administrador-conjunto-delete-dialog';
import AdministradorConjuntoDetail from './administrador-conjunto-detail';
import AdministradorConjuntoUpdate from './administrador-conjunto-update';

const AdministradorConjuntoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AdministradorConjunto />} />
    <Route path="new" element={<AdministradorConjuntoUpdate />} />
    <Route path=":id">
      <Route index element={<AdministradorConjuntoDetail />} />
      <Route path="edit" element={<AdministradorConjuntoUpdate />} />
      <Route path="delete" element={<AdministradorConjuntoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AdministradorConjuntoRoutes;
