import React, { useEffect, useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Calendar, MapPin, Clock, Users, DollarSign, Star, Heart, User, UserCheck, UserPlus, Calendar as CalendarIcon } from 'lucide-react';
import { Link, useParams, useNavigate } from 'react-router-dom';
import { cn } from '@/lib/utils';
import Navbar from '@/components/Navbar';
import EventComments from '@/components/EventComments';
import EventPosts from '@/components/EventPosts';
import EventEvaluation from '@/components/EventEvaluation';
import { eventApi, EventResponse, AvaliacaoResponse, participationApi, ParticipationResponse, avaliacaoApi, userApi, UserResponse } from '@/services/api';
import { useAuth } from '@/contexts/AuthContext';

const EventDetails = () => {
  const { eventId } = useParams<{ eventId: string }>();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState('details');
  const [attendance, setAttendance] = useState<'not_going' | 'maybe' | 'confirmed'>('not_going');
  const [isSaved, setIsSaved] = useState(false);
  const [isFollowingOrganizer, setIsFollowingOrganizer] = useState(false);
  const [event, setEvent] = useState<EventResponse | null>(null);
  const [organizer, setOrganizer] = useState<UserResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [rating, setRating] = useState<number>(0);
  const [totalRatings, setTotalRatings] = useState<number>(0);
  const [participation, setParticipation] = useState<ParticipationResponse | null>(null);
  const [confirmedParticipants, setConfirmedParticipants] = useState<number>(0);

  useEffect(() => {
    const fetchEvent = async () => {
      setLoading(true);
      try {
        if (eventId) {
          const data = await eventApi.getEventById(eventId);
          setEvent(data);
          
          // Buscar informações do organizador
          if (data.organizadorId) {
            const organizerData = await userApi.getUserById(data.organizadorId);
            setOrganizer(organizerData);
          }
          
          // Buscar avaliações do evento
          const avaliacoes = await avaliacaoApi.listarAvaliacoesPorEvento(eventId);
          setTotalRatings(avaliacoes.length);
          
          // Calcular média das avaliações
          if (avaliacoes.length > 0) {
            const somaNotas = avaliacoes.reduce((acc, curr) => acc + curr.nota, 0);
            const media = somaNotas / avaliacoes.length;
            setRating(media);
          }

          // Buscar participações confirmadas
          const participations = await participationApi.getEventParticipations(eventId);
          const confirmedCount = participations.filter(p => p.status === 'CONFIRMADO').length;
          setConfirmedParticipants(confirmedCount);

          // Buscar status de participação do usuário
          if (user) {
            const part = await participationApi.getParticipation(eventId, user.id);
            setParticipation(part);
            if (part) {
              setAttendance(part.status === 'CONFIRMADO' ? 'confirmed' : 'maybe');
              setIsSaved(part.status === 'SALVO');
            } else {
              setAttendance('not_going');
              setIsSaved(false);
            }
          }
        }
      } catch (error) {
        setEvent(null);
      } finally {
        setLoading(false);
      }
    };
    fetchEvent();
  }, [eventId, user]);

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: 'long',
      year: 'numeric',
    });
  };

  const handleAttendanceChange = async (status: 'not_going' | 'maybe' | 'confirmed') => {
    if (!user || !eventId) return;
    try {
      if (status === 'confirmed') {
        if (participation) {
          // Se já existe participação, atualiza o status
          await participationApi.updateParticipationStatus(Number(eventId), Number(user.id), 'CONFIRMADO');
        } else {
          // Se não existe, cria nova participação
          await participationApi.createParticipation({ eventoId: Number(eventId), usuarioId: Number(user.id), status: 'CONFIRMADO' });
        }
        setAttendance('confirmed');
        setIsSaved(false);
        setParticipation({ id: '', eventoId: eventId, usuarioId: user.id, status: 'CONFIRMADO' });
      } else if (status === 'maybe') {
        if (participation) {
          // Se já existe participação, atualiza o status
          await participationApi.updateParticipationStatus(Number(eventId), Number(user.id), 'SALVO');
        } else {
          // Se não existe, cria nova participação
          await participationApi.createParticipation({ eventoId: Number(eventId), usuarioId: Number(user.id), status: 'SALVO' });
        }
        setAttendance('maybe');
        setIsSaved(true);
        setParticipation({ id: '', eventoId: eventId, usuarioId: user.id, status: 'SALVO' });
      } else {
        // Não confirmado nem salvo
        setAttendance('not_going');
        setIsSaved(false);
        setParticipation(null);
      }
    } catch (error) {
      console.error('Erro ao atualizar participação:', error);
      // Aqui você pode adicionar uma notificação de erro para o usuário
    }
  };

  const handleToggleSave = async () => {
    if (!user || !eventId) return;
    try {
      if (!isSaved) {
        if (participation) {
          // Se já existe participação, atualiza o status
          await participationApi.updateParticipationStatus(Number(eventId), Number(user.id), 'SALVO');
        } else {
          // Se não existe, cria nova participação
          await participationApi.createParticipation({ eventoId: Number(eventId), usuarioId: Number(user.id), status: 'SALVO' });
        }
        setIsSaved(true);
        setAttendance('maybe');
        setParticipation({ id: '', eventoId: eventId, usuarioId: user.id, status: 'SALVO' });
      } else {
        // Não existe endpoint para remover, apenas simula
        setIsSaved(false);
        setAttendance('not_going');
        setParticipation(null);
      }
    } catch (error) {
      console.error('Erro ao atualizar participação:', error);
      // Aqui você pode adicionar uma notificação de erro para o usuário
    }
  };

  const handleToggleFollow = () => {
    setIsFollowingOrganizer(!isFollowingOrganizer);
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100 flex items-center justify-center">
        <Navbar />
        <div className="text-xl text-gray-700">Carregando evento...</div>
      </div>
    );
  }

  if (!event) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100">
        <Navbar />
        <div className="container mx-auto px-4 py-8">
          <div className="max-w-4xl mx-auto">
            <Card>
              <CardContent className="p-6 text-center">
                <h2 className="text-xl font-semibold text-gray-900 mb-2">Evento não encontrado</h2>
                <p className="text-gray-600">O evento que você está procurando não existe.</p>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    );
  }

  // Adaptar dados para o layout antigo
  const eventData = {
    id: event.id,
    title: event.titulo,
    description: event.descricao,
    date: event.dataInicio,
    time: new Date(event.dataInicio).toTimeString().slice(0,5),
    endTime: new Date(event.dataFim).toTimeString().slice(0,5),
    location: event.local,
    genre: event.genero,
    price: event.valor,
    image: event.imagem ? `/imagens/${event.imagem}` : '/placeholder.svg',
    participants: confirmedParticipants,
    rating: rating,
    reviews: totalRatings,
    address: '',
    maxParticipants: undefined,
    isPast: false,
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100">
      <Navbar />
      <div className="container mx-auto px-4 py-8">
        <div className="max-w-4xl mx-auto">
          {/* Event Header */}
          <Card className="border-purple-100 overflow-hidden mb-8">
            <div className="h-48 md:h-64 bg-gradient-to-r from-purple-500 to-purple-700 relative">
              {eventData.image && (
                <img
                  src={eventData.image}
                  alt={eventData.title}
                  className="w-full h-full object-cover opacity-50"
                />
              )}
              <div className="absolute top-4 left-4">
                <Badge className="bg-purple-700 text-white">
                  {eventData.genre}
                </Badge>
              </div>
            </div>
            <CardContent className="p-6">
              <div className="flex flex-col md:flex-row md:items-start md:justify-between gap-4">
                <div>
                  <h1 className="text-2xl md:text-3xl font-bold text-gray-900 mb-2">{eventData.title}</h1>
                  <div className="flex items-center space-x-2 mb-4">
                    <div className="flex items-center">
                      <Star className="w-4 h-4 text-yellow-500 fill-current" />
                      <span className="ml-1 text-gray-700">{eventData.rating}</span>
                    </div>
                    <span className="text-gray-500">•</span>
                    <span className="text-gray-700">{eventData.reviews} avaliações</span>
                  </div>
                  <div className="grid grid-cols-1 gap-3 text-gray-700">
                    <div className="flex items-center">
                      <Calendar className="w-5 h-5 text-purple-600 mr-3" />
                      <span>
                        {formatDate(eventData.date)} • {eventData.time} {eventData.endTime && `- ${eventData.endTime}`}
                      </span>
                    </div>
                    <div className="flex items-start">
                      <MapPin className="w-5 h-5 text-purple-600 mr-3 mt-0.5" />
                      <div>
                        <div>{eventData.location}</div>
                        {eventData.address && <div className="text-sm text-gray-500">{eventData.address}</div>}
                      </div>
                    </div>
                    <div className="flex items-center">
                      <DollarSign className="w-5 h-5 text-purple-600 mr-3" />
                      <span>{eventData.price === 0 ? 'Gratuito' : `R$ ${eventData.price.toFixed(2)}`}</span>
                    </div>
                    <div className="flex items-center">
                      <Users className="w-5 h-5 text-purple-600 mr-3" />
                      <span>
                        {eventData.participants} confirmados
                        {eventData.maxParticipants && ` • ${eventData.maxParticipants - eventData.participants} vagas disponíveis`}
                      </span>
                    </div>
                  </div>
                </div>
                <div className="flex flex-col space-y-3">
                  <Button 
                    className={cn(
                      "w-full transition-colors",
                      attendance === 'confirmed' 
                        ? 'bg-green-600 hover:bg-green-700 text-white' 
                        : 'bg-purple-600 hover:bg-purple-700 text-white'
                    )}
                    onClick={() => handleAttendanceChange(attendance === 'confirmed' ? 'not_going' : 'confirmed')}
                  >
                    {attendance === 'confirmed' ? (
                      <>
                        <UserCheck className="w-4 h-4 mr-2" />
                        Confirmado
                      </>
                    ) : (
                      'Confirmar Presença'
                    )}
                  </Button>
                  <Button 
                    variant="outline"
                    className={cn(
                      "w-full transition-colors",
                      isSaved
                        ? 'bg-blue-600 hover:bg-blue-700 text-white border-blue-600' 
                        : 'border-gray-300 text-gray-700 hover:bg-gray-50'
                    )}
                    onClick={handleToggleSave}
                  >
                    {isSaved ? 'Salvo' : 'Salvar'}
                  </Button>
                </div>
              </div>

              {/* Organizer Info */}
              {organizer && (
                <div className="mt-6 pt-6 border-t border-gray-200">
                  <div className="flex items-center justify-between">
                    <div className="flex items-center space-x-4">
                      <Avatar className="w-12 h-12 cursor-pointer" onClick={() => navigate(`/user/${organizer.id}`)}>
                        <AvatarFallback className="text-lg bg-purple-100 text-purple-600">
                          {organizer.nome.split(' ').map(n => n[0]).join('')}
                        </AvatarFallback>
                      </Avatar>
                      <div>
                        <h3 className="font-semibold text-gray-900 cursor-pointer hover:text-purple-600" onClick={() => navigate(`/user/${organizer.id}`)}>
                          {organizer.nome}
                        </h3>
                        <p className="text-sm text-gray-500">Organizador do evento</p>
                      </div>
                    </div>
                    {user && user.id !== organizer.id && (
                      <Button
                        variant="outline"
                        size="sm"
                        onClick={() => setIsFollowingOrganizer(!isFollowingOrganizer)}
                        className={cn(
                          "transition-colors",
                          isFollowingOrganizer
                            ? 'bg-green-600 hover:bg-green-700 text-white border-green-600'
                            : 'border-gray-300 text-gray-700 hover:bg-gray-50'
                        )}
                      >
                        {isFollowingOrganizer ? (
                          <>
                            <UserCheck className="w-4 h-4 mr-2" />
                            Seguindo
                          </>
                        ) : (
                          <>
                            <UserPlus className="w-4 h-4 mr-2" />
                            Seguir
                          </>
                        )}
                      </Button>
                    )}
                  </div>
                </div>
              )}
            </CardContent>
          </Card>
          {/* Event Tabs */}
          <Tabs value={activeTab} onValueChange={setActiveTab} className="mb-8">
            <TabsList className="grid grid-cols-4 w-full">
              <TabsTrigger value="details">Detalhes</TabsTrigger>
              <TabsTrigger value="comments">Comentários</TabsTrigger>
              <TabsTrigger value="posts">Relatos</TabsTrigger>
              {/* Ajuste para exibir avaliações se necessário */}
              <TabsTrigger value="evaluation">Avaliações</TabsTrigger>
            </TabsList>
            <TabsContent value="details" className="mt-6">
              <Card>
                <CardHeader>
                  <CardTitle>Sobre o Evento</CardTitle>
                </CardHeader>
                <CardContent>
                  <p className="text-gray-700 whitespace-pre-line">
                    {eventData.description}
                  </p>
                </CardContent>
              </Card>
            </TabsContent>
            <TabsContent value="comments" className="mt-6">
              <EventComments eventId={eventId || "1"} isOrganizer={false} />
            </TabsContent>
            <TabsContent value="posts" className="mt-6">
              <EventPosts eventId={eventId || "1"} hasAttended={attendance === 'confirmed'} />
            </TabsContent>
            <TabsContent value="evaluation" className="mt-6">
              <EventEvaluation eventId={eventId || "1"} userAttended={attendance === 'confirmed'} />
            </TabsContent>
          </Tabs>
        </div>
      </div>
    </div>
  );
};

export default EventDetails;