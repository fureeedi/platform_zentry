import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AdministradorConjunto from './administrador-conjunto';
import Anuncios from './anuncios';
import ConjuntoResidencial from './conjunto-residencial';
import FacturaDePago from './factura-de-pago';
import Inmueble from './inmueble';
import Reservas from './reservas';
import Servicio from './servicio';
import ServicioConjunto from './servicio-conjunto';
import TipoDocumento from './tipo-documento';
import Vinculado from './vinculado';
import VinculadoInmueble from './vinculado-inmueble';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="/tipo-documento/*" element={<TipoDocumento />} />
        <Route path="/anuncios/*" element={<Anuncios />} />
        <Route path="/servicio/*" element={<Servicio />} />
        <Route path="/conjunto-residencial/*" element={<ConjuntoResidencial />} />
        <Route path="/servicio-conjunto/*" element={<ServicioConjunto />} />
        <Route path="/vinculado-inmueble/*" element={<VinculadoInmueble />} />
        <Route path="/reservas/*" element={<Reservas />} />
        <Route path="/factura-de-pago/*" element={<FacturaDePago />} />
        <Route path="/vinculado/*" element={<Vinculado />} />
        <Route path="/inmueble/*" element={<Inmueble />} />
        <Route path="/administrador-conjunto/*" element={<AdministradorConjunto />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
