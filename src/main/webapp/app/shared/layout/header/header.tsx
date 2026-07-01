import './header.scss';

import React from 'react';
import { Nav, Navbar } from 'react-bootstrap';

import LoadingBar from 'react-redux-loading-bar';

import { AccountMenu, AdminMenu, EntitiesMenu, AdministradorConjuntoMenu, ClienteMenu } from '../menus';

import { Brand, Home } from './header-components';

export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  isAdministradorConjunto: boolean;
  isCliente: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isOpenAPIEnabled: boolean;
}

const Header = (props: IHeaderProps) => {
  /* jhipster-needle-add-element-to-menu - JHipster will add new menu items here */

  return (
    <div id="app-header">
      <LoadingBar className="loading-bar" />
      <Navbar data-cy="navbar" data-bs-theme="dark" expand="md" fixed="top" className="navbar-zentry" collapseOnSelect>
        <Navbar.Toggle aria-controls="header-tabs" aria-label="Menu" />
        <Brand />
        <Navbar.Collapse id="header-tabs">
          <Nav className="ms-auto">
            <Home />
            {props.isAuthenticated && props.isAdmin && <EntitiesMenu />}
            {props.isAuthenticated && props.isAdministradorConjunto && <AdministradorConjuntoMenu />}
            {props.isAuthenticated && props.isCliente && <ClienteMenu />}
            {props.isAuthenticated && props.isAdmin && <AdminMenu showOpenAPI={props.isOpenAPIEnabled} />}
            <AccountMenu isAuthenticated={props.isAuthenticated} />
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    </div>
  );
};

export default Header;
