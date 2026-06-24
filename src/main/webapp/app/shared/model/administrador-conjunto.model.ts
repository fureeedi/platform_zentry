import { IConjuntoResidencial } from 'app/shared/model/conjunto-residencial.model';
import { ITipoDocumento } from 'app/shared/model/tipo-documento.model';

export interface IAdministradorConjunto {
  id?: string;
  nombres?: string;
  apellidos?: string;
  numeroDocumento?: string;
  telefono?: string | null;
  correo?: string;
  activo?: boolean;
  conjuntoResidencial?: IConjuntoResidencial;
  tipoDocumento?: ITipoDocumento;
  login?: string;
  password?: string;
}

export const defaultValue: Readonly<IAdministradorConjunto> = {
  activo: false,
};
