import styled from 'styled-components';

export const Navbar = styled.nav`
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  padding: 16px 0;
  position: sticky;
  top: 0;
  z-index: 1000;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
`;

Navbar.Container = styled.div`
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

Navbar.Logo = styled.div`
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 24px;
  font-weight: 700;
  color: #7B2CBF;
  text-decoration: none;
  transition: all 0.2s ease;

  &:hover {
    transform: scale(1.05);
    color: #9D4EDD;
  }

  i {
    font-size: 28px;
  }
`;

Navbar.Menu = styled.div`
  display: flex;
  align-items: center;
  gap: 24px;
`;

Navbar.MenuItem = styled.a`
  color: #495057;
  text-decoration: none;
  font-weight: 500;
  padding: 8px 16px;
  border-radius: 20px;
  transition: all 0.2s ease;

  &:hover {
    color: #7B2CBF;
    background: rgba(123, 44, 191, 0.1);
  }

  &.active {
    color: #7B2CBF;
    background: rgba(123, 44, 191, 0.1);
  }
`;

Navbar.Button = styled.button`
  background: #7B2CBF;
  color: white;
  border: none;
  padding: 10px 24px;
  border-radius: 20px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  box-shadow: 0 2px 8px rgba(123, 44, 191, 0.25);

  &:hover {
    background: #9D4EDD;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(123, 44, 191, 0.35);
  }
`; 