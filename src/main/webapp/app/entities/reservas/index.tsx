import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Reservas from './reservas';
import ReservasDeleteDialog from './reservas-delete-dialog';
import ReservasDetail from './reservas-detail';
import ReservasUpdate from './reservas-update';

const ReservasRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Reservas />} />
    <Route path="new" element={<ReservasUpdate />} />
    <Route path=":id">
      <Route index element={<ReservasDetail />} />
      <Route path="edit" element={<ReservasUpdate />} />
      <Route path="delete" element={<ReservasDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReservasRoutes;
