import React, { useState } from "react";
import * as api from "../services/api";

function TicketModal({ isOpen, onClose, client, fetchClients }) {
  const [ticket, setTicket] = useState({
    category: "",
    content: "",
    status: "Open",
  });
  const [error, setError] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setTicket((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!ticket.category.trim() || !ticket.content.trim()) {
      setError("Categoria e conteúdo são obrigatórios");
      return;
    }
    try {
      await api.createTicket(client.id, ticket);
      fetchClients();
      onClose();
      setTicket({ category: "", content: "", status: "Open" });
      setError("");
    } catch (error) {
      setError("Erro ao criar ticket");
    }
  };

  if (!isOpen || !client) return null;

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2 className="modal-title">Adicionar Ticket para {client.name}</h2>
        <form className="modal-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label" htmlFor="category">
              Categoria
            </label>
            <input
              id="category"
              name="category"
              type="text"
              className="form-input"
              value={ticket.category}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label className="form-label" htmlFor="content">
              Conteúdo
            </label>
            <textarea
              id="content"
              name="content"
              className="form-textarea"
              value={ticket.content}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label className="form-label" htmlFor="status">
              Status
            </label>
            <select
              id="status"
              name="status"
              className="form-select"
              value={ticket.status}
              onChange={handleChange}
            >
              <option value="Open">Aberto</option>
              <option value="In Progress">Em Progresso</option>
              <option value="Closed">Fechado</option>
            </select>
          </div>
          {error && <p className="form-error">{error}</p>}
          <div className="modal-buttons">
            <button
              type="button"
              className="btn btn-secondary"
              onClick={onClose}
            >
              Cancelar
            </button>
            <button type="submit" className="btn btn-primary">
              Criar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default TicketModal;
