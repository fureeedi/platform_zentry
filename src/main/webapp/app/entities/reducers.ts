import administradorConjunto from 'app/entities/administrador-conjunto/administrador-conjunto.reducer';
import anuncios from 'app/entities/anuncios/anuncios.reducer';
import conjuntoResidencial from 'app/entities/conjunto-residencial/conjunto-residencial.reducer';
import facturaDePago from 'app/entities/factura-de-pago/factura-de-pago.reducer';
import inmueble from 'app/entities/inmueble/inmueble.reducer';
import reservas from 'app/entities/reservas/reservas.reducer';
import servicio from 'app/entities/servicio/servicio.reducer';
import servicioConjunto from 'app/entities/servicio-conjunto/servicio-conjunto.reducer';
import tipoDocumento from 'app/entities/tipo-documento/tipo-documento.reducer';
import vinculado from 'app/entities/vinculado/vinculado.reducer';
import vinculadoInmueble from 'app/entities/vinculado-inmueble/vinculado-inmueble.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  tipoDocumento,
  anuncios,
  servicio,
  conjuntoResidencial,
  servicioConjunto,
  vinculadoInmueble,
  reservas,
  facturaDePago,
  vinculado,
  inmueble,
  administradorConjunto,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
