import React, { useState, useEffect } from "react";
import * as api from "../services/api";

function ClientModal({ isOpen, onClose, client, mode, fetchClients }) {
  const [form, setForm] = useState({ name: "", email: "" });
  const [error, setError] = useState("");

  useEffect(() => {
    if (client && mode === "edit") {
      setForm({ name: client.name, email: client.email });
    } else {
      setForm({ name: "", email: "" });
    }
  }, [client, mode]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.name.trim() || !form.email.trim()) {
      setError("Nome e email são obrigatórios");
      return;
    }
    try {
      if (mode === "create") {
        await api.createClient(form);
      } else {
        await api.updateClient(client.id, form);
      }
      fetchClients();
      onClose();
      setError("");
    } catch (error) {
      setError("Erro ao salvar cliente");
    }
  };

  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2 className="modal-title">
          {mode === "create" ? "Adicionar Cliente" : "Editar Cliente"}
        </h2>
        <form className="modal-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label" htmlFor="name">
              Nome
            </label>
            <input
              id="name"
              name="name"
              type="text"
              className="form-input"
              value={form.name}
              onChange={handleChange}
              required
            />
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
              {mode === "create" ? "Criar" : "Salvar"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default ClientModal;
