import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Event as StyledEvent } from './Event.styles';
import ReviewModal from '../../components/ReviewModal';

function Event({ event }) {
  const navigate = useNavigate();
  const [isReviewModalOpen, setIsReviewModalOpen] = useState(false);

  if (!event) {
    return null;
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

  const handleReviewSubmit = (review) => {
    console.log('Avaliação enviada:', review);
  };

  return (
    <StyledEvent>
      <StyledEvent.Container>
        <StyledEvent.Header>
          <StyledEvent.Image src={event.image} />
          <StyledEvent.Content>
            <StyledEvent.Title>{event.title}</StyledEvent.Title>
            
            <StyledEvent.Info>
              <StyledEvent.InfoItem>
                <i>📅</i>
                {formatDate(event.date)}
              </StyledEvent.InfoItem>
              <StyledEvent.InfoItem>
                <i>📍</i>
                {event.location}
              </StyledEvent.InfoItem>
              <StyledEvent.InfoItem>
                <i>💰</i>
                {event.price}
              </StyledEvent.InfoItem>
              <StyledEvent.InfoItem>
                {event.category === 'music' ? 'Música' :
                 event.category === 'theater' ? 'Teatro' :
                 event.category === 'art' ? 'Arte' :
                 event.category === 'sports' ? 'Esportes' : 'Outros'}
              </StyledEvent.InfoItem>
            </StyledEvent.Info>

            <StyledEvent.Description>
              {event.description}
            </StyledEvent.Description>

            <StyledEvent.Actions>
              <StyledEvent.Button 
                className="primary"
                onClick={() => setIsReviewModalOpen(true)}
              >
                Avaliar Evento
              </StyledEvent.Button>
              <StyledEvent.Button 
                className="secondary"
                onClick={() => navigate('/eventos')}
              >
                Voltar
              </StyledEvent.Button>
            </StyledEvent.Actions>
          </StyledEvent.Content>
        </StyledEvent.Header>
      </StyledEvent.Container>

      <ReviewModal
        isOpen={isReviewModalOpen}
        onClose={() => setIsReviewModalOpen(false)}
        onSubmit={handleReviewSubmit}
        eventTitle={event.title}
      />
    </StyledEvent>
  );
}

export default Event; 