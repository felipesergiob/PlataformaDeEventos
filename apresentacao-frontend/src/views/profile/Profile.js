import React from 'react';
import { Profile as StyledProfile } from './Profile.styles';
import { EventCalendar } from '../../components/EventCalendar';
import { SavedEvents } from '../../components/SavedEvents';

function Profile() {
  // Mock de eventos salvos/confirmados
  const savedEvents = [
    {
      id: 1,
      title: 'Show de Rock',
      date: '2024-04-15T20:00:00',
      location: 'Arena Show',
      image: 'https://images.unsplash.com/photo-1501281668745-f7f57925c3b4?w=800&auto=format&fit=crop&q=60',
      status: 'confirmado'
    },
    {
      id: 2,
      title: 'Festa Eletrônica',
      date: '2024-04-20T23:00:00',
      location: 'Club House',
      image: 'https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?w=800&auto=format&fit=crop&q=60',
      status: 'salvo'
    }
  ];

  return (
    <StyledProfile>
      <StyledProfile.Container maxWidth="lg">
        <StyledProfile.Header>
          <StyledProfile.Title>Meu Perfil</StyledProfile.Title>
        </StyledProfile.Header>

        <StyledProfile.Content>
          <StyledProfile.Section>
            <StyledProfile.SectionTitle>Calendário de Eventos</StyledProfile.SectionTitle>
            <EventCalendar events={savedEvents} />
          </StyledProfile.Section>

          <StyledProfile.Section>
            <StyledProfile.SectionTitle>Meus Eventos</StyledProfile.SectionTitle>
            <SavedEvents events={savedEvents} />
          </StyledProfile.Section>
        </StyledProfile.Content>
      </StyledProfile.Container>
    </StyledProfile>
  );
}

export default Profile; 