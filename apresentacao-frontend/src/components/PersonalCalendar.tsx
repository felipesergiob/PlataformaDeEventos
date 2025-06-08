import React, { useEffect, useState } from 'react';
import { Card, CardContent } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Calendar as CalendarIcon, List, Grid, ChevronLeft, ChevronRight } from 'lucide-react';
import { useAuth } from '@/contexts/AuthContext';
import { participationApi, eventApi, EventResponse } from '@/services/api';
import { useToast } from '@/hooks/use-toast';
import { cn } from '@/lib/utils';

interface CalendarEvent {
  id: string;
  title: string;
  date: Date;
  status: 'SALVO' | 'CONFIRMADO';
}

const PersonalCalendar = () => {
  const { user } = useAuth();
  const { toast } = useToast();
  const [events, setEvents] = useState<CalendarEvent[]>([]);
  const [loading, setLoading] = useState(true);
  const [viewMode, setViewMode] = useState<'list' | 'calendar'>('list');
  const [currentDate, setCurrentDate] = useState(new Date());

  useEffect(() => {
    const fetchUserEvents = async () => {
      if (!user) return;
      
      try {
        setLoading(true);
        // Buscar participações do usuário
        const participations = await participationApi.getUserParticipations(user.id);
        
        // Buscar detalhes de cada evento
        const eventPromises = participations.map(async (participation) => {
          const event = await eventApi.getEventById(participation.eventoId);
          return {
            id: event.id || '',
            title: event.titulo,
            date: new Date(event.dataInicio),
            status: participation.status
          };
        });

        const userEvents = await Promise.all(eventPromises);
        setEvents(userEvents);
      } catch (error) {
        console.error('Erro ao carregar eventos:', error);
        toast({
          title: 'Erro',
          description: 'Não foi possível carregar seus eventos.',
          variant: 'destructive'
        });
      } finally {
        setLoading(false);
      }
    };

    fetchUserEvents();
  }, [user, toast]);

  const getEventsForDate = (date: Date) => {
    return events.filter(event => 
      event.date.getDate() === date.getDate() &&
      event.date.getMonth() === date.getMonth() &&
      event.date.getFullYear() === date.getFullYear()
    );
  };

  const getDaysInMonth = (date: Date) => {
    const year = date.getFullYear();
    const month = date.getMonth();
    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    const daysInMonth = lastDay.getDate();
    const startingDayOfWeek = firstDay.getDay();

    const days = [];
    
    // Dias do mês anterior
    for (let i = startingDayOfWeek - 1; i >= 0; i--) {
      const prevDate = new Date(year, month, -i);
      days.push({ date: prevDate, isCurrentMonth: false });
    }
    
    // Dias do mês atual
    for (let day = 1; day <= daysInMonth; day++) {
      days.push({ date: new Date(year, month, day), isCurrentMonth: true });
    }
    
    // Dias do próximo mês
    const remainingDays = 42 - days.length;
    for (let day = 1; day <= remainingDays; day++) {
      const nextDate = new Date(year, month + 1, day);
      days.push({ date: nextDate, isCurrentMonth: false });
    }
    
    return days;
  };

  const navigateMonth = (direction: 'prev' | 'next') => {
    setCurrentDate(prev => {
      const newDate = new Date(prev);
      if (direction === 'prev') {
        newDate.setMonth(prev.getMonth() - 1);
      } else {
        newDate.setMonth(prev.getMonth() + 1);
      }
      return newDate;
    });
  };

  if (loading) {
    return (
      <div className="container mx-auto px-4 py-8">
        <div className="text-center text-gray-600">Carregando seus eventos...</div>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <Card>
        <CardContent className="p-6">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-2xl font-bold text-gray-900">Meu Calendário</h2>
            <div className="flex items-center space-x-4">
              <div className="flex items-center bg-gray-100 rounded-lg p-1">
                <Button
                  variant={viewMode === 'list' ? 'default' : 'ghost'}
                  size="sm"
                  onClick={() => setViewMode('list')}
                  className="rounded-md"
                >
                  <List className="w-4 h-4" />
                </Button>
                <Button
                  variant={viewMode === 'calendar' ? 'default' : 'ghost'}
                  size="sm"
                  onClick={() => setViewMode('calendar')}
                  className="rounded-md"
                >
                  <Grid className="w-4 h-4" />
                </Button>
              </div>
            </div>
          </div>

          {events.length === 0 ? (
            <div className="text-center text-gray-600 py-8">
              Você ainda não tem eventos salvos ou confirmados.
            </div>
          ) : viewMode === 'list' ? (
            <div className="space-y-4">
              {events.map(event => (
                <div
                  key={event.id}
                  className={`p-4 rounded-lg border ${
                    event.status === 'CONFIRMADO'
                      ? 'bg-green-50 border-green-200'
                      : 'bg-blue-50 border-blue-200'
                  }`}
                >
                  <div className="flex items-center justify-between">
                    <div>
                      <h3 className="font-semibold text-gray-900">{event.title}</h3>
                      <p className="text-sm text-gray-600">
                        {event.date.toLocaleDateString('pt-BR', {
                          weekday: 'long',
                          day: '2-digit',
                          month: 'long',
                          year: 'numeric',
                          hour: '2-digit',
                          minute: '2-digit'
                        })}
                      </p>
                    </div>
                    <span className={`px-2 py-1 rounded-full text-xs font-medium ${
                      event.status === 'CONFIRMADO'
                        ? 'bg-green-100 text-green-800'
                        : 'bg-blue-100 text-blue-800'
                    }`}>
                      {event.status === 'CONFIRMADO' ? 'Confirmado' : 'Salvo'}
                    </span>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className="space-y-4">
              {/* Navegação do Calendário */}
              <div className="flex items-center justify-between">
                <Button variant="outline" size="sm" onClick={() => navigateMonth('prev')}>
                  <ChevronLeft className="w-4 h-4" />
                </Button>
                <h3 className="text-lg font-semibold">
                  {currentDate.toLocaleDateString('pt-BR', { month: 'long', year: 'numeric' })}
                </h3>
                <Button variant="outline" size="sm" onClick={() => navigateMonth('next')}>
                  <ChevronRight className="w-4 h-4" />
                </Button>
              </div>

              {/* Cabeçalho dos dias da semana */}
              <div className="grid grid-cols-7 gap-2 mb-2">
                {['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'].map(day => (
                  <div key={day} className="text-center font-medium text-gray-600">
                    {day}
                  </div>
                ))}
              </div>

              {/* Grade do Calendário */}
              <div className="grid grid-cols-7 gap-2">
                {getDaysInMonth(currentDate).map((dayInfo, index) => {
                  const dayEvents = getEventsForDate(dayInfo.date);
                  const isToday = dayInfo.date.toDateString() === new Date().toDateString();
                  
                  return (
                    <div
                      key={index}
                      className={cn(
                        "min-h-[100px] p-2 border rounded-lg",
                        dayInfo.isCurrentMonth ? "bg-white" : "bg-gray-50",
                        isToday && "border-purple-500 bg-purple-50"
                      )}
                    >
                      <div className={cn(
                        "text-sm font-medium mb-2",
                        dayInfo.isCurrentMonth ? "text-gray-900" : "text-gray-400",
                        isToday && "text-purple-600"
                      )}>
                        {dayInfo.date.getDate()}
                      </div>
                      
                      <div className="space-y-1">
                        {dayEvents.map(event => (
                          <div
                            key={event.id}
                            className={cn(
                              "text-xs p-1 rounded text-white truncate",
                              event.status === 'CONFIRMADO' ? 'bg-green-500' : 'bg-blue-500'
                            )}
                          >
                            {event.title}
                          </div>
                        ))}
                      </div>
                    </div>
                  );
                })}
              </div>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  );
};

export default PersonalCalendar;
