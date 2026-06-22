import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Anuncios from './anuncios';
import AnunciosDeleteDialog from './anuncios-delete-dialog';
import AnunciosDetail from './anuncios-detail';
import AnunciosUpdate from './anuncios-update';

const AnunciosRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Anuncios />} />
    <Route path="new" element={<AnunciosUpdate />} />
    <Route path=":id">
      <Route index element={<AnunciosDetail />} />
      <Route path="edit" element={<AnunciosUpdate />} />
      <Route path="delete" element={<AnunciosDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AnunciosRoutes;
