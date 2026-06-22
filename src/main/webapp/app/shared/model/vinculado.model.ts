import { IAdministradorConjunto } from 'app/shared/model/administrador-conjunto.model';
import { ITipoDocumento } from 'app/shared/model/tipo-documento.model';
import { IUser } from 'app/shared/model/user.model';

export interface IVinculado {
  id?: string;
  nombres?: string;
  apellidos?: string;
  numeroDocumento?: string;
  telefono?: string | null;
  correo?: string;
  activo?: boolean;
  user?: IUser;
  tipoDocumento?: ITipoDocumento;
  administradorConjunto?: IAdministradorConjunto;
}

export const defaultValue: Readonly<IVinculado> = {
  activo: false,
};
