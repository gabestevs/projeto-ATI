import React, { useState, useEffect } from "react";
import ClientTable from "./components/ClientTable";
import DarkModeToggle from "./components/DarkModeToggle";
import * as api from "./services/api";

function App() {
  const [clients, setClients] = useState([]);
  const [isDarkMode, setIsDarkMode] = useState(false);
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    fetchClients();
    const savedMode = localStorage.getItem("darkMode") === "true";
    setIsDarkMode(savedMode);
    document.documentElement.setAttribute(
      "data-theme",
      savedMode ? "dark" : "light"
    );
  }, []);

  useEffect(() => {
    localStorage.setItem("darkMode", isDarkMode);
    document.documentElement.setAttribute(
      "data-theme",
      isDarkMode ? "dark" : "light"
    );
  }, [isDarkMode]);

  const fetchClients = async () => {
    try {
      setIsLoading(true);
      const response = await api.getClients();
      setClients(response.data);
      setError(null);
    } catch (error) {
      setError("Não foi possível carregar os clientes.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="container">
      <header className="header">
        <h1 className="header-title">Sistema de Suporte</h1>
        <div className="header-buttons">
          <button
            className="btn btn-primary"
            onClick={() => document.querySelector("#add-client-button").click()}
            aria-label="Adicionar cliente"
          >
            <svg
              className="btn-icon"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M12 4v16m8-8H4"
              />
            </svg>
            Adicionar Cliente
          </button>
          <DarkModeToggle
            isDarkMode={isDarkMode}
            setIsDarkMode={setIsDarkMode}
          />
        </div>
      </header>
      <main>
        {isLoading && (
          <div className="alert alert-loading">Carregando clientes...</div>
        )}
        {error && <div className="alert alert-error">{error}</div>}
        {!isLoading && clients.length === 0 && !error && (
          <div className="alert alert-empty">Nenhum cliente encontrado.</div>
        )}
        <ClientTable clients={clients} fetchClients={fetchClients} />
      </main>
    </div>
  );
}

export default App;
