
import React, { useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Calendar, MapPin, Clock, Users, DollarSign, Star, Heart, User, UserCheck, UserPlus, Calendar as CalendarIcon } from 'lucide-react';
import { Link, useParams } from 'react-router-dom';
import { cn } from '@/lib/utils';
import Navbar from '@/components/Navbar';
import EventComments from '@/components/EventComments';
import EventPosts from '@/components/EventPosts';
import EventEvaluation from '@/components/EventEvaluation';
import { getEvent, getUser } from '@/data/mockData';

const EventDetails = () => {
  const { eventId } = useParams<{ eventId: string }>();
  const [activeTab, setActiveTab] = useState('details');
  const [attendance, setAttendance] = useState<'not_going' | 'maybe' | 'confirmed'>('not_going');
  const [isSaved, setIsSaved] = useState(false);
  const [isFollowingOrganizer, setIsFollowingOrganizer] = useState(false);

  // Get event and organizer data from centralized mock
  const event = getEvent(parseInt(eventId || '1'));
  const organizer = event ? getUser(event.organizerId) : null;

  if (!event || !organizer) {
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

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: 'long',
      year: 'numeric',
    });
  };

  const handleAttendanceChange = (status: 'not_going' | 'maybe' | 'confirmed') => {
    setAttendance(status);
  };

  const handleToggleSave = () => {
    setIsSaved(!isSaved);
  };

  const handleToggleFollow = () => {
    setIsFollowingOrganizer(!isFollowingOrganizer);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100">
      <Navbar />
      
      <div className="container mx-auto px-4 py-8">
        <div className="max-w-4xl mx-auto">
          {/* Event Header */}
          <Card className="border-purple-100 overflow-hidden mb-8">
            <div className="h-48 md:h-64 bg-gradient-to-r from-purple-500 to-purple-700 relative">
              {event.image && (
                <img
                  src={event.image}
                  alt={event.title}
                  className="w-full h-full object-cover opacity-50"
                />
              )}
              <div className="absolute top-4 left-4">
                <Badge className="bg-purple-700 text-white">
                  {event.genre}
                </Badge>
              </div>
            </div>
            
            <CardContent className="p-6">
              <div className="flex flex-col md:flex-row md:items-start md:justify-between gap-4">
                <div>
                  <h1 className="text-2xl md:text-3xl font-bold text-gray-900 mb-2">{event.title}</h1>
                  
                  <div className="flex items-center space-x-2 mb-4">
                    <div className="flex items-center">
                      <Star className="w-4 h-4 text-yellow-500 fill-current" />
                      <span className="ml-1 text-gray-700">{event.rating}</span>
                    </div>
                    <span className="text-gray-500">•</span>
                    <span className="text-gray-700">{event.reviews} avaliações</span>
                  </div>
                  
                  <div className="grid grid-cols-1 gap-3 text-gray-700">
                    <div className="flex items-center">
                      <Calendar className="w-5 h-5 text-purple-600 mr-3" />
                      <span>
                        {formatDate(event.date)} • {event.time} {event.endTime && `- ${event.endTime}`}
                      </span>
                    </div>
                    <div className="flex items-start">
                      <MapPin className="w-5 h-5 text-purple-600 mr-3 mt-0.5" />
                      <div>
                        <div>{event.location}</div>
                        {event.address && <div className="text-sm text-gray-500">{event.address}</div>}
                      </div>
                    </div>
                    <div className="flex items-center">
                      <DollarSign className="w-5 h-5 text-purple-600 mr-3" />
                      <span>{event.price === 0 ? 'Gratuito' : `R$ ${event.price.toFixed(2)}`}</span>
                    </div>
                    <div className="flex items-center">
                      <Users className="w-5 h-5 text-purple-600 mr-3" />
                      <span>
                        {event.participants} confirmados
                        {event.maxParticipants && ` • ${event.maxParticipants - event.participants} vagas disponíveis`}
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
                      attendance === 'maybe' 
                        ? 'bg-blue-600 hover:bg-blue-700 text-white border-blue-600' 
                        : 'border-gray-300 text-gray-700 hover:bg-gray-50'
                    )}
                    onClick={() => handleAttendanceChange(attendance === 'maybe' ? 'not_going' : 'maybe')}
                  >
                    {attendance === 'maybe' ? 'Talvez ✓' : 'Talvez'}
                  </Button>
                  
                  <Button 
                    variant="outline"
                    className={isSaved ? 'text-red-600 border-red-200 hover:bg-red-50' : 'border-gray-300 text-gray-700 hover:bg-gray-50'}
                    onClick={handleToggleSave}
                  >
                    <Heart className={cn("w-4 h-4 mr-2", isSaved && "fill-current")} />
                    {isSaved ? 'Salvo' : 'Salvar'}
                  </Button>
                </div>
              </div>

              {/* Organizer Info */}
              <div className="mt-8 pt-6 border-t border-gray-200">
                <div className="flex items-center justify-between">
                  <div className="flex items-center space-x-3">
                    <Avatar>
                      <AvatarImage src={organizer.avatar} alt={organizer.name} />
                      <AvatarFallback>
                        {organizer.name.split(' ').map(n => n[0]).join('')}
                      </AvatarFallback>
                    </Avatar>
                    <div>
                      <div className="text-sm text-gray-500">Organizado por</div>
                      <Link to={`/user/${organizer.id}`} className="font-medium text-gray-900 hover:text-purple-600">
                        {organizer.name}
                      </Link>
                      <div className="flex items-center text-sm text-gray-500">
                        <Star className="w-3 h-3 text-yellow-500 fill-current mr-1" />
                        {organizer.averageRating} • {organizer.eventsOrganized} eventos
                      </div>
                    </div>
                  </div>
                  
                  <Button 
                    variant={isFollowingOrganizer ? "default" : "outline"} 
                    className={isFollowingOrganizer ? "bg-purple-600 hover:bg-purple-700" : ""}
                    onClick={handleToggleFollow}
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
                </div>
              </div>
            </CardContent>
          </Card>

          {/* Event Tabs */}
          <Tabs value={activeTab} onValueChange={setActiveTab} className="mb-8">
            <TabsList className="grid grid-cols-4 w-full">
              <TabsTrigger value="details">Detalhes</TabsTrigger>
              <TabsTrigger value="comments">Comentários</TabsTrigger>
              <TabsTrigger value="posts">Relatos</TabsTrigger>
              {!event.isPast && <TabsTrigger value="evaluation">Avaliações</TabsTrigger>}
              {event.isPast && <TabsTrigger value="evaluation">Avaliar</TabsTrigger>}
            </TabsList>

            <TabsContent value="details" className="mt-6">
              <Card>
                <CardHeader>
                  <CardTitle>Sobre o Evento</CardTitle>
                </CardHeader>
                <CardContent>
                  <p className="text-gray-700 whitespace-pre-line">
                    {event.description}
                  </p>
                </CardContent>
              </Card>
            </TabsContent>

            <TabsContent value="comments" className="mt-6">
              <EventComments eventId={eventId || "1"} isOrganizer={false} />
            </TabsContent>

            <TabsContent value="posts" className="mt-6">
              <EventPosts eventId={eventId || "1"} hasAttended={attendance === 'confirmed' || event.isPast} />
            </TabsContent>

            <TabsContent value="evaluation" className="mt-6">
              <EventEvaluation eventId={eventId || "1"} userAttended={attendance === 'confirmed' || event.isPast} />
            </TabsContent>
          </Tabs>
        </div>
      </div>
    </div>
  );
};

export default EventDetails;
