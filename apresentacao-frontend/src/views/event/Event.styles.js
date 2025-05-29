import styled from 'styled-components';

export const Event = styled.div`
  min-height: 100vh;
  background: #f5f5f5;
`;

Event.Container = styled.div`
  max-width: 1200px;
  margin: 0 auto;
  padding: 48px 24px;
`;

Event.Header = styled.div`
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 32px;
`;

Event.Image = styled.div`
  width: 100%;
  height: 200px;
  background-image: url(${props => props.src});
  background-size: cover;
  background-position: center;
  position: relative;

  &::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100px;
    background: linear-gradient(to top, rgba(0,0,0,0.7), transparent);
  }
`;

Event.Content = styled.div`
  padding: 32px;
`;

Event.Title = styled.h1`
  font-size: 32px;
  color: #9b3fab;
  margin-bottom: 16px;
`;

Event.Info = styled.div`
  display: flex;
  gap: 32px;
  margin-bottom: 24px;
  flex-wrap: wrap;
`;

Event.InfoItem = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  color: #666;

  i {
    color: #9b3fab;
    font-size: 20px;
  }
`;

Event.Description = styled.p`
  color: #666;
  line-height: 1.6;
  margin-bottom: 32px;
`;

Event.Actions = styled.div`
  display: flex;
  gap: 16px;
  margin-top: 32px;
`;

Event.Button = styled.button`
  padding: 12px 32px;
  border-radius: 24px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  border: none;
  font-size: 16px;

  &.primary {
    background: #9b3fab;
    color: white;

    &:hover {
      background: #1565c0;
      transform: translateY(-2px);
    }
  }

  &.secondary {
    background: transparent;
    color: #9b3fab;
    border: 2px solid #9b3fab;

    &:hover {
      background: rgba(25, 118, 210, 0.1);
      transform: translateY(-2px);
    }
  }
`; 