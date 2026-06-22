import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FacturaDePago from './factura-de-pago';
import FacturaDePagoDeleteDialog from './factura-de-pago-delete-dialog';
import FacturaDePagoDetail from './factura-de-pago-detail';
import FacturaDePagoUpdate from './factura-de-pago-update';

const FacturaDePagoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FacturaDePago />} />
    <Route path="new" element={<FacturaDePagoUpdate />} />
    <Route path=":id">
      <Route index element={<FacturaDePagoDetail />} />
      <Route path="edit" element={<FacturaDePagoUpdate />} />
      <Route path="delete" element={<FacturaDePagoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FacturaDePagoRoutes;
