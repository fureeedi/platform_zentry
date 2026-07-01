import React from 'react';

import { NavDropdown } from './menu-components';
import MenuItem from 'app/shared/layout/menus/menu-item';

export const ClienteMenu = () => (
  <NavDropdown
    icon="th-list"
    name="Entidades"
    id="administrador-conjunto-menu"
    data-cy="administrador-conjunto"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/anuncios">
      Anuncios
    </MenuItem>
    <MenuItem icon="asterisk" to="/servicio-conjunto">
      Servicio Conjunto
    </MenuItem>
    <MenuItem icon="asterisk" to="/reservas">
      Reservas
    </MenuItem>
    <MenuItem icon="asterisk" to="/factura-de-pago">
      Factura De Pago
    </MenuItem>
  </NavDropdown>
);
