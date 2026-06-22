import { IConjuntoResidencial } from 'app/shared/model/conjunto-residencial.model';
import { TipoInmueble } from 'app/shared/model/enumerations/tipo-inmueble.model';

export interface IInmueble {
  id?: string;
  tipoInmueble?: keyof typeof TipoInmueble;
  torre?: string | null;
  numeroInmueble?: string;
  piso?: number | null;
  conjuntoResidencial?: IConjuntoResidencial;
}

export const defaultValue: Readonly<IInmueble> = {};
