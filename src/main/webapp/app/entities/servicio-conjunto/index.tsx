import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ServicioConjunto from './servicio-conjunto';
import ServicioConjuntoDeleteDialog from './servicio-conjunto-delete-dialog';
import ServicioConjuntoDetail from './servicio-conjunto-detail';
import ServicioConjuntoUpdate from './servicio-conjunto-update';

const ServicioConjuntoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ServicioConjunto />} />
    <Route path="new" element={<ServicioConjuntoUpdate />} />
    <Route path=":id">
      <Route index element={<ServicioConjuntoDetail />} />
      <Route path="edit" element={<ServicioConjuntoUpdate />} />
      <Route path="delete" element={<ServicioConjuntoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServicioConjuntoRoutes;
