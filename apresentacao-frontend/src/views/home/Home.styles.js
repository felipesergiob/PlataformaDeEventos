import styled from 'styled-components';

export const Home = styled.div`
  min-height: 100vh;
  background: #F8F9FA;
  padding: 24px;
`;

Home.Container = styled.div`
  max-width: ${props => props.maxWidth || '1200px'};
  margin: 0 auto;
  display: grid;
  grid-template-columns: 1fr 300px;
  gap: 24px;
`;

Home.Header = styled.div`
  grid-column: 1 / -1;
`;

Home.Title = styled.h1`
  font-size: 32px;
  font-weight: 700;
  color: #212529;
  margin-bottom: 24px;
`;

Home.Content = styled.div`
  grid-column: 1;
`;

Home.Filters = styled.div`
  grid-column: 2;
  position: sticky;
  top: 24px;
  height: fit-content;
  background: #FFFFFF;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  padding: 24px;
`; 