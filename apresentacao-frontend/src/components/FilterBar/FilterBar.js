import React from 'react';
import { FilterBar as StyledFilterBar } from './FilterBar.styles';

const CATEGORIES = [
  { id: 'all', label: 'Todos' },
  { id: 'music', label: 'Música' },
  { id: 'theater', label: 'Teatro' },
  { id: 'art', label: 'Arte' }, 
  { id: 'sports', label: 'Esportes' },
];

function FilterBar({ 
  activeCategory, 
  onCategoryChange, 
  searchQuery, 
  onSearchChange,
  startTime,
  endTime,
  onTimeRangeChange,
  minPrice,
  maxPrice,
  onPriceRangeChange,
  startDate,
  endDate,
  onDateRangeChange
}) {
  const formatPrice = (value) => {
    if (value === null || value === undefined) return 'R$ 0,00';
    return `R$ ${Number(value).toFixed(2)}`;
  };

  const formatTime = (value) => {
    if (value === null || value === undefined) return '00:00';
    const hours = Math.floor(value);
    const minutes = Math.round((value - hours) * 60);
    return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
  };

  const handlePriceChange = (type, value) => {
    const numValue = Number(value);
    if (type === 'min') {
      if (numValue <= maxPrice) {
        onPriceRangeChange(numValue, maxPrice);
      }
    } else {
      if (numValue >= minPrice) {
        onPriceRangeChange(minPrice, numValue);
      }
    }
  };

  const handleTimeChange = (type, value) => {
    const numValue = Number(value);
    if (type === 'start') {
      if (numValue <= endTime) {
        onTimeRangeChange(numValue, endTime);
      }
    } else {
      if (numValue >= startTime) {
        onTimeRangeChange(startTime, numValue);
      }
    }
  };

  return (
    <StyledFilterBar>
      <StyledFilterBar.Search>
        <i className="fas fa-search">🔍</i>
        <input
          type="text"
          placeholder="Buscar eventos..."
          value={searchQuery}
          onChange={(e) => onSearchChange(e.target.value)}
        />
      </StyledFilterBar.Search>

      <StyledFilterBar.Section>
        <StyledFilterBar.SectionTitle>Categorias</StyledFilterBar.SectionTitle>
        <StyledFilterBar.CategoryList>
          {CATEGORIES.map((category) => (
            <StyledFilterBar.Category
              key={category.id}
              active={activeCategory === category.id}
              onClick={() => onCategoryChange(category.id)}
            >
              {category.label}
            </StyledFilterBar.Category>
          ))}
        </StyledFilterBar.CategoryList>
      </StyledFilterBar.Section>

      <StyledFilterBar.Section>
        <StyledFilterBar.SectionTitle>Horário</StyledFilterBar.SectionTitle>
        <StyledFilterBar.RangeContainer>
          <StyledFilterBar.RangeInput
            type="range"
            min="0"
            max="23"
            step="0.5"
            value={startTime}
            onChange={(e) => handleTimeChange('start', e.target.value)}
          />
          <StyledFilterBar.RangeInput
            type="range"
            min="0"
            max="23"
            step="0.5"
            value={endTime}
            onChange={(e) => handleTimeChange('end', e.target.value)}
          />
          <StyledFilterBar.RangeLabels>
            <span>{formatTime(startTime)}</span>
            <span>{formatTime(endTime)}</span>
          </StyledFilterBar.RangeLabels>
        </StyledFilterBar.RangeContainer>
      </StyledFilterBar.Section>

      <StyledFilterBar.Section>
        <StyledFilterBar.SectionTitle>Preço</StyledFilterBar.SectionTitle>
        <StyledFilterBar.RangeContainer>
          <StyledFilterBar.RangeInput
            type="range"
            min="0"
            max="500"
            step="10"
            value={minPrice}
            onChange={(e) => handlePriceChange('min', e.target.value)}
          />
          <StyledFilterBar.RangeInput
            type="range"
            min="0"
            max="500"
            step="10"
            value={maxPrice}
            onChange={(e) => handlePriceChange('max', e.target.value)}
          />
          <StyledFilterBar.RangeLabels>
            <span>{formatPrice(minPrice)}</span>
            <span>{formatPrice(maxPrice)}</span>
          </StyledFilterBar.RangeLabels>
        </StyledFilterBar.RangeContainer>
      </StyledFilterBar.Section>

      <StyledFilterBar.Section>
        <StyledFilterBar.SectionTitle>Data</StyledFilterBar.SectionTitle>
        <StyledFilterBar.DateRangeContainer>
          <StyledFilterBar.DateInput
            type="date"
            value={startDate}
            onChange={(e) => onDateRangeChange(e.target.value, endDate)}
            max={endDate || '2025-12-31'}
          />
          <StyledFilterBar.DateInput
            type="date"
            value={endDate}
            onChange={(e) => onDateRangeChange(startDate, e.target.value)}
            min={startDate}
            max="2025-12-31"
          />
        </StyledFilterBar.DateRangeContainer>
      </StyledFilterBar.Section>
    </StyledFilterBar>
  );
}

export default FilterBar; 