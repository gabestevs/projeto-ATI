import React from "react";

function DeleteConfirmationModal({ isOpen, onClose, onConfirm, clientName }) {
  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2 className="modal-title">Confirmar Exclusão</h2>
        <p>Deseja realmente excluir o cliente "{clientName}"?</p>
        <div className="modal-buttons">
          <button type="button" className="btn btn-secondary" onClick={onClose}>
            Cancelar
          </button>
          <button type="button" className="btn btn-primary" onClick={onConfirm}>
            Excluir
          </button>
        </div>
      </div>
    </div>
  );
}

export default DeleteConfirmationModal;
