import { TipoVinculo } from 'app/shared/model/enumerations/tipo-vinculo.model';
import { IInmueble } from 'app/shared/model/inmueble.model';
import { IVinculado } from 'app/shared/model/vinculado.model';

export interface IVinculadoInmueble {
  id?: string;
  tipoVinculo?: keyof typeof TipoVinculo;
  vinculado?: IVinculado;
  inmueble?: IInmueble;
}

export const defaultValue: Readonly<IVinculadoInmueble> = {};
