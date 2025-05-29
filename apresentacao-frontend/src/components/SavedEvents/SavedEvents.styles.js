import styled from 'styled-components';

export const SavedEvents = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

SavedEvents.Item = styled.div`
  display: flex;
  gap: 16px;
  padding: 16px;
  background: #FFFFFF;
  border-radius: 12px;
  border: 1px solid #E9ECEF;
  transition: all 0.2s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    border-color: #7B2CBF;
  }
`;

SavedEvents.Image = styled.img`
  width: 120px;
  height: 90px;
  object-fit: cover;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

SavedEvents.Content = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

SavedEvents.Title = styled.h4`
  font-size: 16px;
  font-weight: 600;
  color: #212529;
  margin: 0;
`;

SavedEvents.Info = styled.div`
  display: flex;
  gap: 16px;
  font-size: 14px;
  color: #6C757D;
`;

SavedEvents.Date = styled.span`
  display: flex;
  align-items: center;
  gap: 4px;
  
  &::before {
    content: '📅';
  }
`;

SavedEvents.Location = styled.span`
  display: flex;
  align-items: center;
  gap: 4px;
  
  &::before {
    content: '📍';
  }
`;

SavedEvents.Status = styled.span`
  align-self: flex-start;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 500;
  background: ${props => props.status === 'confirmado' ? 'rgba(123, 44, 191, 0.1)' : 'rgba(108, 117, 125, 0.1)'};
  color: ${props => props.status === 'confirmado' ? '#7B2CBF' : '#6C757D'};
`; 