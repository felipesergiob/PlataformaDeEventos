
import React, { useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { 
  User, 
  Calendar, 
  Star, 
  MessageSquare, 
  Users, 
  Heart,
  BarChart3,
  Settings,
  UserPlus,
  UserCheck
} from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { toast } from '@/hooks/use-toast';
import { getUser, getEventsByOrganizer, getUserReviews } from '@/data/mockData';

interface UserProfileProps {
  userId: string;
  isOwnProfile?: boolean;
}

const UserProfile = ({ userId, isOwnProfile = false }: UserProfileProps) => {
  const [isFollowing, setIsFollowing] = useState(false);
  const [activeTab, setActiveTab] = useState('events');
  const navigate = useNavigate();

  // Get user data from centralized mock
  const user = getUser(userId);
  const pastEvents = getEventsByOrganizer(userId).filter(event => event.isPast);
  const reviews = getUserReviews(userId);

  if (!user) {
    return (
      <div className="max-w-4xl mx-auto p-6">
        <Card>
          <CardContent className="p-6 text-center">
            <h2 className="text-xl font-semibold text-gray-900 mb-2">Usuário não encontrado</h2>
            <p className="text-gray-600">O perfil que você está procurando não existe.</p>
          </CardContent>
        </Card>
      </div>
    );
  }

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: 'short',
      year: 'numeric'
    });
  };

  const handleFollowToggle = () => {
    setIsFollowing(!isFollowing);
    if (!isFollowing) {
      toast({
        title: `Você está seguindo ${user.name}!`,
        description: `Você receberá notificações sobre os eventos de ${user.name}.`
      });
    }
  };

  const handleViewEvent = (eventId: number) => {
    navigate(`/event/${eventId}`);
  };

  return (
    <div className="max-w-4xl mx-auto p-6 space-y-6">
      {/* Profile Header */}
      <Card>
        <CardContent className="p-6">
          <div className="flex flex-col md:flex-row items-start md:items-center space-y-4 md:space-y-0 md:space-x-6">
            <Avatar className="w-24 h-24">
              <AvatarImage src={user.avatar} alt={user.name} />
              <AvatarFallback className="text-2xl bg-purple-100 text-purple-600">
                {user.name.split(' ').map(n => n[0]).join('')}
              </AvatarFallback>
            </Avatar>

            <div className="flex-1">
              <div className="flex flex-col md:flex-row md:items-center md:justify-between mb-4">
                <div>
                  <h1 className="text-2xl font-bold text-gray-900">{user.name}</h1>
                  <p className="text-gray-600">{user.location}</p>
                  <p className="text-sm text-gray-500">Membro desde {formatDate(user.joinedDate)}</p>
                </div>

                <div className="flex items-center space-x-2 mt-4 md:mt-0">
                  {!isOwnProfile && (
                    <Button
                      onClick={handleFollowToggle}
                      className={isFollowing ? 'bg-green-600 hover:bg-green-700' : 'bg-purple-600 hover:bg-purple-700'}
                    >
                      {isFollowing ? (
                        <>
                          <UserCheck className="w-4 h-4 mr-2" />
                          Seguindo
                        </>
                      ) : (
                        <>
                          <UserPlus className="w-4 h-4 mr-2" />
                          Seguir
                        </>
                      )}
                    </Button>
                  )}
                  {isOwnProfile && (
                    <Button variant="outline">
                      <Settings className="w-4 h-4 mr-2" />
                      Editar Perfil
                    </Button>
                  )}
                </div>
              </div>

              <p className="text-gray-700 mb-4">{user.bio}</p>

              {/* Stats */}
              <div className="grid grid-cols-2 md:grid-cols-5 gap-4">
                <div className="text-center">
                  <div className="text-2xl font-bold text-purple-600">{user.followers}</div>
                  <div className="text-sm text-gray-600">Seguidores</div>
                </div>
                <div className="text-center">
                  <div className="text-2xl font-bold text-purple-600">{user.following}</div>
                  <div className="text-sm text-gray-600">Seguindo</div>
                </div>
                <div className="text-center">
                  <div className="text-2xl font-bold text-purple-600">{user.eventsOrganized}</div>
                  <div className="text-sm text-gray-600">Organizados</div>
                </div>
                <div className="text-center">
                  <div className="text-2xl font-bold text-purple-600">{user.eventsAttended}</div>
                  <div className="text-sm text-gray-600">Participou</div>
                </div>
                <div className="text-center">
                  <div className="flex items-center justify-center space-x-1">
                    <Star className="w-4 h-4 text-yellow-500 fill-current" />
                    <span className="text-2xl font-bold text-purple-600">{user.averageRating}</span>
                  </div>
                  <div className="text-sm text-gray-600">Avaliação</div>
                </div>
              </div>
            </div>
          </div>
        </CardContent>
      </Card>

      {/* Content Tabs */}
      <Tabs value={activeTab} onValueChange={setActiveTab}>
        <TabsList className="grid w-full grid-cols-3">
          <TabsTrigger value="events">Eventos Passados</TabsTrigger>
          <TabsTrigger value="reviews">Avaliações</TabsTrigger>
          {isOwnProfile && <TabsTrigger value="dashboard">Dashboard</TabsTrigger>}
          {!isOwnProfile && <TabsTrigger value="following">Seguidores</TabsTrigger>}
        </TabsList>

        <TabsContent value="events" className="space-y-4">
          <div className="grid gap-4">
            {pastEvents.map((event) => (
              <Card key={event.id} className="hover:shadow-md transition-shadow cursor-pointer" onClick={() => handleViewEvent(event.id)}>
                <CardContent className="p-6">
                  <div className="flex items-center space-x-4">
                    <div className="w-16 h-16 bg-gradient-to-br from-purple-400 to-blue-500 rounded-lg flex items-center justify-center">
                      <Calendar className="w-8 h-8 text-white" />
                    </div>
                    <div className="flex-1">
                      <div className="flex items-center justify-between">
                        <h3 className="font-semibold text-lg text-gray-900">{event.title}</h3>
                        <div className="flex items-center space-x-1">
                          <Star className="w-4 h-4 text-yellow-500 fill-current" />
                          <span className="font-semibold">{event.rating}</span>
                        </div>
                      </div>
                      <p className="text-gray-600">{formatDate(event.date)}</p>
                      <div className="flex items-center space-x-4 mt-2">
                        <div className="flex items-center space-x-1">
                          <Users className="w-4 h-4 text-gray-500" />
                          <span className="text-sm text-gray-600">{event.participants} participantes</span>
                        </div>
                        <div className="flex items-center space-x-1">
                          <MessageSquare className="w-4 h-4 text-gray-500" />
                          <span className="text-sm text-gray-600">{event.reviews} avaliações</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        </TabsContent>

        <TabsContent value="reviews" className="space-y-4">
          <div className="grid gap-4">
            {reviews.map((review) => (
              <Card key={review.id}>
                <CardContent className="p-6">
                  <div className="flex items-start justify-between mb-3">
                    <h4 className="font-semibold text-gray-900">{review.eventTitle}</h4>
                    <div className="flex items-center space-x-1">
                      {[...Array(5)].map((_, i) => (
                        <Star
                          key={i}
                          className={`w-4 h-4 ${
                            i < review.rating ? 'text-yellow-500 fill-current' : 'text-gray-300'
                          }`}
                        />
                      ))}
                    </div>
                  </div>
                  <p className="text-gray-700 mb-3">{review.comment}</p>
                  <div className="flex items-center justify-between text-sm text-gray-600">
                    <span>Por {review.author}</span>
                    <span>{formatDate(review.date)}</span>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        </TabsContent>

        {isOwnProfile && (
          <TabsContent value="dashboard">
            <Card>
              <CardHeader>
                <CardTitle className="flex items-center space-x-2">
                  <BarChart3 className="w-5 h-5" />
                  <span>Dashboard de Eventos</span>
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                  <div className="text-center p-6 bg-blue-50 rounded-lg">
                    <div className="text-3xl font-bold text-blue-600 mb-2">245</div>
                    <div className="text-sm text-blue-700">Total de Participantes</div>
                  </div>
                  <div className="text-center p-6 bg-green-50 rounded-lg">
                    <div className="text-3xl font-bold text-green-600 mb-2">4.8</div>
                    <div className="text-sm text-green-700">Avaliação Média</div>
                  </div>
                  <div className="text-center p-6 bg-purple-50 rounded-lg">
                    <div className="text-3xl font-bold text-purple-600 mb-2">85%</div>
                    <div className="text-sm text-purple-700">Taxa de Satisfação</div>
                  </div>
                </div>
              </CardContent>
            </Card>
          </TabsContent>
        )}

        {!isOwnProfile && (
          <TabsContent value="following">
            <Card>
              <CardHeader>
                <CardTitle>Seguidores</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  {[1, 2, 3, 4].map((id) => (
                    <div key={id} className="flex items-center space-x-3 p-3 border rounded-lg hover:bg-gray-50">
                      <Avatar>
                        <AvatarFallback>U{id}</AvatarFallback>
                      </Avatar>
                      <div className="flex-1">
                        <h4 className="font-medium">Usuário {id}</h4>
                        <p className="text-sm text-gray-500">4 eventos organizados</p>
                      </div>
                      <Button variant="outline" size="sm">
                        Ver Perfil
                      </Button>
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>
          </TabsContent>
        )}
      </Tabs>
    </div>
  );
};

export default UserProfile;
