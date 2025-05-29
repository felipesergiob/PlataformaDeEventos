import React, { useState } from 'react';
import { Modal as StyledModal } from './ReviewModal.styles';

function ReviewModal({ isOpen, onClose, onSubmit, eventTitle }) {
  const [rating, setRating] = useState(0);
  const [comment, setComment] = useState('');

  if (!isOpen) return null;

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({ rating, comment });
    setRating(0);
    setComment('');
    onClose();
  };

  return (
    <StyledModal>
      <StyledModal.Content>
        <StyledModal.Title>
          Avaliar: {eventTitle}
        </StyledModal.Title>

        <StyledModal.Form onSubmit={handleSubmit}>
          <StyledModal.Rating>
            {[1, 2, 3, 4, 5].map((star) => (
              <StyledModal.Star
                key={star}
                type="button"
                active={star <= rating}
                onClick={() => setRating(star)}
              >
                ★
              </StyledModal.Star>
            ))}
          </StyledModal.Rating>

          <StyledModal.TextArea
            placeholder="Escreva seu comentário sobre o evento..."
            value={comment}
            onChange={(e) => setComment(e.target.value)}
          />

          <StyledModal.Actions>
            <StyledModal.Button 
              type="button" 
              className="secondary"
              onClick={onClose}
            >
              Cancelar
            </StyledModal.Button>
            <StyledModal.Button 
              type="submit" 
              className="primary"
              disabled={rating === 0}
            >
              Enviar Avaliação
            </StyledModal.Button>
          </StyledModal.Actions>
        </StyledModal.Form>
      </StyledModal.Content>
    </StyledModal>
  );
}

export default ReviewModal; 