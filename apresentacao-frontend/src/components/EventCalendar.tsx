import { useState } from "react";
import { Calendar } from "@/components/ui/calendar";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { CalendarIcon, Clock, MapPin } from "lucide-react";
import { Event } from "@/components/EventCard";

interface EventCalendarProps {
  events: Event[];
}

const EventCalendar = ({ events }: EventCalendarProps) => {
  const [selectedDate, setSelectedDate] = useState<Date | undefined>(new Date());

  // Convert event dates to Date objects and create a map
  const eventDates = events.reduce((acc, event) => {
    const date = new Date(event.date);
    const dateString = date.toDateString();
    if (!acc[dateString]) {
      acc[dateString] = [];
    }
    acc[dateString].push(event);
    return acc;
  }, {} as Record<string, Event[]>);

  // Get events for selected date
  const selectedDateEvents = selectedDate ? eventDates[selectedDate.toDateString()] || [] : [];

  // Get all event dates for highlighting
  const eventDateObjects = Object.keys(eventDates).map(dateString => new Date(dateString));

  const getCategoryColor = (category: string) => {
    const colors = {
      tecnologia: "bg-blue-100 text-blue-800",
      musica: "bg-pink-100 text-pink-800",
      esportes: "bg-green-100 text-green-800",
      gastronomia: "bg-orange-100 text-orange-800",
      arte: "bg-purple-100 text-purple-800",
      negocios: "bg-gray-100 text-gray-800",
    };
    return colors[category as keyof typeof colors] || "bg-gray-100 text-gray-800";
  };

  return (
    <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
      {/* Calendar */}
      <Card className="border-purple-200">
        <CardHeader>
          <CardTitle className="flex items-center space-x-2">
            <CalendarIcon className="w-5 h-5 text-purple-600" />
            <span>Calendário de Eventos</span>
          </CardTitle>
        </CardHeader>
        <CardContent>
          <Calendar
            mode="single"
            selected={selectedDate}
            onSelect={setSelectedDate}
            className="rounded-md border border-purple-200 pointer-events-auto"
            modifiers={{
              hasEvent: eventDateObjects,
            }}
            modifiersClassNames={{
              hasEvent: "bg-purple-100 text-purple-800 font-bold",
            }}
          />
        </CardContent>
      </Card>

      {/* Events for selected date */}
      <Card className="border-purple-200">
        <CardHeader>
          <CardTitle className="flex items-center space-x-2">
            <Clock className="w-5 h-5 text-purple-600" />
            <span>
              Eventos em {selectedDate?.toLocaleDateString("pt-BR") || "data selecionada"}
            </span>
          </CardTitle>
        </CardHeader>
        <CardContent>
          {selectedDateEvents.length === 0 ? (
            <div className="text-center py-8">
              <div className="w-16 h-16 bg-gradient-to-br from-purple-100 to-purple-200 rounded-full mx-auto mb-4 flex items-center justify-center">
                <CalendarIcon className="w-8 h-8 text-purple-500" />
              </div>
              <p className="text-muted-foreground">
                Nenhum evento nesta data
              </p>
            </div>
          ) : (
            <div className="space-y-4">
              {selectedDateEvents.map((event) => (
                <div key={event.id} className="border border-purple-100 rounded-lg p-4 hover:shadow-md transition-shadow">
                  <div className="flex justify-between items-start mb-2">
                    <h3 className="font-semibold text-gray-900 text-sm">{event.title}</h3>
                    <Badge className={getCategoryColor(event.category)}>
                      {event.category}
                    </Badge>
                  </div>
                  <div className="space-y-1 text-sm text-muted-foreground">
                    <div className="flex items-center">
                      <Clock className="w-3 h-3 mr-1" />
                      {event.time}
                    </div>
                    <div className="flex items-center">
                      <MapPin className="w-3 h-3 mr-1" />
                      {event.location}
                    </div>
                  </div>
                  <p className="text-xs text-muted-foreground mt-2 line-clamp-2">
                    {event.description}
                  </p>
                </div>
              ))}
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  );
};

export default EventCalendar;
