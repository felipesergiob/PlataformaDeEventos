import React from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { ArrowLeft, Users, Star, MessageCircle, DollarSign, TrendingUp, Calendar, MapPin } from 'lucide-react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, PieChart, Pie, Cell } from 'recharts';
import Navbar from '@/components/Navbar';
import { getEventAnalytics, getRatingDistribution, getDemographicsData, getTopComments } from '@/data/mockData';

interface EventReportProps {
  eventId: number;
  onBack: () => void;
}

const EventReport = ({ eventId, onBack }: EventReportProps) => {
  // Get analytics data from centralized mock
  const eventData = getEventAnalytics(eventId);
  const ratingDistribution = getRatingDistribution(eventId);
  const demographicsData = getDemographicsData(eventId);
  const topComments = getTopComments(eventId);

  if (!eventData) {
    return <div>Evento não encontrado</div>;
  }

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: 'long',
      year: 'numeric'
    });
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100">
      <Navbar />
      
      <div className="container mx-auto px-4 py-8">
        <div className="max-w-6xl mx-auto">
          {/* Header */}
          <div className="flex items-center mb-8">
            <Button 
              variant="outline" 
              onClick={onBack}
              className="mr-4"
            >
              <ArrowLeft className="w-4 h-4 mr-2" />
              Voltar
            </Button>
            <div>
              <h1 className="text-3xl font-bold text-gray-900">{eventData.title}</h1>
              <p className="text-gray-600">Relatório detalhado do evento</p>
            </div>
          </div>

          {/* Event Summary */}
          <Card className="mb-8">
            <CardHeader>
              <CardTitle>Resumo do Evento</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div className="space-y-3">
                  <div className="flex items-center">
                    <Calendar className="w-5 h-5 text-purple-600 mr-3" />
                    <span>{formatDate(eventData.date)} • {eventData.time}</span>
                  </div>
                  <div className="flex items-center">
                    <MapPin className="w-5 h-5 text-purple-600 mr-3" />
                    <span>{eventData.location}</span>
                  </div>
                  <div className="flex items-center">
                    <Users className="w-5 h-5 text-purple-600 mr-3" />
                    <span>{eventData.participants} de {eventData.maxParticipants} participantes</span>
                  </div>
                </div>
                <div className="space-y-3">
                  <div className="flex items-center">
                    <Star className="w-5 h-5 text-yellow-500 mr-3" />
                    <span>{eventData.rating}/5 ({eventData.totalRatings} avaliações)</span>
                  </div>
                  <div className="flex items-center">
                    <MessageCircle className="w-5 h-5 text-blue-500 mr-3" />
                    <span>{eventData.comments} comentários</span>
                  </div>
                  <div className="flex items-center">
                    <DollarSign className="w-5 h-5 text-green-500 mr-3" />
                    <span>R$ {eventData.revenue.toLocaleString()} de receita</span>
                  </div>
                </div>
              </div>
            </CardContent>
          </Card>

          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-8">
            {/* Financial Summary */}
            <Card>
              <CardHeader>
                <CardTitle className="flex items-center">
                  <TrendingUp className="w-5 h-5 mr-2" />
                  Resumo Financeiro
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  <div className="flex justify-between items-center">
                    <span className="text-gray-600">Receita Total</span>
                    <span className="font-semibold text-green-600">R$ {eventData.revenue.toLocaleString()}</span>
                  </div>
                  <div className="flex justify-between items-center">
                    <span className="text-gray-600">Despesas</span>
                    <span className="font-semibold text-red-600">R$ {eventData.expenses.toLocaleString()}</span>
                  </div>
                  <div className="border-t pt-2">
                    <div className="flex justify-between items-center">
                      <span className="text-gray-900 font-semibold">Lucro Líquido</span>
                      <span className="font-bold text-green-600 text-lg">R$ {eventData.profit.toLocaleString()}</span>
                    </div>
                  </div>
                  <div className="text-sm text-gray-500">
                    Taxa de ocupação: {Math.round((eventData.participants / eventData.maxParticipants) * 100)}%
                  </div>
                </div>
              </CardContent>
            </Card>

            {/* Rating Distribution */}
            <Card>
              <CardHeader>
                <CardTitle>Distribuição de Avaliações</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-3">
                  {ratingDistribution.map((item, index) => (
                    <div key={index} className="flex items-center space-x-3">
                      <span className="w-12 text-sm font-medium">{item.rating}</span>
                      <div className="flex-1 bg-gray-200 rounded-full h-2">
                        <div 
                          className="bg-purple-600 h-2 rounded-full" 
                          style={{ width: `${item.percentage}%` }}
                        />
                      </div>
                      <span className="text-sm text-gray-600 w-8">{item.count}</span>
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>
          </div>

          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-8">
            {/* Demographics Chart */}
            <Card>
              <CardHeader>
                <CardTitle>Demografia dos Participantes</CardTitle>
              </CardHeader>
              <CardContent>
                <ResponsiveContainer width="100%" height={200}>
                  <PieChart>
                    <Pie
                      data={demographicsData}
                      cx="50%"
                      cy="50%"
                      innerRadius={40}
                      outerRadius={80}
                      paddingAngle={5}
                      dataKey="value"
                    >
                      {demographicsData.map((entry, index) => (
                        <Cell key={`cell-${index}`} fill={entry.fill} />
                      ))}
                    </Pie>
                    <Tooltip formatter={(value) => [`${value}%`, 'Participação']} />
                  </PieChart>
                </ResponsiveContainer>
                <div className="flex flex-wrap justify-center mt-4 gap-4">
                  {demographicsData.map((item, index) => (
                    <div key={index} className="flex items-center space-x-2">
                      <div 
                        className="w-3 h-3 rounded-full" 
                        style={{ backgroundColor: item.fill }}
                      />
                      <span className="text-sm">{item.name}: {item.value}%</span>
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>

            {/* Top Comments */}
            <Card>
              <CardHeader>
                <CardTitle>Principais Comentários</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  {topComments.map((comment) => (
                    <div key={comment.id} className="border-b border-gray-200 pb-3 last:border-b-0">
                      <div className="flex items-start space-x-3">
                        <Avatar className="w-8 h-8">
                          <AvatarImage src={comment.userAvatar} alt={comment.userName} />
                          <AvatarFallback className="text-xs">
                            {comment.userName.split(' ').map(n => n[0]).join('')}
                          </AvatarFallback>
                        </Avatar>
                        <div className="flex-1">
                          <div className="flex items-center space-x-2 mb-1">
                            <h5 className="font-medium text-sm">{comment.userName}</h5>
                            <div className="flex">
                              {[...Array(comment.rating)].map((_, i) => (
                                <Star key={i} className="w-3 h-3 text-yellow-500 fill-current" />
                              ))}
                            </div>
                          </div>
                          <p className="text-sm text-gray-700">{comment.comment}</p>
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>
          </div>

          {/* Key Metrics */}
          <Card>
            <CardHeader>
              <CardTitle>Métricas Principais</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="grid grid-cols-2 md:grid-cols-4 gap-6">
                <div className="text-center">
                  <div className="text-2xl font-bold text-purple-600">{eventData.participants}</div>
                  <div className="text-sm text-gray-600">Participantes</div>
                </div>
                <div className="text-center">
                  <div className="text-2xl font-bold text-yellow-500">{eventData.rating}</div>
                  <div className="text-sm text-gray-600">Nota Média</div>
                </div>
                <div className="text-center">
                  <div className="text-2xl font-bold text-blue-500">{eventData.comments}</div>
                  <div className="text-sm text-gray-600">Comentários</div>
                </div>
                <div className="text-center">
                  <div className="text-2xl font-bold text-green-500">{Math.round((eventData.participants / eventData.maxParticipants) * 100)}%</div>
                  <div className="text-sm text-gray-600">Taxa de Ocupação</div>
                </div>
              </div>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default EventReport;
