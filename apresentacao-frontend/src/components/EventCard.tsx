
import { useState } from "react";
import { Card, CardContent, CardFooter } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Calendar, MapPin, Users, Clock, Heart } from "lucide-react";
import { toast } from "@/hooks/use-toast";
import EventComments from "@/components/EventComments";

export interface Event {
  id: string;
  title: string;
  description: string;
  category: string;
  price: number;
  location: string;
  date: string;
  time: string;
  image: string;
  capacity: number;
  enrolled: number;
  isFree: boolean;
}

interface EventCardProps {
  event: Event;
  onRegister?: (eventId: string) => void;
  isRegistered?: boolean;
  showRegisterButton?: boolean;
}

const EventCard = ({ event, onRegister, isRegistered = false, showRegisterButton = true }: EventCardProps) => {
  const [isFavorited, setIsFavorited] = useState(false);
  const [isRegistering, setIsRegistering] = useState(false);

  const handleRegister = async () => {
    if (!onRegister) return;
    
    setIsRegistering(true);
    
    // Simulate API call
    setTimeout(() => {
      onRegister(event.id);
      setIsRegistering(false);
      toast({
        title: "Inscrição realizada!",
        description: `Você se inscreveu no evento "${event.title}".`,
      });
    }, 1000);
  };

  const handleFavorite = () => {
    setIsFavorited(!isFavorited);
    toast({
      title: isFavorited ? "Removido dos favoritos" : "Adicionado aos favoritos",
      description: `Evento "${event.title}" ${isFavorited ? "removido" : "adicionado"}.`,
    });
  };

  const formatPrice = (price: number) => {
    if (price === 0) return "Gratuito";
    return new Intl.NumberFormat("pt-BR", {
      style: "currency",
      currency: "BRL",
    }).format(price);
  };

  const getCategoryColor = (category: string) => {
    const colors = {
      musica: "bg-pink-100 text-pink-800",
      festa: "bg-purple-100 text-purple-800",
      show: "bg-blue-100 text-blue-800",
      rock: "bg-red-100 text-red-800",
      pop: "bg-yellow-100 text-yellow-800",
      eletronica: "bg-cyan-100 text-cyan-800",
      samba: "bg-orange-100 text-orange-800",
      funk: "bg-green-100 text-green-800",
      jazz: "bg-indigo-100 text-indigo-800",
    };
    return colors[category as keyof typeof colors] || "bg-gray-100 text-gray-800";
  };

  const isFullyBooked = event.enrolled >= event.capacity;

  return (
    <div className="space-y-4">
      <Card className="hover-lift border-purple-100 overflow-hidden group">
        <div className="relative">
          <div className="h-48 relative overflow-hidden">
            {event.image ? (
              <img 
                src={event.image} 
                alt={event.title}
                className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
              />
            ) : (
              <div className="h-full bg-gradient-to-br from-purple-400 via-purple-500 to-purple-600" />
            )}
            <div className="absolute inset-0 bg-black/20" />
            <div className="absolute top-3 left-3">
              <Badge className={getCategoryColor(event.category)}>
                {event.category}
              </Badge>
            </div>
            <div className="absolute top-3 right-3">
              <Button
                variant="ghost"
                size="sm"
                onClick={handleFavorite}
                className="bg-white/20 backdrop-blur-sm hover:bg-white/30 text-white"
              >
                <Heart className={`w-4 h-4 ${isFavorited ? "fill-red-500 text-red-500" : ""}`} />
              </Button>
            </div>
            <div className="absolute bottom-3 left-3">
              <Badge className="bg-white/90 text-purple-800 font-semibold">
                {formatPrice(event.price)}
              </Badge>
            </div>
          </div>
        </div>

        <CardContent className="p-4 space-y-3">
          <div>
            <h3 className="font-bold text-lg text-gray-900 group-hover:text-purple-600 transition-colors line-clamp-2">
              {event.title}
            </h3>
            <p className="text-muted-foreground text-sm mt-1 line-clamp-2">
              {event.description}
            </p>
          </div>

          <div className="space-y-2">
            <div className="flex items-center text-sm text-muted-foreground">
              <Calendar className="w-4 h-4 mr-2 text-purple-500" />
              {event.date}
            </div>
            <div className="flex items-center text-sm text-muted-foreground">
              <Clock className="w-4 h-4 mr-2 text-purple-500" />
              {event.time}
            </div>
            <div className="flex items-center text-sm text-muted-foreground">
              <MapPin className="w-4 h-4 mr-2 text-purple-500" />
              {event.location}
            </div>
            <div className="flex items-center text-sm text-muted-foreground">
              <Users className="w-4 h-4 mr-2 text-purple-500" />
              {event.enrolled}/{event.capacity} inscritos
            </div>
          </div>

          {/* Progress bar for capacity */}
          <div className="w-full bg-gray-200 rounded-full h-2">
            <div
              className="bg-gradient-to-r from-purple-500 to-purple-600 h-2 rounded-full transition-all duration-300"
              style={{
                width: `${Math.min((event.enrolled / event.capacity) * 100, 100)}%`,
              }}
            />
          </div>
        </CardContent>

        {showRegisterButton && (
          <CardFooter className="p-4 pt-0">
            {isRegistered ? (
              <Button disabled className="w-full bg-green-100 text-green-800 border-green-200">
                ✓ Inscrito
              </Button>
            ) : isFullyBooked ? (
              <Button disabled className="w-full">
                Esgotado
              </Button>
            ) : (
              <Button
                onClick={handleRegister}
                disabled={isRegistering}
                className="w-full btn-purple"
              >
                {isRegistering ? "Inscrevendo..." : "Inscrever-se"}
              </Button>
            )}
          </CardFooter>
        )}
      </Card>

      {/* Event Comments */}
      <EventComments eventId={event.id} eventTitle={event.title} />
    </div>
  );
};

export default EventCard;
