
import React, { useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { Calendar, MapPin, Clock, Users, BarChart3, Edit, Trash2 } from 'lucide-react';
import { Link } from 'react-router-dom';
import Navbar from '@/components/Navbar';
import EventReport from '@/components/EventReport';
import { getUpcomingEvents, getPastEvents, getEventsByOrganizer } from '@/data/mockData';

const MyEvents = () => {
  const [activeTab, setActiveTab] = useState('upcoming');
  const [showReport, setShowReport] = useState<number | null>(null);

  // Get events from centralized mock data (filtered by current user)
  const currentUserId = 'current-user';
  const allUpcomingEvents = getUpcomingEvents();
  const allPastEvents = getPastEvents();
  
  const upcomingEvents = allUpcomingEvents.filter(event => event.organizerId === currentUserId);
  const pastEvents = allPastEvents.filter(event => event.organizerId === currentUserId);

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('pt-BR');
  };

  if (showReport) {
    return <EventReport eventId={showReport} onBack={() => setShowReport(null)} />;
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100">
      <Navbar />
      
      <div className="container mx-auto px-4 py-8">
        <div className="max-w-6xl mx-auto">
          <div className="flex items-center justify-between mb-8">
            <h1 className="text-3xl font-bold text-gray-900">Meus Eventos</h1>
            <Link to="/create-event">
              <Button className="bg-purple-600 hover:bg-purple-700 text-white">
                Criar Novo Evento
              </Button>
            </Link>
          </div>

          <Tabs value={activeTab} onValueChange={setActiveTab}>
            <TabsList className="grid w-full grid-cols-2">
              <TabsTrigger value="upcoming">Próximos Eventos</TabsTrigger>
              <TabsTrigger value="past">Eventos Passados</TabsTrigger>
            </TabsList>

            <TabsContent value="upcoming" className="mt-6">
              <div className="space-y-4">
                {upcomingEvents.map((event) => (
                  <Card key={event.id}>
                    <CardContent className="p-6">
                      <div className="flex flex-col md:flex-row md:items-center md:justify-between">
                        <div>
                          <Link to={`/event/${event.id}`}>
                            <h3 className="text-xl font-semibold text-gray-900 mb-2 hover:text-purple-600 cursor-pointer">{event.title}</h3>
                          </Link>
                          <p className="text-gray-600 text-sm mb-4">{event.description}</p>
                          
                          <div className="flex flex-wrap gap-4 text-sm text-gray-600">
                            <div className="flex items-center space-x-1">
                              <Calendar className="w-4 h-4 text-purple-600" />
                              <span>{formatDate(event.date)} • {event.time}</span>
                            </div>
                            <div className="flex items-center space-x-1">
                              <MapPin className="w-4 h-4 text-purple-600" />
                              <span>{event.location}</span>
                            </div>
                            <div className="flex items-center space-x-1">
                              <Users className="w-4 h-4 text-purple-600" />
                              <span>{event.participants} confirmados</span>
                            </div>
                          </div>
                        </div>

                        <div className="flex items-center space-x-2 mt-4 md:mt-0">
                          <Button variant="outline" className="border-purple-200 text-purple-600">
                            <Edit className="w-4 h-4 mr-1" />
                            Editar
                          </Button>
                          <Button variant="outline" className="border-red-200 text-red-600 hover:bg-red-50">
                            <Trash2 className="w-4 h-4 mr-1" />
                            Cancelar
                          </Button>
                        </div>
                      </div>
                    </CardContent>
                  </Card>
                ))}

                {upcomingEvents.length === 0 && (
                  <div className="text-center py-12">
                    <h3 className="text-lg font-semibold text-gray-900 mb-2">Você ainda não tem eventos futuros</h3>
                    <p className="text-gray-600 mb-4">Que tal criar um novo evento?</p>
                    <Link to="/create-event">
                      <Button className="bg-purple-600 hover:bg-purple-700 text-white">
                        Criar Novo Evento
                      </Button>
                    </Link>
                  </div>
                )}
              </div>
            </TabsContent>

            <TabsContent value="past" className="mt-6">
              <div className="space-y-4">
                {pastEvents.map((event) => (
                  <Card key={event.id}>
                    <CardContent className="p-6">
                      <div className="flex flex-col md:flex-row md:items-center md:justify-between">
                        <div>
                          <div className="flex items-center space-x-3 mb-2">
                            <Link to={`/event/${event.id}`}>
                              <h3 className="text-xl font-semibold text-gray-900 hover:text-purple-600 cursor-pointer">{event.title}</h3>
                            </Link>
                            <Badge className="bg-purple-600 text-white">
                              {event.rating} ⭐
                            </Badge>
                            <Badge variant="outline">
                              {event.totalRatings} avaliações
                            </Badge>
                          </div>
                          
                          <p className="text-gray-600 text-sm mb-4">{event.description}</p>
                          
                          <div className="flex flex-wrap gap-4 text-sm text-gray-600">
                            <div className="flex items-center space-x-1">
                              <Calendar className="w-4 h-4 text-purple-600" />
                              <span>{formatDate(event.date)} • {event.time}</span>
                            </div>
                            <div className="flex items-center space-x-1">
                              <MapPin className="w-4 h-4 text-purple-600" />
                              <span>{event.location}</span>
                            </div>
                            <div className="flex items-center space-x-1">
                              <Users className="w-4 h-4 text-purple-600" />
                              <span>{event.participants} participaram</span>
                            </div>
                          </div>
                        </div>

                        <div className="flex items-center space-x-2 mt-4 md:mt-0">
                          <Button 
                            className="border-purple-200 bg-purple-600 text-white hover:bg-purple-700"
                            onClick={() => setShowReport(event.id)}
                          >
                            <BarChart3 className="w-4 h-4 mr-2" />
                            Ver Relatório
                          </Button>
                        </div>
                      </div>
                    </CardContent>
                  </Card>
                ))}

                {pastEvents.length === 0 && (
                  <div className="text-center py-12">
                    <h3 className="text-lg font-semibold text-gray-900 mb-2">Você ainda não organizou eventos</h3>
                    <p className="text-gray-600">Seus eventos passados aparecerão aqui depois que acontecerem.</p>
                  </div>
                )}
              </div>
            </TabsContent>
          </Tabs>
        </div>
      </div>
    </div>
  );
};

export default MyEvents;
