import React from 'react';
import { SavedEvents as StyledSavedEvents } from './SavedEvents.styles';

function SavedEvents({ events }) {
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  return (
    <StyledSavedEvents>
      {events.map(event => (
        <StyledSavedEvents.Item key={event.id}>
          <StyledSavedEvents.Image src={event.image} alt={event.title} />
          <StyledSavedEvents.Content>
            <StyledSavedEvents.Title>{event.title}</StyledSavedEvents.Title>
            <StyledSavedEvents.Info>
              <StyledSavedEvents.Date>
                {formatDate(event.date)}
              </StyledSavedEvents.Date>
              <StyledSavedEvents.Location>
                {event.location}
              </StyledSavedEvents.Location>
            </StyledSavedEvents.Info>
            <StyledSavedEvents.Status status={event.status}>
              {event.status === 'confirmado' ? 'Confirmado' : 'Salvo'}
            </StyledSavedEvents.Status>
          </StyledSavedEvents.Content>
        </StyledSavedEvents.Item>
      ))}
    </StyledSavedEvents>
  );
}

export default SavedEvents; 