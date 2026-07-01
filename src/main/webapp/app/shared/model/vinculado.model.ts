import { IAdministradorConjunto } from 'app/shared/model/administrador-conjunto.model';
import { ITipoDocumento } from 'app/shared/model/tipo-documento.model';

export interface IVinculado {
  id?: string;
  nombres?: string;
  apellidos?: string;
  numeroDocumento?: string;
  telefono?: string | null;
  correo?: string;
  activo?: boolean;
  tipoDocumento?: ITipoDocumento;
  administradorConjunto?: IAdministradorConjunto;
  login?: string;
  password?: string;
}

export const defaultValue: Readonly<IVinculado> = {
  activo: false,
};
