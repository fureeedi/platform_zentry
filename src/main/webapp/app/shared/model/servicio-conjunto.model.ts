import { IConjuntoResidencial } from 'app/shared/model/conjunto-residencial.model';
import { TipoDisponibilidad } from 'app/shared/model/enumerations/tipo-disponibilidad.model';
import { IServicio } from 'app/shared/model/servicio.model';

export interface IServicioConjunto {
  id?: string;
  disponible?: keyof typeof TipoDisponibilidad;
  aforoMaximo?: number;
  conjuntoResidencial?: IConjuntoResidencial;
  servicio?: IServicio;
}

export const defaultValue: Readonly<IServicioConjunto> = {};
