import dayjs from 'dayjs';

import { IConjuntoResidencial } from 'app/shared/model/conjunto-residencial.model';
import { IVinculado } from 'app/shared/model/vinculado.model';

export interface IFacturaDePago {
  id?: string;
  fechaEnvio?: dayjs.Dayjs;
  imagenFacturaContentType?: string;
  imagenFactura?: string;
  conjuntoResidencial?: IConjuntoResidencial;
  vinculado?: IVinculado;
}

export const defaultValue: Readonly<IFacturaDePago> = {};
