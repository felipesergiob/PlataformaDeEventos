import { useState, useEffect } from "react";
import Header from "@/components/Header";
import EventFilters, { FilterState } from "@/components/EventFilters";
import EventCard, { Event } from "@/components/EventCard";
import CreateEventModal from "@/components/CreateEventModal";
import MyEventsPage from "@/components/MyEventsPage";
import ProfilePage from "@/components/ProfilePage";
import ReportsPage from "@/components/ReportsPage";
import SocialFeed from "@/components/SocialFeed";
import FeaturedEvents from "@/components/FeaturedEvents";
import { Button } from "@/components/ui/button";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { ArrowUp } from "lucide-react";

const Index = () => {
  const [currentPage, setCurrentPage] = useState("home");
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showScrollTop, setShowScrollTop] = useState(false);
  const [postsFilters, setPostsFilters] = useState<FilterState>({
    search: "",
    category: "",
    priceRange: "",
    date: "",
    time: "",
    location: "",
  });
  
  // Mock data for events - agora focado em shows e festas
  const [allEvents, setAllEvents] = useState<Event[]>([
    {
      id: "1",
      title: "Festival de Rock Nacional",
      description: "Uma noite épica com as melhores bandas de rock nacional. Venha curtir muito som e energia!",
      category: "musica",
      price: 120,
      location: "Estádio do Morumbi, São Paulo, SP",
      date: "2024-06-15",
      time: "20:00",
      image: "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=800&h=600&fit=crop",
      capacity: 50000,
      enrolled: 32000,
      isFree: false,
    },
    {
      id: "2",
      title: "Festa Eletrônica Underground",
      description: "A melhor festa eletrônica da cidade com DJs internacionais e pista de dança incrível.",
      category: "musica",
      price: 80,
      location: "Club Vibe, Rio de Janeiro, RJ",
      date: "2024-06-20",
      time: "23:00",
      image: "https://images.unsplash.com/photo-1605810230434-7631ac76ec81?w=800&h=600&fit=crop",
      capacity: 2000,
      enrolled: 1850,
      isFree: false,
    },
    {
      id: "3",
      title: "Show de Samba e Pagode",
      description: "Uma roda de samba autêntica com os melhores sambistas da região. Diversão garantida!",
      category: "musica",
      price: 60,
      location: "Casa de Shows Vila Madalena, São Paulo, SP",
      date: "2024-07-10",
      time: "21:00",
      image: "https://images.unsplash.com/photo-1500673922987-e212871fec22?w=800&h=600&fit=crop",
      capacity: 800,
      enrolled: 650,
      isFree: false,
    },
    {
      id: "4",
      title: "Festa Junina Tradicional",
      description: "Festa junina com quadrilha, comidas típicas, fogueira e muita diversão para toda família.",
      category: "festa",
      price: 0,
      location: "Praça Central, Campinas, SP",
      date: "2024-06-25",
      time: "19:00",
      image: "https://images.unsplash.com/photo-1500375592092-40eb2168fd21?w=800&h=600&fit=crop",
      capacity: 5000,
      enrolled: 3200,
      isFree: true,
    },
    {
      id: "5",
      title: "Baile Funk da Virada",
      description: "O maior baile funk da cidade com os MCs mais famosos. Prepare-se para dançar até o amanhecer!",
      category: "festa",
      price: 40,
      location: "Quadra da Escola, Rio de Janeiro, RJ",
      date: "2024-07-05",
      time: "22:00",
      image: "https://images.unsplash.com/photo-1470813740244-df37b8c1edcb?w=800&h=600&fit=crop",
      capacity: 3000,
      enrolled: 2800,
      isFree: false,
    },
    {
      id: "6",
      title: "Festival de Jazz ao Ar Livre",
      description: "Uma noite mágica de jazz com vista para a cidade. Músicos de renome internacional.",
      category: "musica",
      price: 150,
      location: "Terraço Copacabana, Rio de Janeiro, RJ",
      date: "2024-08-15",
      time: "19:30",
      image: "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=800&h=600&fit=crop",
      capacity: 500,
      enrolled: 380,
      isFree: false,
    },
  ]);

  const [filteredEvents, setFilteredEvents] = useState<Event[]>(allEvents);
  const [filteredFeaturedEvents, setFilteredFeaturedEvents] = useState<Event[]>(allEvents.slice(0, 3));
  const [registeredEventIds, setRegisteredEventIds] = useState<string[]>([]);
  const [createdEvents, setCreatedEvents] = useState<Event[]>([]);

  // Handle scroll to top button
  useEffect(() => {
    const handleScroll = () => {
      setShowScrollTop(window.scrollY > 400);
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  const handleFilterChange = (filters: FilterState) => {
    let filtered = [...allEvents];

    // Text search
    if (filters.search) {
      filtered = filtered.filter(event =>
        event.title.toLowerCase().includes(filters.search.toLowerCase()) ||
        event.description.toLowerCase().includes(filters.search.toLowerCase()) ||
        event.location.toLowerCase().includes(filters.search.toLowerCase())
      );
    }

    // Category filter
    if (filters.category) {
      filtered = filtered.filter(event => event.category === filters.category);
    }

    // Price range filter
    if (filters.priceRange) {
      filtered = filtered.filter(event => {
        if (filters.priceRange === "free") return event.price === 0;
        if (filters.priceRange === "0-50") return event.price >= 0 && event.price <= 50;
        if (filters.priceRange === "50-100") return event.price > 50 && event.price <= 100;
        if (filters.priceRange === "100-200") return event.price > 100 && event.price <= 200;
        if (filters.priceRange === "200+") return event.price > 200;
        return true;
      });
    }

    // Location filter
    if (filters.location) {
      filtered = filtered.filter(event =>
        event.location.toLowerCase().includes(filters.location.toLowerCase())
      );
    }

    // Date filter
    if (filters.date) {
      const today = new Date();
      const tomorrow = new Date(today);
      tomorrow.setDate(tomorrow.getDate() + 1);

      filtered = filtered.filter(event => {
        const eventDate = new Date(event.date);
        if (filters.date === "today") {
          return eventDate.toDateString() === today.toDateString();
        }
        if (filters.date === "tomorrow") {
          return eventDate.toDateString() === tomorrow.toDateString();
        }
        if (filters.date === "week") {
          const weekFromNow = new Date(today);
          weekFromNow.setDate(weekFromNow.getDate() + 7);
          return eventDate >= today && eventDate <= weekFromNow;
        }
        return true;
      });
    }

    // Time filter
    if (filters.time) {
      filtered = filtered.filter(event => {
        const eventHour = parseInt(event.time.split(':')[0]);
        if (filters.time === "manha") return eventHour >= 6 && eventHour < 12;
        if (filters.time === "tarde") return eventHour >= 12 && eventHour < 18;
        if (filters.time === "noite") return eventHour >= 18 && eventHour < 24;
        if (filters.time === "madrugada") return eventHour >= 0 && eventHour < 6;
        return true;
      });
    }

    setFilteredEvents(filtered);
  };

  const handleFeaturedFilterChange = (filters: FilterState) => {
    let filtered = [...allEvents];

    // Text search
    if (filters.search) {
      filtered = filtered.filter(event =>
        event.title.toLowerCase().includes(filters.search.toLowerCase()) ||
        event.description.toLowerCase().includes(filters.search.toLowerCase()) ||
        event.location.toLowerCase().includes(filters.search.toLowerCase())
      );
    }

    // Category filter
    if (filters.category) {
      filtered = filtered.filter(event => event.category === filters.category);
    }

    // Price range filter
    if (filters.priceRange) {
      filtered = filtered.filter(event => {
        if (filters.priceRange === "free") return event.price === 0;
        if (filters.priceRange === "0-50") return event.price >= 0 && event.price <= 50;
        if (filters.priceRange === "50-100") return event.price > 50 && event.price <= 100;
        if (filters.priceRange === "100-200") return event.price > 100 && event.price <= 200;
        if (filters.priceRange === "200+") return event.price > 200;
        return true;
      });
    }

    // Location filter
    if (filters.location) {
      filtered = filtered.filter(event =>
        event.location.toLowerCase().includes(filters.location.toLowerCase())
      );
    }

    // Date filter
    if (filters.date) {
      const today = new Date();
      const tomorrow = new Date(today);
      tomorrow.setDate(tomorrow.getDate() + 1);

      filtered = filtered.filter(event => {
        const eventDate = new Date(event.date);
        if (filters.date === "today") {
          return eventDate.toDateString() === today.toDateString();
        }
        if (filters.date === "tomorrow") {
          return eventDate.toDateString() === tomorrow.toDateString();
        }
        if (filters.date === "week") {
          const weekFromNow = new Date(today);
          weekFromNow.setDate(weekFromNow.getDate() + 7);
          return eventDate >= today && eventDate <= weekFromNow;
        }
        return true;
      });
    }

    // Time filter
    if (filters.time) {
      filtered = filtered.filter(event => {
        const eventHour = parseInt(event.time.split(':')[0]);
        if (filters.time === "manha") return eventHour >= 6 && eventHour < 12;
        if (filters.time === "tarde") return eventHour >= 12 && eventHour < 18;
        if (filters.time === "noite") return eventHour >= 18 && eventHour < 24;
        if (filters.time === "madrugada") return eventHour >= 0 && eventHour < 6;
        return true;
      });
    }

    setFilteredFeaturedEvents(filtered);
  };

  const handleRegisterEvent = (eventId: string) => {
    if (!registeredEventIds.includes(eventId)) {
      setRegisteredEventIds(prev => [...prev, eventId]);
      
      // Update enrollment count
      setAllEvents(prev => prev.map(event => 
        event.id === eventId 
          ? { ...event, enrolled: event.enrolled + 1 }
          : event
      ));
    }
  };

  const handleUnregisterEvent = (eventId: string) => {
    setRegisteredEventIds(prev => prev.filter(id => id !== eventId));
    
    // Update enrollment count
    setAllEvents(prev => prev.map(event => 
      event.id === eventId 
        ? { ...event, enrolled: Math.max(0, event.enrolled - 1) }
        : event
    ));
  };

  const handleCreateEvent = (newEvent: Event) => {
    setAllEvents(prev => [...prev, newEvent]);
    setCreatedEvents(prev => [...prev, newEvent]);
    setFilteredEvents(prev => [...prev, newEvent]);
  };

  const getRegisteredEvents = () => {
    return allEvents.filter(event => registeredEventIds.includes(event.id));
  };

  const renderContent = () => {
    switch (currentPage) {
      case "my-events":
        return (
          <MyEventsPage
            registeredEvents={getRegisteredEvents()}
            createdEvents={createdEvents}
            onUnregister={handleUnregisterEvent}
          />
        );
      case "profile":
        return <ProfilePage registeredEvents={getRegisteredEvents()} />;
      case "reports":
        return <ReportsPage createdEvents={createdEvents} registeredEventIds={registeredEventIds} />;
      default:
        return (
          <div className="container mx-auto px-4 py-8">
            {/* Hero Section */}
            <div className="text-center mb-12">
              <div className="relative">
                <h1 className="text-5xl md:text-6xl font-bold mb-4">
                  <span className="text-gradient">Se Liga AI</span>
                </h1>
                <p className="text-xl text-muted-foreground mb-8 max-w-2xl mx-auto">
                  A rede social de eventos que conecta pessoas através de shows, festas e experiências inesquecíveis
                </p>
                <div className="absolute -top-4 -right-4 w-24 h-24 bg-gradient-to-r from-purple-400 to-pink-400 rounded-full opacity-20 animate-pulse" />
                <div className="absolute -bottom-4 -left-4 w-32 h-32 bg-gradient-to-r from-blue-400 to-purple-400 rounded-full opacity-20 animate-pulse delay-1000" />
              </div>
            </div>

            {/* Main Content Tabs */}
            <Tabs defaultValue="posts" className="w-full">
              <TabsList className="grid w-full grid-cols-3 mb-8">
                <TabsTrigger value="posts" className="text-lg">Postagens</TabsTrigger>
                <TabsTrigger value="events" className="text-lg">Eventos</TabsTrigger>
                <TabsTrigger value="featured" className="text-lg">Em Destaque</TabsTrigger>
              </TabsList>
              
              <TabsContent value="posts" className="space-y-6">
                {/* Filters for Posts */}
                <div className="mb-8">
                  <EventFilters onFilterChange={setPostsFilters} />
                </div>
                <SocialFeed events={allEvents} filters={postsFilters} />
              </TabsContent>
              
              <TabsContent value="events" className="space-y-6">
                {/* Filters */}
                <div className="mb-8">
                  <EventFilters onFilterChange={handleFilterChange} />
                </div>

                {/* Events Grid */}
                <div>
                  <div className="flex items-center justify-between mb-6">
                    <h2 className="text-2xl font-bold text-gray-900">
                      Todos os eventos
                    </h2>
                    <span className="text-muted-foreground">
                      {filteredEvents.length} evento{filteredEvents.length !== 1 ? "s" : ""} encontrado{filteredEvents.length !== 1 ? "s" : ""}
                    </span>
                  </div>

                  {filteredEvents.length === 0 ? (
                    <div className="text-center py-16">
                      <div className="w-24 h-24 bg-gradient-to-br from-purple-100 to-purple-200 rounded-full mx-auto mb-4 flex items-center justify-center">
                        <span className="text-2xl">🔍</span>
                      </div>
                      <h3 className="text-xl font-semibold mb-2">Nenhum evento encontrado</h3>
                      <p className="text-muted-foreground">
                        Tente ajustar os filtros para encontrar eventos
                      </p>
                    </div>
                  ) : (
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                      {filteredEvents.map((event) => (
                        <EventCard
                          key={event.id}
                          event={event}
                          onRegister={handleRegisterEvent}
                          isRegistered={registeredEventIds.includes(event.id)}
                        />
                      ))}
                    </div>
                  )}
                </div>
              </TabsContent>

              <TabsContent value="featured" className="space-y-6">
                {/* Filters for Featured Events */}
                <div className="mb-8">
                  <EventFilters onFilterChange={handleFeaturedFilterChange} />
                </div>

                {/* Featured Events */}
                <div>
                  <FeaturedEvents 
                    events={filteredFeaturedEvents} 
                    onRegister={handleRegisterEvent} 
                    registeredEventIds={registeredEventIds} 
                  />
                </div>
              </TabsContent>
            </Tabs>
          </div>
        );
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 via-white to-purple-50">
      <Header
        onCreateEvent={() => setShowCreateModal(true)}
        onNavigate={setCurrentPage}
        currentPage={currentPage}
      />
      
      <main className="min-h-screen">
        {renderContent()}
      </main>

      {/* Create Event Modal */}
      <CreateEventModal
        isOpen={showCreateModal}
        onClose={() => setShowCreateModal(false)}
        onCreateEvent={handleCreateEvent}
      />

      {/* Scroll to Top Button */}
      {showScrollTop && (
        <Button
          onClick={scrollToTop}
          className="fixed bottom-6 right-6 btn-purple rounded-full p-3 shadow-lg z-50"
          size="sm"
        >
          <ArrowUp className="w-5 h-5" />
        </Button>
      )}
    </div>
  );
};

export default Index;
