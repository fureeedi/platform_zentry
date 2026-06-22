import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/tipo-documento">
        Tipo Documento
      </MenuItem>
      <MenuItem icon="asterisk" to="/anuncios">
        Anuncios
      </MenuItem>
      <MenuItem icon="asterisk" to="/servicio">
        Servicio
      </MenuItem>
      <MenuItem icon="asterisk" to="/conjunto-residencial">
        Conjunto Residencial
      </MenuItem>
      <MenuItem icon="asterisk" to="/servicio-conjunto">
        Servicio Conjunto
      </MenuItem>
      <MenuItem icon="asterisk" to="/vinculado-inmueble">
        Vinculado Inmueble
      </MenuItem>
      <MenuItem icon="asterisk" to="/reservas">
        Reservas
      </MenuItem>
      <MenuItem icon="asterisk" to="/factura-de-pago">
        Factura De Pago
      </MenuItem>
      <MenuItem icon="asterisk" to="/vinculado">
        Vinculado
      </MenuItem>
      <MenuItem icon="asterisk" to="/inmueble">
        Inmueble
      </MenuItem>
      <MenuItem icon="asterisk" to="/administrador-conjunto">
        Administrador Conjunto
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
