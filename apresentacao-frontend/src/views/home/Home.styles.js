import styled from 'styled-components';
import { Container } from '@mui/material';

export const Home = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;
  max-width: 100vw;
  min-height: calc(100vh - 64px); /* Altura total da viewport menos a altura da navbar */
  background: linear-gradient(180deg, #F8F9FA 0%, #FFFFFF 100%);
  box-shadow: 0px 0px 16px 0px rgba(0, 0, 0, 0.04);

  @media screen and (max-width: 601px) {
    margin: 0 auto;
    border-radius: 0;
  }
`;

Home.Container = styled(Container)`
  padding: 32px 24px;
  min-height: 100%;
`;

Home.Header = styled.header`
  display: flex;
  flex-direction: column;
  margin-bottom: 32px;
`;

Home.Title = styled.h1`
  font-weight: 600;
  font-size: 28px;
  line-height: 36px;
  color: #212529;
  margin-bottom: 24px;
  letter-spacing: -0.5px;
`;

Home.Content = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
  min-height: calc(100vh - 200px); /* Altura mínima para o conteúdo */
`; 