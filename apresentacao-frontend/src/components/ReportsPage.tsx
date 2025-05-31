
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { BarChart3, Users, Calendar, TrendingUp, Eye, Heart, Star, DollarSign } from "lucide-react";
import { Event } from "@/components/EventCard";

interface ReportsPageProps {
  createdEvents: Event[];
  registeredEventIds: string[];
}

const ReportsPage = ({ createdEvents, registeredEventIds }: ReportsPageProps) => {
  const totalEvents = createdEvents.length;
  const totalRegistrations = createdEvents.reduce((sum, event) => sum + event.enrolled, 0);
  const averageRegistrations = totalEvents > 0 ? Math.round(totalRegistrations / totalEvents) : 0;
  const totalCapacity = createdEvents.reduce((sum, event) => sum + event.capacity, 0);
  const occupancyRate = totalCapacity > 0 ? Math.round((totalRegistrations / totalCapacity) * 100) : 0;
  const totalRevenue = createdEvents.reduce((sum, event) => sum + (event.price * event.enrolled), 0);
  const avgTicketPrice = totalEvents > 0 ? Math.round(createdEvents.reduce((sum, event) => sum + event.price, 0) / totalEvents) : 0;

  const categoryStats = createdEvents.reduce((acc, event) => {
    acc[event.category] = (acc[event.category] || 0) + 1;
    return acc;
  }, {} as Record<string, number>);

  const mostPopularCategory = Object.entries(categoryStats).reduce(
    (max, [category, count]) => count > max.count ? { category, count } : max,
    { category: "Nenhuma", count: 0 }
  );

  const topPerformingEvents = [...createdEvents]
    .sort((a, b) => (b.enrolled / b.capacity) - (a.enrolled / a.capacity))
    .slice(0, 3);

  const recentEvents = [...createdEvents]
    .sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime())
    .slice(0, 5);

  const monthlyStats = createdEvents.reduce((acc, event) => {
    const month = new Date(event.date).toLocaleDateString('pt-BR', { month: 'long', year: 'numeric' });
    acc[month] = (acc[month] || 0) + event.enrolled;
    return acc;
  }, {} as Record<string, number>);

  return (
    <div className="container mx-auto px-4 py-8 max-w-7xl">
      <div className="mb-8">
        <h1 className="text-4xl font-bold text-gradient mb-2">Dashboard de Relatórios</h1>
        <p className="text-muted-foreground text-lg">
          Análise completa do desempenho dos seus eventos criados
        </p>
      </div>

      {totalEvents === 0 ? (
        <Card className="text-center py-16">
          <CardContent>
            <div className="w-24 h-24 bg-gradient-to-br from-purple-100 to-purple-200 rounded-full mx-auto mb-4 flex items-center justify-center">
              <BarChart3 className="w-12 h-12 text-purple-500" />
            </div>
            <h3 className="text-xl font-semibold mb-2">Nenhum evento criado ainda</h3>
            <p className="text-muted-foreground">
              Crie seu primeiro evento para ver as métricas aqui
            </p>
          </CardContent>
        </Card>
      ) : (
        <div className="space-y-8">
          {/* Métricas Principais */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            <Card className="border-purple-200 bg-gradient-to-br from-purple-50 to-white">
              <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                <CardTitle className="text-sm font-medium">Total de Eventos</CardTitle>
                <Calendar className="h-4 w-4 text-purple-600" />
              </CardHeader>
              <CardContent>
                <div className="text-3xl font-bold text-purple-700">{totalEvents}</div>
                <p className="text-xs text-muted-foreground">
                  eventos criados por você
                </p>
              </CardContent>
            </Card>

            <Card className="border-blue-200 bg-gradient-to-br from-blue-50 to-white">
              <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                <CardTitle className="text-sm font-medium">Total de Participantes</CardTitle>
                <Users className="h-4 w-4 text-blue-600" />
              </CardHeader>
              <CardContent>
                <div className="text-3xl font-bold text-blue-700">{totalRegistrations}</div>
                <p className="text-xs text-muted-foreground">
                  pessoas se inscreveram
                </p>
              </CardContent>
            </Card>

            <Card className="border-green-200 bg-gradient-to-br from-green-50 to-white">
              <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                <CardTitle className="text-sm font-medium">Receita Total</CardTitle>
                <DollarSign className="h-4 w-4 text-green-600" />
              </CardHeader>
              <CardContent>
                <div className="text-3xl font-bold text-green-700">
                  R$ {totalRevenue.toLocaleString('pt-BR')}
                </div>
                <p className="text-xs text-muted-foreground">
                  em vendas de ingressos
                </p>
              </CardContent>
            </Card>

            <Card className="border-orange-200 bg-gradient-to-br from-orange-50 to-white">
              <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                <CardTitle className="text-sm font-medium">Taxa de Ocupação</CardTitle>
                <TrendingUp className="h-4 w-4 text-orange-600" />
              </CardHeader>
              <CardContent>
                <div className="text-3xl font-bold text-orange-700">{occupancyRate}%</div>
                <p className="text-xs text-muted-foreground">
                  da capacidade total
                </p>
              </CardContent>
            </Card>
          </div>

          {/* Métricas Secundárias */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <Card className="border-purple-200">
              <CardHeader>
                <CardTitle className="flex items-center space-x-2">
                  <Heart className="w-5 h-5 text-purple-600" />
                  <span>Categoria Mais Popular</span>
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="text-xl font-semibold text-purple-700 capitalize mb-2">
                  {mostPopularCategory.category}
                </div>
                <p className="text-muted-foreground">
                  {mostPopularCategory.count} evento{mostPopularCategory.count !== 1 ? "s" : ""} criado{mostPopularCategory.count !== 1 ? "s" : ""}
                </p>
              </CardContent>
            </Card>

            <Card className="border-purple-200">
              <CardHeader>
                <CardTitle className="flex items-center space-x-2">
                  <Users className="w-5 h-5 text-purple-600" />
                  <span>Média de Participantes</span>
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="text-xl font-semibold text-purple-700 mb-2">
                  {averageRegistrations}
                </div>
                <p className="text-muted-foreground">
                  participantes por evento
                </p>
              </CardContent>
            </Card>

            <Card className="border-purple-200">
              <CardHeader>
                <CardTitle className="flex items-center space-x-2">
                  <DollarSign className="w-5 h-5 text-purple-600" />
                  <span>Preço Médio</span>
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="text-xl font-semibold text-purple-700 mb-2">
                  R$ {avgTicketPrice}
                </div>
                <p className="text-muted-foreground">
                  valor médio dos ingressos
                </p>
              </CardContent>
            </Card>
          </div>

          {/* Top 3 Eventos */}
          <Card className="border-purple-200">
            <CardHeader>
              <CardTitle className="flex items-center space-x-2">
                <Star className="w-5 h-5 text-yellow-500" />
                <span>Top 3 Eventos com Melhor Performance</span>
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {topPerformingEvents.map((event, index) => (
                  <div key={event.id} className="flex items-center space-x-4 p-4 border border-purple-100 rounded-lg bg-gradient-to-r from-purple-50 to-white">
                    <div className="w-8 h-8 bg-gradient-to-br from-yellow-400 to-yellow-500 rounded-full flex items-center justify-center text-white font-bold">
                      {index + 1}
                    </div>
                    {event.image && (
                      <img src={event.image} alt={event.title} className="w-16 h-16 object-cover rounded-lg" />
                    )}
                    <div className="flex-1">
                      <h3 className="font-semibold text-gray-900">{event.title}</h3>
                      <p className="text-sm text-muted-foreground">{event.date}</p>
                    </div>
                    <div className="text-right">
                      <div className="font-semibold text-purple-700">
                        {Math.round((event.enrolled / event.capacity) * 100)}% ocupação
                      </div>
                      <div className="text-sm text-muted-foreground">
                        {event.enrolled}/{event.capacity} participantes
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>

          {/* Estatísticas Mensais */}
          <Card className="border-purple-200">
            <CardHeader>
              <CardTitle className="flex items-center space-x-2">
                <BarChart3 className="w-5 h-5 text-purple-600" />
                <span>Participantes por Mês</span>
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-3">
                {Object.entries(monthlyStats).map(([month, participants]) => (
                  <div key={month} className="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                    <span className="font-medium capitalize">{month}</span>
                    <span className="font-semibold text-purple-600">{participants} participantes</span>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>

          {/* Lista Detalhada de Eventos */}
          <Card className="border-purple-200">
            <CardHeader>
              <CardTitle className="flex items-center space-x-2">
                <Eye className="w-5 h-5 text-purple-600" />
                <span>Detalhes dos Eventos Recentes</span>
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {recentEvents.map((event) => (
                  <div key={event.id} className="border border-purple-100 rounded-lg p-4 hover:shadow-md transition-shadow">
                    <div className="flex items-start space-x-4">
                      {event.image && (
                        <img src={event.image} alt={event.title} className="w-20 h-20 object-cover rounded-lg" />
                      )}
                      <div className="flex-1">
                        <div className="flex justify-between items-start mb-2">
                          <h3 className="font-semibold text-gray-900">{event.title}</h3>
                          <span className="text-sm bg-purple-100 text-purple-700 px-2 py-1 rounded capitalize">
                            {event.category}
                          </span>
                        </div>
                        <div className="grid grid-cols-2 md:grid-cols-5 gap-4 text-sm text-muted-foreground">
                          <div>
                            <span className="font-medium">Participantes:</span> {event.enrolled}/{event.capacity}
                          </div>
                          <div>
                            <span className="font-medium">Taxa:</span> {Math.round((event.enrolled / event.capacity) * 100)}%
                          </div>
                          <div>
                            <span className="font-medium">Data:</span> {event.date}
                          </div>
                          <div>
                            <span className="font-medium">Preço:</span> {event.price === 0 ? "Gratuito" : `R$ ${event.price}`}
                          </div>
                          <div>
                            <span className="font-medium">Receita:</span> R$ {(event.price * event.enrolled).toLocaleString('pt-BR')}
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </div>
      )}
    </div>
  );
};

export default ReportsPage;
