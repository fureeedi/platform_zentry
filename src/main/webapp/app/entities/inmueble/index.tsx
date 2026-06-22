import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Inmueble from './inmueble';
import InmuebleDeleteDialog from './inmueble-delete-dialog';
import InmuebleDetail from './inmueble-detail';
import InmuebleUpdate from './inmueble-update';

const InmuebleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Inmueble />} />
    <Route path="new" element={<InmuebleUpdate />} />
    <Route path=":id">
      <Route index element={<InmuebleDetail />} />
      <Route path="edit" element={<InmuebleUpdate />} />
      <Route path="delete" element={<InmuebleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InmuebleRoutes;
