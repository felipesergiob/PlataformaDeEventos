
import React, { useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { 
  Calendar as CalendarIcon, 
  ChevronLeft, 
  ChevronRight, 
  Clock, 
  MapPin,
  Filter,
  List,
  Grid
} from 'lucide-react';
import { cn } from '@/lib/utils';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';

const PersonalCalendar = () => {
  const [currentDate, setCurrentDate] = useState(new Date());
  const [viewMode, setViewMode] = useState<'month' | 'week' | 'day'>('month');
  const [layoutMode, setLayoutMode] = useState<'calendar' | 'list'>('calendar');

  // Mock events data with specific dates
  const events = [
    {
      id: 1,
      title: 'Workshop React Avançado',
      date: '2024-06-15',
      time: '19:00',
      location: 'Centro de Tecnologia',
      status: 'confirmed',
      type: 'saved'
    },
    {
      id: 2,
      title: 'Festival de Jazz',
      date: '2024-06-20',
      time: '20:00',
      location: 'Parque Ibirapuera',
      status: 'confirmed',
      type: 'confirmed'
    },
    {
      id: 3,
      title: 'Palestra IA',
      date: '2024-06-25',
      time: '14:00',
      location: 'USP',
      status: 'maybe',
      type: 'maybe'
    },
    {
      id: 4,
      title: 'Meetup Desenvolvedores',
      date: '2024-06-18',
      time: '18:30',
      location: 'Coworking Tech',
      status: 'confirmed',
      type: 'confirmed'
    },
    {
      id: 5,
      title: 'Workshop UX Design',
      date: '2024-06-05',
      time: '09:00',
      location: 'Design Studio',
      status: 'confirmed',
      type: 'confirmed'
    },
    {
      id: 6,
      title: 'Feira de Startups',
      date: '2024-06-12',
      time: '10:00',
      location: 'Centro de Convenções',
      status: 'maybe',
      type: 'maybe'
    },
    {
      id: 7,
      title: 'Happy Hour Tech',
      date: '2024-06-19',
      time: '18:00',
      location: 'Pub Central',
      status: 'saved',
      type: 'saved'
    }
  ];

  const monthNames = [
    'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
    'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'
  ];

  const daysOfWeek = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'];

  const getDaysInMonth = (date: Date) => {
    const year = date.getFullYear();
    const month = date.getMonth();
    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    const daysInMonth = lastDay.getDate();
    const startingDayOfWeek = firstDay.getDay();

    const days = [];
    
    // Previous month's trailing days
    for (let i = startingDayOfWeek - 1; i >= 0; i--) {
      const prevDate = new Date(year, month, -i);
      days.push({ date: prevDate, isCurrentMonth: false });
    }
    
    // Current month's days
    for (let day = 1; day <= daysInMonth; day++) {
      days.push({ date: new Date(year, month, day), isCurrentMonth: true });
    }
    
    // Next month's leading days to fill the grid
    const remainingDays = 42 - days.length;
    for (let day = 1; day <= remainingDays; day++) {
      const nextDate = new Date(year, month + 1, day);
      days.push({ date: nextDate, isCurrentMonth: false });
    }
    
    return days;
  };

  const getEventsForDate = (date: Date) => {
    const dateString = format(date, 'yyyy-MM-dd');
    return events.filter(event => event.date === dateString);
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

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'confirmed': return 'bg-green-500';
      case 'maybe': return 'bg-yellow-500';
      case 'saved': return 'bg-blue-500';
      default: return 'bg-gray-500';
    }
  };

  const getStatusText = (status: string) => {
    switch (status) {
      case 'confirmed': return 'Confirmado';
      case 'maybe': return 'Talvez';
      case 'saved': return 'Salvo';
      default: return 'Indefinido';
    }
  };

  return (
    <div className="max-w-6xl mx-auto p-6">
      <div className="flex flex-col md:flex-row md:items-center justify-between mb-6">
        <h1 className="text-3xl font-bold text-gray-900 mb-4 md:mb-0">Meu Calendário</h1>
        
        <div className="flex items-center space-x-4">
          {/* View Mode Toggle */}
          <div className="flex items-center bg-gray-100 rounded-lg p-1">
            <Button
              variant={viewMode === 'month' ? 'default' : 'ghost'}
              size="sm"
              onClick={() => setViewMode('month')}
              className="rounded-md"
            >
              Mês
            </Button>
            <Button
              variant={viewMode === 'week' ? 'default' : 'ghost'}
              size="sm"
              onClick={() => setViewMode('week')}
              className="rounded-md"
            >
              Semana
            </Button>
            <Button
              variant={viewMode === 'day' ? 'default' : 'ghost'}
              size="sm"
              onClick={() => setViewMode('day')}
              className="rounded-md"
            >
              Dia
            </Button>
          </div>

          {/* Layout Toggle */}
          <div className="flex items-center bg-gray-100 rounded-lg p-1">
            <Button
              variant={layoutMode === 'calendar' ? 'default' : 'ghost'}
              size="sm"
              onClick={() => setLayoutMode('calendar')}
              className="rounded-md"
            >
              <Grid className="w-4 h-4" />
            </Button>
            <Button
              variant={layoutMode === 'list' ? 'default' : 'ghost'}
              size="sm"
              onClick={() => setLayoutMode('list')}
              className="rounded-md"
            >
              <List className="w-4 h-4" />
            </Button>
          </div>
        </div>
      </div>

      {/* Calendar Navigation */}
      <Card className="mb-6">
        <CardHeader>
          <div className="flex items-center justify-between">
            <Button variant="outline" size="sm" onClick={() => navigateMonth('prev')}>
              <ChevronLeft className="w-4 h-4" />
            </Button>
            
            <h2 className="text-xl font-semibold">
              {monthNames[currentDate.getMonth()]} {currentDate.getFullYear()}
            </h2>
            
            <Button variant="outline" size="sm" onClick={() => navigateMonth('next')}>
              <ChevronRight className="w-4 h-4" />
            </Button>
          </div>
        </CardHeader>
      </Card>

      {layoutMode === 'calendar' ? (
        /* Calendar View */
        <Card>
          <CardContent className="p-6">
            {/* Days of week header */}
            <div className="grid grid-cols-7 gap-2 mb-4">
              {daysOfWeek.map(day => (
                <div key={day} className="text-center font-medium text-gray-600 p-2">
                  {day}
                </div>
              ))}
            </div>

            {/* Calendar grid */}
            <div className="grid grid-cols-7 gap-2">
              {getDaysInMonth(currentDate).map((dayInfo, index) => {
                const dayEvents = getEventsForDate(dayInfo.date);
                const isToday = dayInfo.date.toDateString() === new Date().toDateString();
                
                return (
                  <div
                    key={index}
                    className={cn(
                      "min-h-[100px] p-2 border rounded-lg cursor-pointer hover:bg-gray-50 transition-colors",
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
                      {dayEvents.slice(0, 2).map(event => (
                        <div
                          key={event.id}
                          className={cn(
                            "text-xs p-1 rounded text-white truncate",
                            getStatusColor(event.status)
                          )}
                        >
                          {event.title}
                        </div>
                      ))}
                      {dayEvents.length > 2 && (
                        <div className="text-xs text-gray-500 pl-1">
                          +{dayEvents.length - 2} mais
                        </div>
                      )}
                    </div>
                  </div>
                );
              })}
            </div>
          </CardContent>
        </Card>
      ) : (
        /* List View */
        <div className="space-y-4">
          {events.map(event => (
            <Card key={event.id} className="hover:shadow-md transition-shadow">
              <CardContent className="p-6">
                <div className="flex items-center justify-between">
                  <div className="flex items-center space-x-4">
                    <div className={cn("w-4 h-4 rounded-full", getStatusColor(event.status))} />
                    <div>
                      <h3 className="font-semibold text-lg text-gray-900">{event.title}</h3>
                      <div className="flex items-center space-x-4 text-sm text-gray-600 mt-1">
                        <div className="flex items-center space-x-1">
                          <CalendarIcon className="w-4 h-4" />
                          <span>{format(new Date(event.date), 'dd/MM/yyyy')}</span>
                        </div>
                        <div className="flex items-center space-x-1">
                          <Clock className="w-4 h-4" />
                          <span>{event.time}</span>
                        </div>
                        <div className="flex items-center space-x-1">
                          <MapPin className="w-4 h-4" />
                          <span>{event.location}</span>
                        </div>
                      </div>
                    </div>
                  </div>
                  <Badge variant="secondary">
                    {getStatusText(event.status)}
                  </Badge>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
};

export default PersonalCalendar;
