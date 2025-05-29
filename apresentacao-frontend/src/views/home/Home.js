import React from 'react';
import { EventList, FilterBar, Loading } from '../../components';
import { Home as StyledHome } from './Home.styles';

function Home({ 
  events, 
  loading, 
  error,
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
  if (loading) {
    return <Loading />;
  }

  if (error) {
    return <div>Erro ao carregar eventos</div>;
  }

  return (
    <StyledHome>
      <StyledHome.Container>
        <StyledHome.Header>
          <StyledHome.Title>
            Eventos Disponíveis
          </StyledHome.Title>
        </StyledHome.Header>

        <StyledHome.Content>
          <EventList events={events} />
        </StyledHome.Content>

        <StyledHome.Filters>
          <FilterBar
            activeCategory={activeCategory}
            onCategoryChange={onCategoryChange}
            searchQuery={searchQuery}
            onSearchChange={onSearchChange}
            startTime={startTime}
            endTime={endTime}
            onTimeRangeChange={onTimeRangeChange}
            minPrice={minPrice}
            maxPrice={maxPrice}
            onPriceRangeChange={onPriceRangeChange}
            startDate={startDate}
            endDate={endDate}
            onDateRangeChange={onDateRangeChange}
          />
        </StyledHome.Filters>
      </StyledHome.Container>
    </StyledHome>
  );
}

export default Home; 