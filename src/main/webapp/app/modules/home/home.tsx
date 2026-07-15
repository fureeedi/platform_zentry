import './home.scss';

import React from 'react';
import { Card, Col, Row } from 'react-bootstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Card className="jh-card">
      <Row className="justify-content-center align-items-center" style={{ minHeight: '60vh' }}>
        <Col md="10" lg="8" className="text-center px-4">
          <h1 className="display-4 text-white fw-bold mb-3">¡Bienvenido a Zentry!</h1>

          <p className="fs-5 text-light opacity-75 mb-5 lh-base">
            Una plataforma diseñada para administrar anuncios, reservas de zonas comunes y comunicación entre administradores y residentes.
          </p>

          {account?.login ? (
            <div className="text-white">
              <p>Ya has iniciado sesión como {account.login}.</p>
            </div>
          ) : (
            <div className="d-flex flex-column align-items-center gap-3">
              <a href="/login" className="btn btn-info btn-lg px-5 py-3 fw-bold shadow-sm">
                Iniciar sesión
              </a>

              <p className="small text-light opacity-50 mt-2">
                ¿Aún no tienes una cuenta?{' '}
                <span className="text-white text-decoration-underline fw-semibold">Solicítala al administrador</span>
              </p>
            </div>
          )}
        </Col>
      </Row>
    </Card>
  );
};

export default Home;
