import React, { useEffect, useState } from 'react';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'react-bootstrap';
import { useLocation, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { deleteEntity, getEntity } from './vinculado-inmueble.reducer';

export const VinculadoInmuebleDeleteDialog = () => {
  const dispatch = useAppDispatch();
  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const vinculadoInmuebleEntity = useAppSelector(state => state.vinculadoInmueble.entity);
  const updateSuccess = useAppSelector(state => state.vinculadoInmueble.updateSuccess);

  const handleClose = () => {
    navigate(`/vinculado-inmueble${pageLocation.search}`);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(vinculadoInmuebleEntity.id));
  };

  return (
    <Modal show onHide={handleClose}>
      <ModalHeader data-cy="vinculadoInmuebleDeleteDialogHeading" closeButton>
        Confirmar operación de borrado
      </ModalHeader>
      <ModalBody id="zentryApp.vinculadoInmueble.delete.question">
        ¿Seguro que quiere eliminar Vinculado Inmueble {vinculadoInmuebleEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button variant="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancelar
        </Button>
        <Button id="project-confirm-delete-vinculadoInmueble" data-cy="entityConfirmDeleteButton" variant="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Eliminar
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default VinculadoInmuebleDeleteDialog;
