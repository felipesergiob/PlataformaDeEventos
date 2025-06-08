import React, { useState, useEffect } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Calendar, MapPin, Search, Filter, Plus } from 'lucide-react';
import EventCard from '@/components/EventCard';
import FeaturedEvents from '@/components/FeaturedEvents';
import FilterPanel from '@/components/FilterPanel';
import Navbar from '@/components/Navbar';
import { eventApi, EventResponse } from '@/services/api';

const Index = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [showFilters, setShowFilters] = useState(false);
  const [filters, setFilters] = useState({
    genre: '',
    date: '',
    time: '',
    price: ''
  });
  const [events, setEvents] = useState<EventResponse[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchEvents = async () => {
      setLoading(true);
      try {
        const data = await eventApi.getAllEvents();
        setEvents(data);
      } catch (error) {
        setEvents([]);
      } finally {
        setLoading(false);
      }
    };
    fetchEvents();
  }, []);

  // Adaptar os dados da API para o formato esperado pelo EventCard
  const mappedEvents = events.map((event) => {
    const startDate = new Date(event.dataInicio);
    return {
      id: Number(event.id),
      title: event.titulo,
      description: event.descricao,
      date: startDate.toISOString().split('T')[0],
      time: startDate.toTimeString().slice(0,5),
      location: event.local,
      genre: event.genero,
      price: event.valor,
      image: event.imagem ? `/imagens/${event.imagem}` : '/placeholder.svg',
      organizer: `Organizador ${event.organizadorId}`,
      participants: event.participantes,
      rating: 4.5, // Valor fictício, ajuste se tiver nota
      isSaved: false,
      attending: 'not_going' as const,
    };
  });

  const filteredEvents = mappedEvents.filter(event => {
    const matchesSearch = event.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         event.description.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesGenre = !filters.genre || event.genre === filters.genre;
    const matchesPrice = !filters.price || 
                        (filters.price === 'free' && event.price === 0) ||
                        (filters.price === 'paid' && event.price > 0);
    return matchesSearch && matchesGenre && matchesPrice;
  });

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100">
      <Navbar />

      <div className="container mx-auto px-4 py-8">
        {/* Hero Section */}
        <div className="text-center mb-12">
          <h2 className="text-4xl md:text-5xl font-bold text-gray-900 mb-4">
            Descubra Eventos
            <span className="block bg-gradient-to-r from-purple-600 to-purple-800 bg-clip-text text-transparent">
              Incríveis
            </span>
          </h2>
          <p className="text-xl text-gray-600 max-w-2xl mx-auto mb-8">
            Conecte-se com sua comunidade, participe de eventos únicos e crie experiências memoráveis
          </p>

          {/* Search and Filter */}
          <div className="max-w-4xl mx-auto">
            <div className="flex flex-col md:flex-row gap-4 mb-6">
              <div className="relative flex-1">
                <Search className="absolute left-3 top-3 h-4 w-4 text-gray-400" />
                <Input
                  placeholder="Buscar eventos por nome ou descrição..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="pl-10 h-12 text-lg border-purple-200 focus:border-purple-500"
                />
              </div>
              <Button
                onClick={() => setShowFilters(!showFilters)}
                variant="outline"
                className="h-12 px-6 border-purple-200 text-purple-600 hover:bg-purple-50"
              >
                <Filter className="w-4 h-4 mr-2" />
                Filtros
              </Button>
            </div>

            {showFilters && (
              <FilterPanel filters={filters} onFiltersChange={setFilters} />
            )}
          </div>
        </div>

        {/* Featured Events */}
        <FeaturedEvents />

        {/* Events Grid */}
        <div className="mb-8">
          <div className="flex items-center justify-between mb-6">
            <h3 className="text-2xl font-bold text-gray-900">Todos os Eventos</h3>
            <div className="flex items-center space-x-2 text-sm text-gray-600">
              <span>{filteredEvents.length} eventos encontrados</span>
            </div>
          </div>

          {loading ? (
            <div className="text-center py-12">Carregando eventos...</div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {filteredEvents.map((event) => (
                <EventCard key={event.id} event={event} />
              ))}
            </div>
          )}

          {!loading && filteredEvents.length === 0 && (
            <div className="text-center py-12">
              <div className="w-24 h-24 bg-purple-50 rounded-full flex items-center justify-center mx-auto mb-4">
                <Search className="w-8 h-8 text-purple-400" />
              </div>
              <h3 className="text-lg font-semibold text-gray-900 mb-2">Nenhum evento encontrado</h3>
              <p className="text-gray-600">Tente ajustar os filtros ou buscar por outros termos</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Index;
