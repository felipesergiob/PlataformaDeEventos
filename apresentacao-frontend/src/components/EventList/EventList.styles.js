import styled from 'styled-components';

export const EventList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
  width: 100%;
`;

EventList.EmptyMessage = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 200px;
  background-color: #FFFFFF;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  font-size: 18px;
  color: #495057;
  border: 1px solid #E9ECEF;
`;

EventList.Item = styled.div`
  display: flex;
  gap: 24px;
  padding: 24px;
  border-radius: 12px;
  background-color: #FFFFFF;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  transition: all 0.2s ease-in-out;
  cursor: pointer;
  border: 1px solid #E9ECEF;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    border-color: #7B2CBF;
  }
`;

EventList.Image = styled.img`
  width: 200px;
  height: 150px;
  object-fit: cover;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

EventList.Content = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 12px;
`;

EventList.Title = styled.h3`
  font-size: 20px;
  font-weight: 600;
  color: #212529;
  margin: 0;
  letter-spacing: -0.5px;
`;

EventList.Description = styled.p`
  font-size: 14px;
  color: #495057;
  margin: 0;
  line-height: 1.6;
`;

EventList.Info = styled.div`
  display: flex;
  gap: 16px;
  margin-top: auto;
  font-size: 14px;
  color: #6C757D;
`;

EventList.Date = styled.span`
  display: flex;
  align-items: center;
  gap: 4px;
  
  &::before {
    content: '📅';
  }
`;

EventList.Location = styled.span`
  display: flex;
  align-items: center;
  gap: 4px;
  
  &::before {
    content: '📍';
  }
`;

EventList.Price = styled.span`
  display: flex;
  align-items: center;
  gap: 4px;
  font-weight: 600;
  color: #9b3fab;
  
  &::before {
    content: '💰';
  }
`; 