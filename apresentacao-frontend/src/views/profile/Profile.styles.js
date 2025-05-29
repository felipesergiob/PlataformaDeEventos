import styled from 'styled-components';
import { Container } from '@mui/material';

export const Profile = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;
  max-width: 100vw;
  min-height: calc(100vh - 64px);
  background: linear-gradient(180deg, #F8F9FA 0%, #FFFFFF 100%);
  box-shadow: 0px 0px 16px 0px rgba(0, 0, 0, 0.04);
`;

Profile.Container = styled(Container)`
  padding: 32px 24px;
  min-height: 100%;
`;

Profile.Header = styled.header`
  display: flex;
  flex-direction: column;
  margin-bottom: 32px;
`;

Profile.Title = styled.h1`
  font-weight: 600;
  font-size: 28px;
  line-height: 36px;
  color: #212529;
  margin-bottom: 24px;
  letter-spacing: -0.5px;
`;

Profile.Content = styled.div`
  display: grid;
  grid-template-columns: 1fr;
  gap: 32px;

  @media (min-width: 1024px) {
    grid-template-columns: 1fr 1fr;
  }
`;

Profile.Section = styled.section`
  background: #FFFFFF;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  border: 1px solid #E9ECEF;
`;

Profile.SectionTitle = styled.h2`
  font-weight: 600;
  font-size: 20px;
  color: #212529;
  margin-bottom: 24px;
  letter-spacing: -0.5px;
`; 