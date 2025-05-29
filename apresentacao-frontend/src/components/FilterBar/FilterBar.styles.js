import styled from 'styled-components';

export const FilterBar = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

FilterBar.Search = styled.div`
  position: relative;
  width: 100%;

  input {
    width: 100%;
    padding: 10px 16px 10px 40px;
    border: 1px solid #E9ECEF;
    border-radius: 8px;
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

FilterBar.Section = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

FilterBar.SectionTitle = styled.h3`
  font-size: 14px;
  font-weight: 600;
  color: #495057;
  margin: 0;
`;

FilterBar.CategoryList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4px;
`;

FilterBar.Category = styled.button`
  display: flex;
  align-items: center;
  padding: 8px 12px;
  background-color: ${props => props.active ? '#7B2CBF' : '#F8F9FA'};
  color: ${props => props.active ? '#fff' : '#495057'};
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s ease;
  font-size: 14px;
  font-weight: 500;
  border: 1px solid ${props => props.active ? '#7B2CBF' : '#E9ECEF'};
  text-align: left;

  &:hover {
    background-color: ${props => props.active ? '#7B2CBF' : '#E9ECEF'};
  }
`;

FilterBar.RangeContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 12px;
  padding: 4px 0;
  position: relative;
`;

FilterBar.RangeInput = styled.input`
  width: 100%;
  height: 4px;
  -webkit-appearance: none;
  background: #E9ECEF;
  border-radius: 2px;
  outline: none;
  transition: all 0.2s ease;
  cursor: pointer;
  position: relative;
  z-index: 1;

  &::-webkit-slider-thumb {
    -webkit-appearance: none;
    width: 20px;
    height: 20px;
    background: #7B2CBF;
    border-radius: 50%;
    cursor: pointer;
    transition: all 0.2s ease;
    border: 2px solid #FFFFFF;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    z-index: 2;

    &:hover {
      transform: scale(1.1);
      background: #9B4DDB;
    }

    &:active {
      transform: scale(0.95);
    }
  }

  &::-moz-range-thumb {
    width: 20px;
    height: 20px;
    background: #7B2CBF;
    border-radius: 50%;
    cursor: pointer;
    transition: all 0.2s ease;
    border: 2px solid #FFFFFF;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    z-index: 2;

    &:hover {
      transform: scale(1.1);
      background: #9B4DDB;
    }

    &:active {
      transform: scale(0.95);
    }
  }

  &:focus {
    &::-webkit-slider-thumb {
      box-shadow: 0 0 0 3px rgba(123, 44, 191, 0.2);
    }
    &::-moz-range-thumb {
      box-shadow: 0 0 0 3px rgba(123, 44, 191, 0.2);
    }
  }
`;

FilterBar.RangeLabels = styled.div`
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #495057;
  font-weight: 500;
  margin-top: 4px;
`;

FilterBar.DateRangeContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

FilterBar.DateInput = styled.input`
  padding: 10px 12px;
  border: 1px solid #E9ECEF;
  border-radius: 8px;
  font-size: 14px;
  color: #495057;
  background: #F8F9FA;
  cursor: pointer;
  transition: all 0.2s ease;
  width: 100%;

  &:focus {
    outline: none;
    border-color: #7B2CBF;
    background: #FFFFFF;
    box-shadow: 0 0 0 3px rgba(123, 44, 191, 0.1);
  }

  &::-webkit-calendar-picker-indicator {
    cursor: pointer;
    filter: invert(0.5);
    padding: 2px;
    transition: all 0.2s ease;

    &:hover {
      filter: invert(0.3);
    }
  }

  &::-webkit-datetime-edit {
    padding: 0;
  }

  &::-webkit-datetime-edit-fields-wrapper {
    padding: 0;
  }

  &::-webkit-datetime-edit-text {
    padding: 0 2px;
  }

  &::-webkit-datetime-edit-year-field,
  &::-webkit-datetime-edit-month-field,
  &::-webkit-datetime-edit-day-field {
    padding: 0 2px;
  }

  &:disabled {
    background: #E9ECEF;
    cursor: not-allowed;
    opacity: 0.7;
  }
`; 