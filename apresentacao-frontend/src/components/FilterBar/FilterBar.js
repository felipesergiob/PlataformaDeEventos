import React from 'react';
import { FilterBar as StyledFilterBar } from './FilterBar.styles';

const CATEGORIES = [
  { id: 'all', label: 'Todos' },
  { id: 'music', label: 'Música' },
  { id: 'theater', label: 'Teatro' },
  { id: 'art', label: 'Arte' }, 
  { id: 'sports', label: 'Esportes' },
];

function FilterBar({ activeCategory, onCategoryChange, searchQuery, onSearchChange }) {
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

      {CATEGORIES.map((category) => (
        <StyledFilterBar.Filter
          key={category.id}
          active={activeCategory === category.id}
          onClick={() => onCategoryChange(category.id)}
        >
          {category.label}
        </StyledFilterBar.Filter>
      ))}
    </StyledFilterBar>
  );
}

export default FilterBar; 