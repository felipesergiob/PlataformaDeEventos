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
  onSearchChange
}) {
  if (loading) {
    return <Loading />;
  }

  if (error) {
    return <div>Erro ao carregar eventos</div>;
  }

  return (
    <StyledHome>
      <StyledHome.Container maxWidth="lg">
        <StyledHome.Header>
          <StyledHome.Title>
            Eventos Disponíveis
          </StyledHome.Title>
          <FilterBar
            activeCategory={activeCategory}
            onCategoryChange={onCategoryChange}
            searchQuery={searchQuery}
            onSearchChange={onSearchChange}
          />
        </StyledHome.Header>
        <StyledHome.Content>
          <EventList events={events} />
        </StyledHome.Content>
      </StyledHome.Container>
    </StyledHome>
  );
}

export default Home; 