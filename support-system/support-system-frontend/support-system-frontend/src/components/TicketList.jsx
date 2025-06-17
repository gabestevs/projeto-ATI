import React, { useState, useEffect } from "react";
import * as api from "../services/api";

function TicketList({ clientId }) {
  const [tickets, setTickets] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchTickets = async () => {
      try {
        const response = await api.getTicketsByClientId(clientId);
        setTickets(response.data);
        setError("");
      } catch (error) {
        setError("Erro ao carregar tickets");
      } finally {
        setIsLoading(false);
      }
    };
    fetchTickets();
  }, [clientId]);

  if (isLoading)
    return <div className="alert alert-loading">Carregando tickets...</div>;
  if (error) return <div className="alert alert-error">{error}</div>;
  if (tickets.length === 0)
    return <div className="alert alert-empty">Nenhum ticket encontrado.</div>;

  return (
    <div className="ticket-list">
      {tickets.map((ticket) => (
        <div key={ticket.id} className="ticket-item">
          <span>
            {ticket.category} - {ticket.content}
          </span>
          <span>{ticket.status}</span>
        </div>
      ))}
    </div>
  );
}

export default TicketList;
