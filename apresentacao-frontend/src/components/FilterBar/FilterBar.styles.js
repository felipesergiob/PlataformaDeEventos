import styled from 'styled-components';

export const FilterBar = styled.div`
  display: flex;
  gap: 16px;
  margin-bottom: 24px;
  flex-wrap: wrap;
  background: #FFFFFF;
  padding: 16px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
`;

FilterBar.Filter = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  background-color: ${props => props.active ? '#7B2CBF' : '#F8F9FA'};
  color: ${props => props.active ? '#fff' : '#495057'};
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.2s ease;
  font-size: 14px;
  font-weight: 500;
  border: 1px solid ${props => props.active ? '#7B2CBF' : '#E9ECEF'};

  &:hover {
    background-color: ${props => props.active ? '#7B2CBF' : '#E9ECEF'};
    transform: translateY(-1px);
  }

  i {
    font-size: 16px;
  }
`;

FilterBar.Search = styled.div`
  flex: 1;
  position: relative;
  min-width: 200px;

  input {
    width: 100%;
    padding: 10px 16px 10px 40px;
    border: 1px solid #E9ECEF;
    border-radius: 20px;
    font-size: 14px;
    transition: all 0.2s ease;
    background: #F8F9FA;

    &:focus {
      outline: none;
      border-color: #7B2CBF;
      background: #FFFFFF;
      box-shadow: 0 0 0 3px rgba(123, 44, 191, 0.1);
    }

    &::placeholder {
      color: #ADB5BD;
    }
  }

  i {
    position: absolute;
    left: 12px;
    top: 50%;
    transform: translateY(-50%);
    color: #ADB5BD;
  }
`; 