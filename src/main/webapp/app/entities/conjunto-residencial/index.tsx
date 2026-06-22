import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ConjuntoResidencial from './conjunto-residencial';
import ConjuntoResidencialDeleteDialog from './conjunto-residencial-delete-dialog';
import ConjuntoResidencialDetail from './conjunto-residencial-detail';
import ConjuntoResidencialUpdate from './conjunto-residencial-update';

const ConjuntoResidencialRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ConjuntoResidencial />} />
    <Route path="new" element={<ConjuntoResidencialUpdate />} />
    <Route path=":id">
      <Route index element={<ConjuntoResidencialDetail />} />
      <Route path="edit" element={<ConjuntoResidencialUpdate />} />
      <Route path="delete" element={<ConjuntoResidencialDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConjuntoResidencialRoutes;
