import dayjs from 'dayjs';

import { IAdministradorConjunto } from 'app/shared/model/administrador-conjunto.model';
import { IConjuntoResidencial } from 'app/shared/model/conjunto-residencial.model';

export interface IAnuncios {
  id?: string;
  titulo?: string;
  descripcion?: string;
  fecha?: dayjs.Dayjs;
  imagenContentType?: string | null;
  imagen?: string | null;
  conjuntoResidencial?: IConjuntoResidencial;
  administradorConjunto?: IAdministradorConjunto;
}

export const defaultValue: Readonly<IAnuncios> = {};
