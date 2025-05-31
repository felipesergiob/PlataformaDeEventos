
import { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { User, Calendar, Edit, Save, Camera, MapPin, Music, PartyPopper } from "lucide-react";
import { Event } from "@/components/EventCard";
import EventCalendar from "@/components/EventCalendar";
import { toast } from "@/hooks/use-toast";

interface ProfilePageProps {
  registeredEvents: Event[];
}

const ProfilePage = ({ registeredEvents }: ProfilePageProps) => {
  const [isEditing, setIsEditing] = useState(false);
  const [profileData, setProfileData] = useState({
    name: "Maria Silva",
    email: "maria.silva@email.com",
    bio: "Apaixonada por música e festas! Sempre em busca dos melhores eventos da cidade. Adoro descobrir novos artistas e compartilhar experiências incríveis com amigos.",
    location: "São Paulo, SP",
    favoriteGenres: ["Eletrônica", "Rock", "Samba"],
    eventsAttended: 24,
    friendsCount: 156,
  });

  const handleSave = () => {
    setIsEditing(false);
    toast({
      title: "Perfil atualizado!",
      description: "Suas informações foram salvas com sucesso.",
    });
  };

  const handleInputChange = (field: string, value: string) => {
    setProfileData(prev => ({ ...prev, [field]: value }));
  };

  const getCategoryIcon = (category: string) => {
    switch (category) {
      case "musica":
        return <Music className="w-4 h-4" />;
      case "festa":
        return <PartyPopper className="w-4 h-4" />;
      default:
        return <Calendar className="w-4 h-4" />;
    }
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
    <div className="container mx-auto px-4 py-8 max-w-6xl">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gradient mb-2">Meu Perfil</h1>
        <p className="text-muted-foreground">
          Gerencie suas informações pessoais e acompanhe seus eventos
        </p>
      </div>

      <Tabs defaultValue="info" className="w-full">
        <TabsList className="grid w-full grid-cols-2 mb-8">
          <TabsTrigger value="info" className="text-lg">Informações Pessoais</TabsTrigger>
          <TabsTrigger value="calendar" className="text-lg">Calendário de Eventos</TabsTrigger>
        </TabsList>

        <TabsContent value="info" className="space-y-6">
          {/* Profile Header */}
          <Card className="border-purple-200 bg-gradient-to-r from-purple-50 to-white">
            <CardContent className="p-6">
              <div className="flex items-start space-x-6">
                <div className="relative">
                  <Avatar className="w-24 h-24">
                    <AvatarFallback className="bg-gradient-to-br from-purple-400 to-purple-600 text-white text-2xl">
                      {profileData.name.split(' ').map(n => n[0]).join('')}
                    </AvatarFallback>
                  </Avatar>
                  <Button
                    size="sm"
                    className="absolute -bottom-2 -right-2 rounded-full w-8 h-8 p-0 bg-purple-600 hover:bg-purple-700"
                  >
                    <Camera className="w-4 h-4 text-white" />
                  </Button>
                </div>
                
                <div className="flex-1">
                  <div className="flex items-center justify-between mb-4">
                    <div>
                      <h2 className="text-2xl font-bold text-gray-900">{profileData.name}</h2>
                      <div className="flex items-center text-muted-foreground mt-1">
                        <MapPin className="w-4 h-4 mr-1" />
                        {profileData.location}
                      </div>
                    </div>
                    <Button
                      onClick={isEditing ? handleSave : () => setIsEditing(true)}
                      className="btn-purple"
                    >
                      {isEditing ? (
                        <>
                          <Save className="w-4 h-4 mr-2" />
                          Salvar
                        </>
                      ) : (
                        <>
                          <Edit className="w-4 h-4 mr-2" />
                          Editar
                        </>
                      )}
                    </Button>
                  </div>
                  
                  <div className="grid grid-cols-3 gap-6 mb-4">
                    <div className="text-center">
                      <div className="text-2xl font-bold text-purple-600">{registeredEvents.length}</div>
                      <div className="text-sm text-muted-foreground">Eventos Inscritos</div>
                    </div>
                    <div className="text-center">
                      <div className="text-2xl font-bold text-purple-600">{profileData.eventsAttended}</div>
                      <div className="text-sm text-muted-foreground">Eventos Participados</div>
                    </div>
                    <div className="text-center">
                      <div className="text-2xl font-bold text-purple-600">{profileData.friendsCount}</div>
                      <div className="text-sm text-muted-foreground">Amigos</div>
                    </div>
                  </div>

                  <p className="text-gray-700">{profileData.bio}</p>
                </div>
              </div>
            </CardContent>
          </Card>

          {/* Personal Information */}
          <Card className="border-purple-200">
            <CardHeader>
              <CardTitle className="flex items-center space-x-2">
                <User className="w-5 h-5 text-purple-600" />
                <span>Informações Pessoais</span>
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="name">Nome Completo</Label>
                  <Input
                    id="name"
                    value={profileData.name}
                    onChange={(e) => handleInputChange('name', e.target.value)}
                    disabled={!isEditing}
                    className="border-purple-200"
                  />
                </div>
                
                <div className="space-y-2">
                  <Label htmlFor="email">Email</Label>
                  <Input
                    id="email"
                    type="email"
                    value={profileData.email}
                    onChange={(e) => handleInputChange('email', e.target.value)}
                    disabled={!isEditing}
                    className="border-purple-200"
                  />
                </div>
              </div>
              
              <div className="space-y-2">
                <Label htmlFor="location">Localização</Label>
                <Input
                  id="location"
                  value={profileData.location}
                  onChange={(e) => handleInputChange('location', e.target.value)}
                  disabled={!isEditing}
                  className="border-purple-200"
                />
              </div>
              
              <div className="space-y-2">
                <Label htmlFor="bio">Biografia</Label>
                <Textarea
                  id="bio"
                  value={profileData.bio}
                  onChange={(e) => handleInputChange('bio', e.target.value)}
                  disabled={!isEditing}
                  className="border-purple-200 min-h-[100px]"
                  placeholder="Conte um pouco sobre você..."
                />
              </div>
              
              <div className="space-y-2">
                <Label>Gêneros Musicais Favoritos</Label>
                <div className="flex flex-wrap gap-2">
                  {profileData.favoriteGenres.map((genre) => (
                    <Badge key={genre} className="bg-purple-100 text-purple-800">
                      {genre}
                    </Badge>
                  ))}
                </div>
              </div>
            </CardContent>
          </Card>

          {/* Event Statistics */}
          <Card className="border-purple-200">
            <CardHeader>
              <CardTitle className="flex items-center space-x-2">
                <Calendar className="w-5 h-5 text-purple-600" />
                <span>Estatísticas de Eventos</span>
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                <div className="text-center p-4 bg-gradient-to-br from-purple-50 to-white rounded-lg border border-purple-100">
                  <Music className="w-8 h-8 text-purple-600 mx-auto mb-2" />
                  <div className="text-2xl font-bold text-purple-600">
                    {registeredEvents.filter(e => e.category === 'musica').length}
                  </div>
                  <div className="text-sm text-muted-foreground">Shows de Música</div>
                </div>
                
                <div className="text-center p-4 bg-gradient-to-br from-purple-50 to-white rounded-lg border border-purple-100">
                  <PartyPopper className="w-8 h-8 text-purple-600 mx-auto mb-2" />
                  <div className="text-2xl font-bold text-purple-600">
                    {registeredEvents.filter(e => e.category === 'festa').length}
                  </div>
                  <div className="text-sm text-muted-foreground">Festas</div>
                </div>
                
                <div className="text-center p-4 bg-gradient-to-br from-purple-50 to-white rounded-lg border border-purple-100">
                  <Calendar className="w-8 h-8 text-purple-600 mx-auto mb-2" />
                  <div className="text-2xl font-bold text-purple-600">{registeredEvents.length}</div>
                  <div className="text-sm text-muted-foreground">Total de Eventos</div>
                </div>
              </div>
            </CardContent>
          </Card>

          {/* Registered Events */}
          <Card className="border-purple-200">
            <CardHeader>
              <CardTitle>Eventos Inscritos</CardTitle>
            </CardHeader>
            <CardContent>
              {registeredEvents.length === 0 ? (
                <div className="text-center py-8">
                  <Calendar className="w-16 h-16 text-gray-300 mx-auto mb-4" />
                  <p className="text-muted-foreground">
                    Você ainda não se inscreveu em nenhum evento.
                  </p>
                </div>
              ) : (
                <div className="space-y-4">
                  {registeredEvents.map((event) => (
                    <div key={event.id} className="flex items-center space-x-4 p-4 border border-purple-100 rounded-lg bg-gradient-to-r from-purple-50 to-white">
                      {event.image && (
                        <img src={event.image} alt={event.title} className="w-16 h-16 object-cover rounded-lg" />
                      )}
                      <div className="flex-1">
                        <div className="flex items-center space-x-2 mb-1">
                          {getCategoryIcon(event.category)}
                          <h3 className="font-semibold text-gray-900">{event.title}</h3>
                          <Badge className={getCategoryColor(event.category)}>
                            {event.category}
                          </Badge>
                        </div>
                        <p className="text-sm text-muted-foreground">
                          {event.date} às {event.time} • {event.location}
                        </p>
                      </div>
                      <div className="text-right">
                        <div className="font-semibold text-purple-600">
                          {event.price === 0 ? "Gratuito" : `R$ ${event.price}`}
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="calendar">
          <EventCalendar events={registeredEvents} />
        </TabsContent>
      </Tabs>
    </div>
  );
};

export default ProfilePage;
