import dayjs from 'dayjs';

import { Estado } from 'app/shared/model/enumerations/estado.model';
import { IServicioConjunto } from 'app/shared/model/servicio-conjunto.model';
import { IVinculado } from 'app/shared/model/vinculado.model';

export interface IReservas {
  id?: string;
  fechaSolicitud?: dayjs.Dayjs;
  fechaReserva?: dayjs.Dayjs;
  horaInicio?: string;
  horafin?: string;
  cuposApartados?: number | null;
  estado?: keyof typeof Estado;
  servicioConjunto?: IServicioConjunto;
  vinculado?: IVinculado;
}

export const defaultValue: Readonly<IReservas> = {};
