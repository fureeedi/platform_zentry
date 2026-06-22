import React, { useEffect, useState } from 'react';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'react-bootstrap';
import { useLocation, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { deleteEntity, getEntity } from './administrador-conjunto.reducer';

export const AdministradorConjuntoDeleteDialog = () => {
  const dispatch = useAppDispatch();
  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const administradorConjuntoEntity = useAppSelector(state => state.administradorConjunto.entity);
  const updateSuccess = useAppSelector(state => state.administradorConjunto.updateSuccess);

  const handleClose = () => {
    navigate(`/administrador-conjunto${pageLocation.search}`);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(administradorConjuntoEntity.id));
  };

  return (
    <Modal show onHide={handleClose}>
      <ModalHeader data-cy="administradorConjuntoDeleteDialogHeading" closeButton>
        Confirmar operación de borrado
      </ModalHeader>
      <ModalBody id="zentryApp.administradorConjunto.delete.question">
        ¿Seguro que quiere eliminar Administrador Conjunto {administradorConjuntoEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button variant="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancelar
        </Button>
        <Button
          id="project-confirm-delete-administradorConjunto"
          data-cy="entityConfirmDeleteButton"
          variant="danger"
          onClick={confirmDelete}
        >
          <FontAwesomeIcon icon="trash" />
          &nbsp; Eliminar
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default AdministradorConjuntoDeleteDialog;
