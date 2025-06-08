import React, { useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Calendar, MapPin, Clock, Tag, DollarSign, Image } from 'lucide-react';
import Navbar from '@/components/Navbar';
import { useToast } from '@/hooks/use-toast';
import { eventApi } from '@/services/api';
import { useAuth } from '@/contexts/AuthContext';
import { useNavigate } from 'react-router-dom';

const CreateEvent = () => {
  const { toast } = useToast();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [eventData, setEventData] = useState({
    title: '',
    description: '',
    date: '',
    time: '',
    location: '',
    genre: '',
    price: '',
    image: null as File | null
  });
  const [loading, setLoading] = useState(false);

  const genres = [
    'Tecnologia', 'Música', 'Gastronomia', 'Educação', 'Arte',
    'Esporte', 'Negócios', 'Saúde', 'Cultura', 'Outros'
  ];

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user) {
      toast({ title: 'Erro', description: 'Você precisa estar logado para criar um evento.', variant: 'destructive' });
      return;
    }
    setLoading(true);
    try {
      // Montar dataInicio e dataFim no formato ISO
      const dataInicio = eventData.date && eventData.time
        ? new Date(`${eventData.date}T${eventData.time}`).toISOString()
        : '';
      const dataFim = dataInicio; // Você pode ajustar para permitir data/hora de fim diferente
      const newEvent = {
        titulo: eventData.title,
        descricao: eventData.description,
        dataInicio,
        dataFim,
        local: eventData.location,
        genero: eventData.genre,
        capacidade: 200, // Pode adicionar campo no form se quiser
        organizadorId: Number(user.id),
        valor: Number(eventData.price)
      };
      const response = await eventApi.createEvent(newEvent);
      toast({ title: 'Evento criado com sucesso!', description: `Evento "${response.titulo}" criado!` });
      setEventData({
        title: '',
        description: '',
        date: '',
        time: '',
        location: '',
        genre: '',
        price: '',
        image: null
      });
      navigate('/'); // Redireciona para a home após criar
    } catch (error) {
      toast({ title: 'Erro ao criar evento', description: 'Tente novamente.', variant: 'destructive' });
    } finally {
      setLoading(false);
    }
  };

  const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setEventData(prev => ({ ...prev, image: file }));
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100">
      <Navbar />
      
      <div className="container mx-auto px-4 py-8">
        <div className="max-w-2xl mx-auto">
          <h1 className="text-3xl font-bold text-gray-900 mb-8 text-center">Criar Novo Evento</h1>

          <Card>
            <CardHeader>
              <CardTitle>Detalhes do Evento</CardTitle>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSubmit} className="space-y-6">
                <div>
                  <Label htmlFor="title">Título do Evento</Label>
                  <Input
                    id="title"
                    value={eventData.title}
                    onChange={(e) => setEventData(prev => ({ ...prev, title: e.target.value }))}
                    placeholder="Digite o título do evento"
                    required
                  />
                </div>

                <div>
                  <Label htmlFor="description">Descrição</Label>
                  <Textarea
                    id="description"
                    value={eventData.description}
                    onChange={(e) => setEventData(prev => ({ ...prev, description: e.target.value }))}
                    placeholder="Descreva o evento em detalhes"
                    rows={4}
                    required
                  />
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <Label htmlFor="date" className="flex items-center space-x-2">
                      <Calendar className="w-4 h-4 text-purple-600" />
                      <span>Data</span>
                    </Label>
                    <Input
                      id="date"
                      type="date"
                      value={eventData.date}
                      onChange={(e) => setEventData(prev => ({ ...prev, date: e.target.value }))}
                      required
                    />
                  </div>

                  <div>
                    <Label htmlFor="time" className="flex items-center space-x-2">
                      <Clock className="w-4 h-4 text-purple-600" />
                      <span>Horário</span>
                    </Label>
                    <Input
                      id="time"
                      type="time"
                      value={eventData.time}
                      onChange={(e) => setEventData(prev => ({ ...prev, time: e.target.value }))}
                      required
                    />
                  </div>
                </div>

                <div>
                  <Label htmlFor="location" className="flex items-center space-x-2">
                    <MapPin className="w-4 h-4 text-purple-600" />
                    <span>Local</span>
                  </Label>
                  <Input
                    id="location"
                    value={eventData.location}
                    onChange={(e) => setEventData(prev => ({ ...prev, location: e.target.value }))}
                    placeholder="Endereço do evento"
                    required
                  />
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <Label htmlFor="genre" className="flex items-center space-x-2">
                      <Tag className="w-4 h-4 text-purple-600" />
                      <span>Categoria</span>
                    </Label>
                    <Select 
                      value={eventData.genre} 
                      onValueChange={(value) => setEventData(prev => ({ ...prev, genre: value }))}
                    >
                      <SelectTrigger>
                        <SelectValue placeholder="Selecione a categoria" />
                      </SelectTrigger>
                      <SelectContent>
                        {genres.map((genre) => (
                          <SelectItem key={genre} value={genre}>
                            {genre}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>

                  <div>
                    <Label htmlFor="price" className="flex items-center space-x-2">
                      <DollarSign className="w-4 h-4 text-purple-600" />
                      <span>Preço (R$)</span>
                    </Label>
                    <Input
                      id="price"
                      type="number"
                      min="0"
                      step="0.01"
                      value={eventData.price}
                      onChange={(e) => setEventData(prev => ({ ...prev, price: e.target.value }))}
                      placeholder="0.00 para eventos gratuitos"
                      required
                    />
                  </div>
                </div>

                <div>
                  <Label htmlFor="image" className="flex items-center space-x-2">
                    <Image className="w-4 h-4 text-purple-600" />
                    <span>Imagem do Evento</span>
                  </Label>
                  <div className="mt-1 flex items-center justify-center border-2 border-dashed border-gray-300 rounded-lg p-6">
                    <input
                      id="image"
                      type="file"
                      accept="image/*"
                      className="hidden"
                      onChange={handleImageUpload}
                    />
                    <label htmlFor="image" className="cursor-pointer">
                      <div className="text-center">
                        <Image className="mx-auto h-12 w-12 text-gray-400" />
                        <div className="mt-2 flex text-sm text-gray-600">
                          <span className="relative cursor-pointer rounded-md font-medium text-purple-600 hover:text-purple-500">
                            Carregar imagem
                          </span>
                          <p className="pl-1">ou arraste e solte</p>
                        </div>
                        <p className="text-xs text-gray-500">
                          PNG, JPG, GIF até 10MB
                        </p>
                      </div>
                    </label>
                    {eventData.image && (
                      <p className="ml-4 text-sm text-gray-700">{eventData.image.name}</p>
                    )}
                  </div>
                </div>

                <div className="pt-4">
                  <Button 
                    type="submit" 
                    className="w-full bg-purple-600 hover:bg-purple-700 text-white"
                    disabled={loading}
                  >
                    {loading ? 'Criando...' : 'Criar Evento'}
                  </Button>
                </div>
              </form>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default CreateEvent;
