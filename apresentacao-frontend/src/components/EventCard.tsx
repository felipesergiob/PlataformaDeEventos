
import React, { useState } from 'react';
import { Card, CardContent } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Calendar, MapPin, Clock, DollarSign, Heart, Star, User, UserCheck, CalendarX } from 'lucide-react';
import { Link } from 'react-router-dom';
import { cn } from '@/lib/utils';

interface Event {
  id: number;
  title: string;
  description: string;
  date: string;
  time: string;
  location: string;
  genre: string;
  price: number;
  image?: string;
  organizer: string;
  participants: number;
  rating: number;
  isSaved: boolean;
  attending: 'not_going' | 'maybe' | 'confirmed';
}

interface EventCardProps {
  event: Event;
}

const EventCard = ({ event }: EventCardProps) => {
  const [isSaved, setIsSaved] = useState(event.isSaved);
  const [attending, setAttending] = useState<'not_going' | 'maybe' | 'confirmed'>(event.attending);

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: 'short',
    });
  };

  const handleSaveToggle = (e: React.MouseEvent) => {
    e.preventDefault(); // Prevent navigation
    setIsSaved(!isSaved);
  };

  const handleAttendance = (status: 'not_going' | 'maybe' | 'confirmed', e: React.MouseEvent) => {
    e.preventDefault(); // Prevent navigation
    setAttending(status);
  };

  return (
    <Link to={`/event/${event.id}`}>
      <Card className="h-full overflow-hidden transition-shadow hover:shadow-md flex flex-col">
        <div className="relative h-48 bg-gradient-to-r from-purple-500 to-purple-700 overflow-hidden">
          {event.image && (
            <img
              src={event.image}
              alt={event.title}
              className="w-full h-full object-cover opacity-75"
            />
          )}
          <div className="absolute top-3 right-3">
            <Button
              variant="ghost"
              size="icon"
              className="rounded-full bg-white/70 hover:bg-white text-gray-700 h-8 w-8"
              onClick={handleSaveToggle}
            >
              <Heart
                className={cn("h-5 w-5", isSaved && "fill-red-500 text-red-500")}
              />
            </Button>
          </div>
          <div className="absolute bottom-3 left-3">
            <Badge className="bg-purple-700 text-white">{event.genre}</Badge>
          </div>
        </div>

        <CardContent className="flex-1 flex flex-col p-4">
          <div className="flex-1">
            <h3 className="font-semibold text-lg text-gray-900 mb-2 line-clamp-2">{event.title}</h3>
            <p className="text-gray-600 text-sm mb-3 line-clamp-2">{event.description}</p>

            <div className="space-y-2 text-sm">
              <div className="flex items-center text-gray-600">
                <Calendar className="w-4 h-4 mr-2 text-purple-600" />
                <span>{formatDate(event.date)}</span>
                <span className="mx-1">•</span>
                <Clock className="w-4 h-4 mr-1 text-purple-600" />
                <span>{event.time}</span>
              </div>

              <div className="flex items-center text-gray-600">
                <MapPin className="w-4 h-4 mr-2 text-purple-600" />
                <span className="truncate">{event.location}</span>
              </div>

              <div className="flex items-center justify-between text-gray-600">
                <div className="flex items-center">
                  <DollarSign className="w-4 h-4 mr-1 text-purple-600" />
                  <span>{event.price === 0 ? 'Grátis' : `R$ ${event.price.toFixed(2)}`}</span>
                </div>
                <div className="flex items-center">
                  <Star className="w-4 h-4 mr-1 text-yellow-500 fill-current" />
                  <span>{event.rating}</span>
                </div>
              </div>
            </div>
          </div>

          <div className="border-t border-gray-100 mt-4 pt-4">
            <div className="flex items-center justify-between">
              <div className="text-sm text-gray-600">
                <User className="w-4 h-4 inline mr-1" />
                <span>{event.organizer}</span>
              </div>
              <div className="flex space-x-1">
                <Button
                  size="sm"
                  variant="ghost"
                  className={cn(
                    "h-8 px-2",
                    attending === 'confirmed' && "text-green-600",
                    attending === 'maybe' && "text-blue-600",
                    attending === 'not_going' && "text-gray-500"
                  )}
                  onClick={(e) => handleAttendance('confirmed', e)}
                >
                  <UserCheck className={cn(
                    "h-4 w-4", 
                    attending === 'confirmed' && "fill-green-100"
                  )} />
                </Button>
                <Button
                  size="sm"
                  variant="ghost"
                  className={attending === 'maybe' ? "text-blue-600" : "text-gray-500"}
                  onClick={(e) => handleAttendance('maybe', e)}
                >
                  ?
                </Button>
                <Button
                  size="sm"
                  variant="ghost"
                  className={attending === 'not_going' ? "text-red-600" : "text-gray-500"}
                  onClick={(e) => handleAttendance('not_going', e)}
                >
                  <CalendarX className="h-4 w-4" />
                </Button>
              </div>
            </div>
          </div>
        </CardContent>
      </Card>
    </Link>
  );
};

export default EventCard;
