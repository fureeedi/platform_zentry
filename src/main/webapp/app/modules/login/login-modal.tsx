import React from 'react';
import { Alert, Button, Col, Form, Modal, ModalBody, ModalFooter, ModalHeader, Row } from 'react-bootstrap';
import { ValidatedField } from 'react-jhipster';
import { Link } from 'react-router';

import { type FieldError, useForm } from 'react-hook-form';

export interface ILoginModalProps {
  showModal: boolean;
  loginError: boolean;
  handleLogin: (username: string, password: string, rememberMe: boolean) => void;
  handleClose: () => void;
}

const LoginModal = (props: ILoginModalProps) => {
  const login = ({ username, password, rememberMe }) => {
    props.handleLogin(username, password, rememberMe);
  };

  const {
    handleSubmit,
    register,
    formState: { errors, touchedFields },
  } = useForm({ mode: 'onTouched' });

  const { loginError, handleClose } = props;

  const handleLoginSubmit = e => {
    handleSubmit(login)(e);
  };

  return (
    <Modal show={props.showModal} onHide={handleClose} backdrop="static" id="login-page" autoFocus={false}>
      <Form onSubmit={handleLoginSubmit}>
        <ModalHeader id="login-title" data-cy="loginTitle" closeButton>
          Iniciar la sesión
        </ModalHeader>
        <ModalBody>
          <Row>
            <Col md="12">
              {loginError && (
                <Alert variant="danger" data-cy="loginError">
                  <strong>¡El inicio de sesión ha fallado!</strong> Por favor, revise las credenciales e intente de nuevo.
                </Alert>
              )}
            </Col>
            <Col md="12">
              <ValidatedField
                name="username"
                label="Usuario"
                placeholder="Nombre de usuario"
                required
                autoFocus
                data-cy="username"
                validate={{ required: 'Username cannot be empty!' }}
                register={register}
                error={errors.username as FieldError}
                isTouched={touchedFields.username}
              />
              <ValidatedField
                name="password"
                type="password"
                label="Contraseña"
                placeholder="Su contraseña"
                required
                data-cy="password"
                validate={{ required: 'Password cannot be empty!' }}
                register={register}
                error={errors.password as FieldError}
                isTouched={touchedFields.password}
              />
              <ValidatedField
                name="rememberMe"
                type="checkbox"
                check
                label="Iniciar la sesión automáticamente"
                value={true}
                register={register}
              />
            </Col>
          </Row>
          <div className="mt-3"></div>

          <div className="d-flex flex-column gap-2 mb-3 px-1">
            <Link
              to="/account/reset/request"
              data-cy="forgetYourPasswordSelector"
              className="text-light opacity-75 small text-decoration-underline"
              style={{ fontSize: '0.9rem' }}
            >
              ¿Ha olvidado su contraseña?
            </Link>
          </div>
        </ModalBody>

        <ModalFooter className="border-top border-secondary border-opacity-25 pt-3">
          <Button variant="secondary" onClick={handleClose} tabIndex={1} className="px-4">
            Cancelar
          </Button>{' '}
          <Button type="submit" data-cy="submit" className="px-4 fw-bold text-white" style={{ backgroundColor: '#2b7fa2', border: 'none' }}>
            Iniciar sesión
          </Button>
        </ModalFooter>
      </Form>
    </Modal>
  );
};

export default LoginModal;
