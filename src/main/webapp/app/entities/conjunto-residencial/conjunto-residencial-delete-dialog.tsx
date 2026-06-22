import React, { useEffect, useState } from 'react';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'react-bootstrap';
import { useLocation, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { deleteEntity, getEntity } from './conjunto-residencial.reducer';

export const ConjuntoResidencialDeleteDialog = () => {
  const dispatch = useAppDispatch();
  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const conjuntoResidencialEntity = useAppSelector(state => state.conjuntoResidencial.entity);
  const updateSuccess = useAppSelector(state => state.conjuntoResidencial.updateSuccess);

  const handleClose = () => {
    navigate(`/conjunto-residencial${pageLocation.search}`);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(conjuntoResidencialEntity.id));
  };

  return (
    <Modal show onHide={handleClose}>
      <ModalHeader data-cy="conjuntoResidencialDeleteDialogHeading" closeButton>
        Confirmar operación de borrado
      </ModalHeader>
      <ModalBody id="zentryApp.conjuntoResidencial.delete.question">
        ¿Seguro que quiere eliminar Conjunto Residencial {conjuntoResidencialEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button variant="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancelar
        </Button>
        <Button
          id="project-confirm-delete-conjuntoResidencial"
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

export default ConjuntoResidencialDeleteDialog;
