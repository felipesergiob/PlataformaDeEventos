import React, { useEffect, useState } from 'react';
import { Card, CardContent } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { TrendingUp, Users, Star, Calendar, MapPin } from 'lucide-react';
import { eventApi, FeaturedEventResponse, participationApi, avaliacaoApi } from '@/services/api';
import { Link } from 'react-router-dom';

const FeaturedEvents = () => {
  const [featuredEvents, setFeaturedEvents] = useState<FeaturedEventResponse[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchFeatured = async () => {
      setLoading(true);
      try {
        // Buscar todos os eventos
        const allEvents = await eventApi.getAllEvents();
        
        // Para cada evento, buscar suas participações e avaliações
        const eventsWithParticipantCount = await Promise.all(
          allEvents.map(async (event) => {
            const [participations, avaliacoes] = await Promise.all([
              participationApi.getEventParticipations(event.id),
              avaliacaoApi.listarAvaliacoesPorEvento(event.id)
            ]);
            
            // Contar apenas participações confirmadas
            const confirmedCount = participations.filter(p => p.status === 'CONFIRMADO').length;
            
            // Calcular média das notas
            const mediaNotas = avaliacoes.length > 0 
              ? avaliacoes.reduce((acc, curr) => acc + curr.nota, 0) / avaliacoes.length 
              : 0;

            return {
              ...event,
              participantes: confirmedCount, // Número de participantes confirmados
              mediaNotas
            };
          })
        );

        // Ordenar por número de participantes confirmados e pegar os top 3
        const topEvents = eventsWithParticipantCount
          .sort((a, b) => b.participantes - a.participantes) // Ordena do maior para o menor número de participantes
          .slice(0, 3)
          .map((event, index) => ({
            ...event,
            posicaoRanking: index + 1
          }));

        setFeaturedEvents(topEvents);
      } catch (error) {
        console.error('Erro ao buscar eventos em destaque:', error);
        setFeaturedEvents([]);
      } finally {
        setLoading(false);
      }
    };
    fetchFeatured();
  }, []);

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: 'short'
    });
  };

  return (
    <div className="mb-12">
      <div className="flex items-center space-x-2 mb-6">
        <TrendingUp className="w-6 h-6 text-purple-600" />
        <h3 className="text-2xl font-bold text-gray-900">Eventos Mais Populares</h3>
      </div>
      {loading ? (
        <div className="text-center py-8">Carregando eventos em destaque...</div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {featuredEvents.map((event) => (
            <Card key={event.id} className="relative overflow-hidden group hover:shadow-lg transition-all duration-300 border-purple-100">
              <div className="absolute top-4 left-4 z-10">
                <Badge className="bg-purple-600 text-white">
                  #{event.posicaoRanking} Mais Popular
                </Badge>
              </div>
              <div className="h-32 bg-gradient-to-br from-purple-400 via-purple-500 to-purple-600 relative">
                <div className="absolute inset-0 bg-black/10" />
                <div className="absolute bottom-4 right-4">
                  <Badge variant="secondary" className="bg-white/90 text-purple-900">
                    {event.genero}
                  </Badge>
                </div>
              </div>
              <CardContent className="p-4">
                <h4 className="font-semibold text-lg text-gray-900 mb-2 group-hover:text-purple-600 transition-colors">
                  {event.titulo}
                </h4>
                <p className="text-gray-600 text-sm mb-3">
                  {event.descricao}
                </p>
                <div className="space-y-2 mb-4">
                  <div className="flex items-center justify-between text-sm">
                    <div className="flex items-center text-gray-600">
                      <Calendar className="w-4 h-4 mr-2 text-purple-600" />
                      <span>{formatDate(event.dataInicio)}</span>
                    </div>
                    <div className="flex items-center text-gray-600">
                      <Star className="w-4 h-4 mr-1 text-yellow-500 fill-current" />
                      <span>{event.mediaNotas?.toFixed(1) || '0.0'}</span>
                    </div>
                  </div>
                  <div className="flex items-center text-sm text-gray-600">
                    <MapPin className="w-4 h-4 mr-2 text-purple-600" />
                    <span className="truncate">{event.local}</span>
                  </div>
                </div>
                <div className="flex items-center justify-between pt-3 border-t border-purple-100">
                  <div className="flex items-center space-x-4">
                    <div className="flex items-center text-sm text-gray-600">
                      <Users className="w-4 h-4 mr-1 text-purple-600" />
                      <span>{event.participantes} confirmados</span>
                    </div>
                  </div>
                  <Link to={`/event/${event.id}`}>
                    <Button size="sm" className="bg-purple-600 hover:bg-purple-700 text-white">
                      Ver Mais
                    </Button>
                  </Link>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
};

export default FeaturedEvents;
