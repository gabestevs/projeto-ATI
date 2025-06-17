import axios from "axios";
import { API_BASE_URL } from "../constants";

const api = axios.create({
  baseURL: API_BASE_URL,
});

export const getClients = () => api.get("/clients");
export const getClient = (id) => api.get(`/clients/${id}`);
export const createClient = (client) => api.post("/clients", client);
export const updateClient = (id, client) => api.put(`/clients/${id}`, client);
export const deleteClient = (id) => api.delete(`/clients/${id}`);
export const getTicketsByClientId = (clientId) =>
  api.get(`/tickets/client/${clientId}`);
export const createTicket = (clientId, ticket) =>
  api.post(`/tickets/${clientId}`, ticket);
