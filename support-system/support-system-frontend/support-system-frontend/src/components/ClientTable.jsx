import React, { useState } from 'react';
import ClientModal from './ClientModal';
import DeleteConfirmationModal from './DeleteConfirmationModal';
import TicketModal from './TicketModal';
import TicketList from './TicketList';
import * as api from '../services/api';

function ClientTable({ clients, fetchClients }) {
  const [isClientModalOpen, setIsClientModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [isTicketModalOpen, setIsTicketModalOpen] = useState(false);
  const [selectedClient, setSelectedClient] = useState(null);
  const [modalMode, setModalMode] = useState('create');
  const [expandedRow, setExpandedRow] = useState(null);

  const openClientModal = (client = null, mode = 'create') => {
    setSelectedClient(client);
    setModalMode(mode);
    setIsClientModalOpen(true);
  };

  const openDeleteModal = (client) => {
    setSelectedClient(client);
    setIsDeleteModalOpen(true);
  };

  const openTicketModal = (client) => {
    setSelectedClient(client);
    setIsTicketModalOpen(true);
  };

  const handleDelete = async () => {
    try {
      await api.deleteClient(selectedClient.id);
      fetchClients();
      setIsDeleteModalOpen(false);
    } catch (error) {
      console.error('Erro ao deletar cliente:', error);
    }
  };

  const toggleRow = (clientId) => {
    setExpandedRow(expandedRow === clientId ? null : clientId);
  };

  return (
    <>
      <button
        id="add-client-button"
        onClick={() => openClientModal()}
        style={{ display: 'none' }}
        aria-hidden="true"
      />
      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nome</th>
            <th style={{ textAlign: 'right' }}>Ações</th>
          </tr>
        </thead>
        <tbody>
          {clients.map((client) => (
            <React.Fragment key={client.id}>
              <tr
                onClick={() => toggleRow(client.id)}
                role="button"
                tabIndex={0}
                onKeyDown={(e) => e.key === 'Enter' && toggleRow(client.id)}
                aria-expanded={expandedRow === client.id}
              >
                <td>{client.id}</td>
                <td>{client.name}</td>
                <td className="table-actions">
                  <button
                    className="btn-action"
                    onClick={(e) => {
                      e.stopPropagation();
                      openClientModal(client, 'edit');
                    }}
                    aria-label={`Editar ${client.name}`}
                  >
                    <svg className="btn-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />
                    </svg>
                  </button>
                  <button
                    className="btn-action"
                    onClick={(e) => {
                      e.stopPropagation();
                      openTicketModal(client);
                    }}
                    aria-label={`Adicionar ticket para ${client.name}`}
                  >
                    <svg className="btn-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 4v16m8-8H4" />
                    </svg>
                  </button>
                  <button
                    className="btn-action delete"
                    onClick={(e) => {
                      e.stopPropagation();
                      openDeleteModal(client);
                    }}
                    aria-label={`Deletar ${client.name}`}
                  >
                    <svg className="btn-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                    </svg>
                  </button>
                </td>
              </tr>
              {expandedRow === client.id && (
                <tr>
                  <td colSpan="3" className="ticket-list">
                    <TicketList clientId={client.id} />
                  </td>
                </tr>
              )}
            </React.Fragment>
          ))}
        </tbody>
      </table>
      <ClientModal
        isOpen={isClientModalOpen}
        onClose={() => setIsClientModalOpen(false)}
        client={selectedClient}
        mode={modalMode}
        fetchClients={fetchClients}
      />
      <DeleteConfirmationModal
        isOpen={isDeleteModalOpen}
        onClose={() => setIsDeleteModalOpen(false)}
        onConfirm={handleDelete}
        clientName={selectedClient?.name}
      />
      <TicketModal
        isOpen={isTicketModalOpen}
        onClose={() => setIsTicketModalOpen(false)}
        client={selectedClient}
        fetchClients={fetchClients}
      />
    </>
  );
}

export default ClientTable;