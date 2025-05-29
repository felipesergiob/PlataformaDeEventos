import React, { useState, useEffect, useMemo } from 'react';
import Home from './Home';

// Mock de eventos
export const mockEvents = [
  {
    id: 1,
    title: 'Show de Rock',
    description: 'Uma noite incrível com as melhores bandas de rock da cidade. Venha curtir muito rock n roll!',
    date: '2024-04-15T20:00:00',
    location: 'Arena Show',
    image: 'https://images.unsplash.com/photo-1501281668745-f7f57925c3b4?w=800&auto=format&fit=crop&q=60',
    price: 'R$ 120,00',
    category: 'music',
    featured: true
  },
  {
    id: 2,
    title: 'Festa Eletrônica',
    description: 'A maior festa eletrônica da cidade com os melhores DJs nacionais e internacionais.',
    date: '2024-04-20T23:00:00',
    location: 'Club House',
    image: 'https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?w=800&auto=format&fit=crop&q=60',
    price: 'R$ 150,00',
    category: 'music',
    featured: true
  },
  {
    id: 3,
    title: 'Festival de Música',
    description: '3 dias de muita música e diversão com mais de 50 artistas nacionais e internacionais.',
    date: '2024-05-01T14:00:00',
    location: 'Parque da Cidade',
    image: 'https://images.unsplash.com/photo-1470229722913-7c0e2dbbafd3?w=800&auto=format&fit=crop&q=60',
    price: 'R$ 300,00',
    category: 'music',
    featured: true
  },
  {
    id: 4,
    title: 'Teatro: Hamlet',
    description: 'Uma adaptação moderna da obra clássica de Shakespeare, com elenco premiado.',
    date: '2024-04-25T19:30:00',
    location: 'Teatro Municipal',
    image: 'https://images.unsplash.com/photo-1507676184212-d03ab07a01bf?w=800&auto=format&fit=crop&q=60',
    price: 'R$ 80,00',
    category: 'theater',
    featured: false
  },
  {
    id: 5,
    title: 'Exposição de Arte',
    description: 'Exposição com obras de artistas contemporâneos nacionais e internacionais.',
    date: '2024-04-10T10:00:00',
    location: 'Museu de Arte Moderna',
    image: 'https://images.unsplash.com/photo-1579783902614-a3fb3927b6a5?w=800&auto=format&fit=crop&q=60',
    price: 'R$ 50,00',
    category: 'art',
    featured: false
  },
  {
    id: 6,
    title: 'Balé: O Lago dos Cisnes',
    description: 'Apresentação do clássico balé com a companhia nacional.',
    date: '2024-05-05T20:00:00',
    location: 'Teatro Nacional',
    image: 'https://images.unsplash.com/photo-1547153760-18fc86324498?w=800&auto=format&fit=crop&q=60',
    price: 'R$ 90,00',
    category: 'theater',
    featured: true
  },
  {
    id: 7,
    title: 'Exposição Fotográfica',
    description: 'Mostra de fotografia documental com registros históricos da cidade.',
    date: '2024-04-18T09:00:00',
    location: 'Centro Cultural',
    image: 'https://images.unsplash.com/photo-1554048612-b6a482bc67e5?w=800&auto=format&fit=crop&q=60',
    price: 'R$ 35,00',
    category: 'art',
    featured: false
  },
  {
    id: 8,
    title: 'Maratona da Cidade',
    description: 'Corrida de rua com percursos de 5km, 10km e 21km. Inscrições abertas!',
    date: '2024-05-12T07:00:00',
    location: 'Avenida Principal',
    image: 'https://images.unsplash.com/photo-1552674605-db6ffd4facb5?w=800&auto=format&fit=crop&q=60',
    price: 'R$ 70,00',
    category: 'sports',
    featured: true
  },
  {
    id: 9,
    title: 'Torneio de Tênis',
    description: 'Torneio amador de tênis com categorias masculina e feminina.',
    date: '2024-04-22T09:00:00',
    location: 'Clube de Tênis',
    image: 'https://images.unsplash.com/photo-1595435934249-5df7ed86e1c0?w=800&auto=format&fit=crop&q=60',
    price: 'R$ 60,00',
    category: 'sports',
    featured: false
  }
];

function HomeContainer() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [activeCategory, setActiveCategory] = useState('all');
  const [searchQuery, setSearchQuery] = useState('');

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        setLoading(true);
        // Simulando uma chamada de API com delay
        await new Promise(resolve => setTimeout(resolve, 1000));
        setEvents(mockEvents);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchEvents();
  }, []);

  const filteredEvents = useMemo(() => {
    return events.filter(event => {
      const matchesCategory = activeCategory === 'all' || event.category === activeCategory;
      const matchesSearch = event.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
                          event.description.toLowerCase().includes(searchQuery.toLowerCase()) ||
                          event.location.toLowerCase().includes(searchQuery.toLowerCase());
      return matchesCategory && matchesSearch;
    });
  }, [events, activeCategory, searchQuery]);

  return (
    <Home
      events={filteredEvents}
      loading={loading}
      error={error}
      activeCategory={activeCategory}
      onCategoryChange={setActiveCategory}
      searchQuery={searchQuery}
      onSearchChange={setSearchQuery}
    />
  );
}

export default HomeContainer; 