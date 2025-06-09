import { useEffect, useState } from 'react';
import { useAuth } from '@/contexts/AuthContext';
import { userApi, eventApi, UserResponse, EventResponse } from '@/services/api';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Button } from '@/components/ui/button';
import { Calendar, MapPin, Clock, Users, DollarSign } from 'lucide-react';
import { Link } from 'react-router-dom';
import Navbar from '@/components/Navbar';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';

interface EventWithOrganizer extends EventResponse {
  organizador: UserResponse;
}

const Following = () => {
  const { user } = useAuth();
  const [events, setEvents] = useState<EventWithOrganizer[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchFollowingEvents = async () => {
      if (!user) return;

      try {
        // Buscar usuários que o usuário atual segue
        const followingUsers = await userApi.getFollowingUsers(user.id);
        
        // Para cada usuário seguido, buscar seus eventos
        const eventsPromises = followingUsers.map(async (followingUser) => {
          const userEvents = await userApi.getOrganizerEvents(followingUser.id);
          return userEvents.map(event => ({
            ...event,
            organizador: followingUser
          }));
        });

        const eventsArrays = await Promise.all(eventsPromises);
        const allEvents = eventsArrays.flat();
        
        // Ordenar eventos por data de criação (mais recentes primeiro)
        allEvents.sort((a, b) => 
          new Date(b.dataCriacao).getTime() - new Date(a.dataCriacao).getTime()
        );

        setEvents(allEvents);
      } catch (error) {
        console.error('Erro ao buscar eventos:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchFollowingEvents();
  }, [user]);

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50">
        <Navbar />
        <div className="container mx-auto px-4 py-8">
          <h1 className="text-2xl font-bold mb-6">Carregando eventos...</h1>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />
      <div className="container mx-auto px-4 py-8">
        <h1 className="text-2xl font-bold mb-6">Eventos de quem você segue</h1>
        
        {events.length === 0 ? (
          <div className="text-center py-8">
            <p className="text-gray-500">Você ainda não segue ninguém ou não há eventos disponíveis.</p>
            <Button asChild className="mt-4">
              <Link to="/explorar">Explorar eventos</Link>
            </Button>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {events.map((event) => (
              <Card key={event.id} className="overflow-hidden">
                {event.imagem && (
                  <div className="aspect-video relative">
                    <img
                      src={event.imagem}
                      alt={event.titulo}
                      className="object-cover w-full h-full"
                    />
                  </div>
                )}
                <CardHeader>
                  <div className="flex items-center space-x-2 mb-2">
                    <Avatar className="h-8 w-8">
                      <AvatarFallback>
                        {event.organizador.nome.split(' ').map(n => n[0]).join('')}
                      </AvatarFallback>
                    </Avatar>
                    <span className="text-sm text-gray-600">
                      {event.organizador.nome}
                    </span>
                  </div>
                  <CardTitle className="line-clamp-2">{event.titulo}</CardTitle>
                  <CardDescription className="line-clamp-2">
                    {event.descricao}
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <div className="space-y-2">
                    <div className="flex items-center text-sm text-gray-600">
                      <Calendar className="w-4 h-4 mr-2" />
                      {format(new Date(event.dataInicio), "d 'de' MMMM 'de' yyyy", { locale: ptBR })}
                    </div>
                    <div className="flex items-center text-sm text-gray-600">
                      <Clock className="w-4 h-4 mr-2" />
                      {format(new Date(event.dataInicio), 'HH:mm')} - {format(new Date(event.dataFim), 'HH:mm')}
                    </div>
                    <div className="flex items-center text-sm text-gray-600">
                      <MapPin className="w-4 h-4 mr-2" />
                      {event.local}
                    </div>
                    <div className="flex items-center text-sm text-gray-600">
                      <Users className="w-4 h-4 mr-2" />
                      {event.participantes} participantes
                    </div>
                    <div className="flex items-center text-sm text-gray-600">
                      <DollarSign className="w-4 h-4 mr-2" />
                      {event.valor === 0 ? 'Gratuito' : `R$ ${event.valor.toFixed(2)}`}
                    </div>
                  </div>
                </CardContent>
                <CardFooter>
                  <Button asChild className="w-full bg-gradient-to-r from-purple-600 to-purple-800 hover:from-purple-700 hover:to-purple-900 text-white">
                    <Link to={`/event/${event.id}`}>
                      Ver detalhes
                    </Link>
                  </Button>
                </CardFooter>
              </Card>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default Following; 