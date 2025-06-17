import React from 'react';

function DarkModeToggle({ isDarkMode, setIsDarkMode }) {
  return (
    <button
      className="btn btn-primary"
      onClick={() => setIsDarkMode(!isDarkMode)}
      aria-label={isDarkMode ? 'Desativar modo escuro' : 'Ativar modo escuro'}
    >
      <svg className="btn-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path
          strokeLinecap="round"
          strokeLinejoin="round"
          strokeWidth="2"
          d={isDarkMode ? 'M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z' : 'M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z'}
        />
      </svg>
      {isDarkMode ? 'Modo Claro' : 'Modo Escuro'}
    </button>
  );
}

export default DarkModeToggle;
