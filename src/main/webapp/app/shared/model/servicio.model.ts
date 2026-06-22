export interface IServicio {
  id?: string;
  disponibilidad?: string;
  nombreZonaComun?: string;
  descripcion?: string | null;
}

export const defaultValue: Readonly<IServicio> = {};
