
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Calendar, MapPin, Users, Clock, Star } from "lucide-react";
import { Event } from "@/components/EventCard";
import { toast } from "@/hooks/use-toast";

interface FeaturedEventsProps {
  events: Event[];
  onRegister: (eventId: string) => void;
  registeredEventIds: string[];
}

const FeaturedEvents = ({ events, onRegister, registeredEventIds }: FeaturedEventsProps) => {
  const handleRegister = (eventId: string) => {
    onRegister(eventId);
    toast({
      title: "Inscrição realizada!",
      description: "Você se inscreveu no evento em destaque.",
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
    };
    return colors[category as keyof typeof colors] || "bg-gray-100 text-gray-800";
  };

  return (
    <div className="mb-12">
      <div className="text-center mb-8">
        <div className="flex items-center justify-center space-x-2 mb-4">
          <Star className="w-6 h-6 text-yellow-500 fill-yellow-500" />
          <h2 className="text-3xl font-bold text-gradient">Eventos em Destaque</h2>
          <Star className="w-6 h-6 text-yellow-500 fill-yellow-500" />
        </div>
        <p className="text-muted-foreground">
          Os eventos mais populares e imperdíveis da semana
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {events.map((event) => (
          <Card key={event.id} className="overflow-hidden group hover-lift border-purple-200 relative">
            <div className="absolute top-3 left-3 z-10">
              <Badge className="bg-yellow-500 text-white font-semibold">
                <Star className="w-3 h-3 mr-1 fill-white" />
                Destaque
              </Badge>
            </div>
            
            <div className="relative h-48 overflow-hidden">
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
              <div className="absolute bottom-3 right-3">
                <Badge className={getCategoryColor(event.category)}>
                  {event.category}
                </Badge>
              </div>
            </div>

            <CardContent className="p-6 space-y-4">
              <div>
                <h3 className="font-bold text-xl text-gray-900 group-hover:text-purple-600 transition-colors line-clamp-2">
                  {event.title}
                </h3>
                <p className="text-muted-foreground text-sm mt-2 line-clamp-2">
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
                  {event.enrolled}/{event.capacity} participantes
                </div>
              </div>

              <div className="flex items-center justify-between pt-4 border-t border-purple-100">
                <div className="text-lg font-bold text-purple-600">
                  {formatPrice(event.price)}
                </div>
                {registeredEventIds.includes(event.id) ? (
                  <Button disabled className="bg-green-100 text-green-800 border-green-200">
                    ✓ Inscrito
                  </Button>
                ) : (
                  <Button
                    onClick={() => handleRegister(event.id)}
                    className="btn-purple"
                  >
                    Inscrever-se
                  </Button>
                )}
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
};

export default FeaturedEvents;
