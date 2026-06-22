import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Servicio from './servicio';
import ServicioDeleteDialog from './servicio-delete-dialog';
import ServicioDetail from './servicio-detail';
import ServicioUpdate from './servicio-update';

const ServicioRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Servicio />} />
    <Route path="new" element={<ServicioUpdate />} />
    <Route path=":id">
      <Route index element={<ServicioDetail />} />
      <Route path="edit" element={<ServicioUpdate />} />
      <Route path="delete" element={<ServicioDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServicioRoutes;
