import styled, { keyframes } from 'styled-components';

const spin = keyframes`
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
`;

export const Loading = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 400px;
`;

Loading.Spinner = styled.div`
  width: 50px;
  height: 50px;
  border: 4px solid #f3f3f3;
  border-top: 4px solid #9b3fab;
  border-radius: 50%;
  animation: ${spin} 1s linear infinite;
`;

Loading.Text = styled.div`
  font-size: 18px;
  color: #9b3fab;
  font-weight: 500;
`;

Loading.Cards = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 24px;
  width: 100%;
  padding: 24px;
`;

Loading.Card = styled.div`
  background: #fff;
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
`;

Loading.CardImage = styled.div`
  width: 100%;
  height: 200px;
  background: #f0f0f0;
  border-radius: 8px;
  margin-bottom: 16px;
`;

Loading.CardTitle = styled.div`
  width: 80%;
  height: 24px;
  background: #f0f0f0;
  border-radius: 4px;
  margin-bottom: 12px;
`;

Loading.CardDescription = styled.div`
  width: 100%;
  height: 16px;
  background: #f0f0f0;
  border-radius: 4px;
  margin-bottom: 8px;
`;

Loading.CardInfo = styled.div`
  display: flex;
  gap: 16px;
  margin-top: 16px;
`;

Loading.CardInfoItem = styled.div`
  width: 60px;
  height: 16px;
  background: #f0f0f0;
  border-radius: 4px;
`; 