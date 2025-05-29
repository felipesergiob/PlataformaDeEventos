import styled from 'styled-components';

export const EventCalendar = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

EventCalendar.Header = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 16px;
`;

EventCalendar.Title = styled.h3`
  font-size: 18px;
  font-weight: 600;
  color: #212529;
  text-transform: capitalize;
`;

EventCalendar.ArrowButton = styled.button`
  background: none;
  border: none;
  font-size: 20px;
  color: #7B2CBF;
  cursor: pointer;
  padding: 8px;
  border-radius: 50%;
  transition: all 0.2s ease;

  &:hover {
    background: rgba(123, 44, 191, 0.1);
  }
`;

EventCalendar.WeekDays = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 8px;
  text-align: center;
  font-weight: 500;
  color: #6C757D;
  font-size: 14px;
  margin-bottom: 8px;
`;

EventCalendar.Days = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 8px;
`;

EventCalendar.Day = styled.div`
  aspect-ratio: 1;
  padding: 8px;
  border-radius: 8px;
  background: ${props => props.hasEvents ? 'rgba(123, 44, 191, 0.1)' : 'transparent'};
  border: 1px solid ${props => props.hasEvents ? '#7B2CBF' : '#E9ECEF'};
  cursor: pointer;
  transition: all 0.2s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }
`;

EventCalendar.EmptyDay = styled.div`
  aspect-ratio: 1;
`;

EventCalendar.DayNumber = styled.div`
  font-size: 14px;
  font-weight: 500;
  color: #212529;
  margin-bottom: 4px;
`;

EventCalendar.EventIndicator = styled.div`
  font-size: 12px;
  color: #7B2CBF;
  font-weight: 500;
`; 