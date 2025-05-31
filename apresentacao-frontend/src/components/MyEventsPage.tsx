
import { useState } from "react";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import EventCard, { Event } from "./EventCard";
import { Calendar, Users, MapPin, Clock, Edit, Trash2 } from "lucide-react";

interface MyEventsPageProps {
  registeredEvents: Event[];
  createdEvents: Event[];
  onUnregister?: (eventId: string) => void;
  onEditEvent?: (event: Event) => void;
  onDeleteEvent?: (eventId: string) => void;
}

const MyEventsPage = ({ 
  registeredEvents, 
  createdEvents, 
  onUnregister,
  onEditEvent,
  onDeleteEvent 
}: MyEventsPageProps) => {
  const [activeTab, setActiveTab] = useState("registered");

  const handleUnregister = (eventId: string) => {
    if (onUnregister) {
      onUnregister(eventId);
    }
  };

  const CreatedEventCard = ({ event }: { event: Event }) => (
    <Card className="hover-lift border-purple-100">
      <CardHeader className="pb-3">
        <div className="flex items-start justify-between">
          <div className="flex-1">
            <CardTitle className="text-lg text-purple-900">{event.title}</CardTitle>
            <p className="text-sm text-muted-foreground mt-1 line-clamp-2">
              {event.description}
            </p>
          </div>
          <div className="flex space-x-2 ml-4">
            <Button
              variant="outline"
              size="sm"
              onClick={() => onEditEvent?.(event)}
              className="border-purple-200 hover:bg-purple-50"
            >
              <Edit className="w-4 h-4" />
            </Button>
            <Button
              variant="outline"
              size="sm"
              onClick={() => onDeleteEvent?.(event.id)}
              className="border-red-200 hover:bg-red-50 text-red-600"
            >
              <Trash2 className="w-4 h-4" />
            </Button>
          </div>
        </div>
      </CardHeader>
      <CardContent className="space-y-3">
        <div className="flex items-center justify-between">
          <Badge className="bg-purple-100 text-purple-800">
            {event.category}
          </Badge>
          <Badge variant={event.price === 0 ? "secondary" : "default"}>
            {event.price === 0 ? "Gratuito" : `R$ ${event.price}`}
          </Badge>
        </div>
        
        <div className="grid grid-cols-2 gap-3 text-sm text-muted-foreground">
          <div className="flex items-center">
            <Calendar className="w-4 h-4 mr-2 text-purple-500" />
            {event.date}
          </div>
          <div className="flex items-center">
            <Clock className="w-4 h-4 mr-2 text-purple-500" />
            {event.time}
          </div>
          <div className="flex items-center">
            <MapPin className="w-4 h-4 mr-2 text-purple-500" />
            {event.location}
          </div>
          <div className="flex items-center">
            <Users className="w-4 h-4 mr-2 text-purple-500" />
            {event.enrolled}/{event.capacity}
          </div>
        </div>

        <div className="w-full bg-gray-200 rounded-full h-2">
          <div
            className="bg-gradient-to-r from-purple-500 to-purple-600 h-2 rounded-full"
            style={{
              width: `${Math.min((event.enrolled / event.capacity) * 100, 100)}%`,
            }}
          />
        </div>
      </CardContent>
    </Card>
  );

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gradient mb-2">Meus Eventos</h1>
        <p className="text-muted-foreground">
          Gerencie seus eventos e acompanhe suas inscrições
        </p>
      </div>

      <Tabs value={activeTab} onValueChange={setActiveTab} className="w-full">
        <TabsList className="grid w-full grid-cols-2 mb-8">
          <TabsTrigger value="registered" className="data-[state=active]:bg-purple-500 data-[state=active]:text-white">
            Eventos Inscritos ({registeredEvents.length})
          </TabsTrigger>
          <TabsTrigger value="created" className="data-[state=active]:bg-purple-500 data-[state=active]:text-white">
            Eventos Criados ({createdEvents.length})
          </TabsTrigger>
        </TabsList>
        
        <TabsContent value="registered" className="space-y-6">
          {registeredEvents.length === 0 ? (
            <Card className="text-center py-12">
              <CardContent>
                <Calendar className="w-16 h-16 mx-auto mb-4 text-muted-foreground" />
                <h3 className="text-xl font-semibold mb-2">Nenhum evento inscrito</h3>
                <p className="text-muted-foreground mb-4">
                  Você ainda não se inscreveu em nenhum evento.
                </p>
                <Button className="btn-purple">
                  Explorar Eventos
                </Button>
              </CardContent>
            </Card>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {registeredEvents.map((event) => (
                <div key={event.id} className="relative">
                  <EventCard
                    event={event}
                    isRegistered={true}
                    showRegisterButton={true}
                  />
                  <Button
                    variant="outline"
                    size="sm"
                    onClick={() => handleUnregister(event.id)}
                    className="absolute top-3 right-3 bg-white/90 backdrop-blur-sm border-red-200 text-red-600 hover:bg-red-50"
                  >
                    Cancelar inscrição
                  </Button>
                </div>
              ))}
            </div>
          )}
        </TabsContent>
        
        <TabsContent value="created" className="space-y-6">
          {createdEvents.length === 0 ? (
            <Card className="text-center py-12">
              <CardContent>
                <Calendar className="w-16 h-16 mx-auto mb-4 text-muted-foreground" />
                <h3 className="text-xl font-semibold mb-2">Nenhum evento criado</h3>
                <p className="text-muted-foreground mb-4">
                  Você ainda não criou nenhum evento.
                </p>
                <Button className="btn-purple">
                  Criar Primeiro Evento
                </Button>
              </CardContent>
            </Card>
          ) : (
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
              {createdEvents.map((event) => (
                <CreatedEventCard key={event.id} event={event} />
              ))}
            </div>
          )}
        </TabsContent>
      </Tabs>
    </div>
  );
};

export default MyEventsPage;
