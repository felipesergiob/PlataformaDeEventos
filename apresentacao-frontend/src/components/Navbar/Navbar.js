import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { Navbar as StyledNavbar } from './Navbar.styles';

function Navbar() {
  const location = useLocation();

  return (
    <StyledNavbar>
      <StyledNavbar.Container>
        <Link to="/" style={{ textDecoration: 'none' }}>
          <StyledNavbar.Logo>
            SeLigaAí
          </StyledNavbar.Logo>
        </Link>

        <StyledNavbar.Menu>
          <StyledNavbar.MenuItem 
            as={Link} 
            to="/" 
            className={location.pathname === '/' ? 'active' : ''}
          >
            Início
          </StyledNavbar.MenuItem>
  
          <StyledNavbar.MenuItem 
            as={Link} 
            to="/perfil" 
            className={location.pathname === '/perfil' ? 'active' : ''}
          >
            Meu Perfil
          </StyledNavbar.MenuItem>
          
          <StyledNavbar.Button>
            Criar Evento
          </StyledNavbar.Button>
        </StyledNavbar.Menu>
      </StyledNavbar.Container>
    </StyledNavbar>
  );
}

export default Navbar; 