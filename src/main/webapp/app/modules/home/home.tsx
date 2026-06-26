import './home.scss';

import React from 'react';
import { Alert, Col, Row } from 'react-bootstrap';
import { Link } from 'react-router';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="9">
        <h1 className="display-4">¡Bienvenido, a Zentry!</h1>
        <p className="lead">Esta es su página de inicio</p>
        {account?.login ? (
          <div>
            <Alert variant="success">Está conectado como &quot;{account.login}&quot;.</Alert>
          </div>
        ) : (
          <div>
            <Alert variant="warning">
              Si desea
              <span>&nbsp;</span>
              <Link to="/login" className="alert-link">
                iniciar sesión
              </Link>
              , puede intentar con las cuentas predeterminadas:
              <br />- Administrador (usuario=&quot;admin&quot; y contraseña=&quot;admin&quot;) <br />- Usuario (usuario=&quot;user&quot; y
              contraseña=&quot;user&quot;).
            </Alert>

            <Alert variant="warning">¿Aún no tienes una cuenta?&nbsp;</Alert>
          </div>
        )}
      </Col>
    </Row>
  );
};

export default Home;
