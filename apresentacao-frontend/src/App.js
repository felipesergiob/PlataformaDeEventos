import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { CssBaseline, ThemeProvider, createTheme } from '@mui/material';
import { Navbar } from './components';
import Home from './views/home';
import Event from './views/event';
import Profile from './views/profile';

const theme = createTheme({
  palette: {
    primary: {
      main: '#7B2CBF',
      light: '#9D4EDD',
      dark: '#5A189A',
    },
    secondary: {
      main: '#FF6B6B',
      light: '#FF8E8E',
      dark: '#E63946',
    },
    background: {
      default: '#F8F9FA',
      paper: '#FFFFFF',
    },
  },
  typography: {
    fontFamily: '"Inter", "Roboto", "Helvetica", "Arial", sans-serif',
    h1: {
      fontWeight: 600,
    },
    h2: {
      fontWeight: 600,
    },
    h3: {
      fontWeight: 600,
    },
  },
  shape: {
    borderRadius: 12,
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
          fontWeight: 500,
        },
      },
    },
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/eventos" element={<Home />} />
          <Route path="/eventos/:id" element={<Event />} />
          <Route path="/perfil" element={<Profile />} />
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

export default App;
