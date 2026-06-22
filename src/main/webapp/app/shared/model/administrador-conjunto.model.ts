import { IConjuntoResidencial } from 'app/shared/model/conjunto-residencial.model';
import { ITipoDocumento } from 'app/shared/model/tipo-documento.model';
import { IUser } from 'app/shared/model/user.model';

export interface IAdministradorConjunto {
  id?: string;
  nombres?: string;
  apellidos?: string;
  numeroDocumento?: string;
  telefono?: string | null;
  correo?: string;
  activo?: boolean;
  user?: IUser;
  conjuntoResidencial?: IConjuntoResidencial;
  tipoDocumento?: ITipoDocumento;
}

export const defaultValue: Readonly<IAdministradorConjunto> = {
  activo: false,
};
