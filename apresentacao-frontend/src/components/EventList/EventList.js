import React from 'react';
import { useNavigate } from 'react-router-dom';
import { EventList as StyledEventList } from './EventList.styles';

function EventList({ events }) {
  const navigate = useNavigate();

  if (!events || events.length === 0) {
    return (
      <StyledEventList>
        <StyledEventList.EmptyMessage>
          Nenhum evento encontrado
        </StyledEventList.EmptyMessage>
      </StyledEventList>
    );
  }

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  return (
    <StyledEventList>
      {events.map((event) => (
        <StyledEventList.Item 
          key={event.id}
          onClick={() => navigate(`/eventos/${event.id}`)}
        >
          <StyledEventList.Image src={event.image} alt={event.title} />
          <StyledEventList.Content>
            <StyledEventList.Title>{event.title}</StyledEventList.Title>
            <StyledEventList.Description>{event.description}</StyledEventList.Description>
            <StyledEventList.Info>
              <StyledEventList.Date>
                {formatDate(event.date)}
              </StyledEventList.Date>
              <StyledEventList.Location>
                {event.location}
              </StyledEventList.Location>
              <StyledEventList.Price>
                {event.price}
              </StyledEventList.Price>
            </StyledEventList.Info>
          </StyledEventList.Content>
        </StyledEventList.Item>
      ))}
    </StyledEventList>
  );
}

export default EventList; 