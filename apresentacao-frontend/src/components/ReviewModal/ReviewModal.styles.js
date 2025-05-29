import styled from 'styled-components';

export const Modal = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
`;

Modal.Content = styled.div`
  background: white;
  border-radius: 12px;
  padding: 32px;
  width: 100%;
  max-width: 500px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
`;

Modal.Title = styled.h2`
  color: #9b3fab;
  margin-bottom: 24px;
  font-size: 24px;
`;

Modal.Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

Modal.Rating = styled.div`
  display: flex;
  gap: 8px;
  margin-bottom: 16px;
`;

Modal.Star = styled.button`
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: ${props => props.active ? '#ffd700' : '#e0e0e0'};
  transition: transform 0.2s ease;

  &:hover {
    transform: scale(1.2);
  }
`;

Modal.TextArea = styled.textarea`
  width: 100%;
  min-height: 120px;
  padding: 12px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  resize: vertical;
  font-family: inherit;
  font-size: 14px;

  &:focus {
    outline: none;
    border-color: #9b3fab;
  }
`;

Modal.Actions = styled.div`
  display: flex;
  gap: 12px;
  margin-top: 24px;
`;

Modal.Button = styled.button`
  padding: 12px 24px;
  border-radius: 24px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  border: none;
  font-size: 16px;
  flex: 1;

  &.primary {
    background: #9b3fab;
    color: white;

    &:hover {
      background: #1565c0;
    }
  }

  &.secondary {
    background: transparent;
    color: #9b3fab;
    border: 2px solid #9b3fab;

    &:hover {
      background: rgba(25, 118, 210, 0.1);
    }
  }
`; 