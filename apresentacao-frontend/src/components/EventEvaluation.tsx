import React, { useState, useEffect } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Textarea } from '@/components/ui/textarea';
import { Badge } from '@/components/ui/badge';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Star, Camera, MessageCircle, ThumbsUp, Reply } from 'lucide-react';
import { cn } from '@/lib/utils';
import { useToast } from '@/hooks/use-toast';
import { avaliacaoApi, AvaliacaoResumo, userApi } from '@/services/api';
import { useAuth } from '@/contexts/AuthContext';
import { AxiosError } from 'axios';

interface ValidationError {
  codes: string[];
  arguments: Array<{
    codes: string[];
    arguments: null;
    defaultMessage: string;
    code: string;
  }>;
  defaultMessage: string;
  objectName: string;
  field: string;
  rejectedValue: string;
  bindingFailure: boolean;
  code: string;
}

interface ApiErrorResponse {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  errors: ValidationError[];
  path: string;
}

interface EventEvaluationProps {
  eventId: string;
  userAttended: boolean;
}

interface UserInfo {
  id: string;
  nome: string;
}

const EventEvaluation = ({ eventId, userAttended }: EventEvaluationProps) => {
  const [rating, setRating] = useState(0);
  const [hoverRating, setHoverRating] = useState(0);
  const [comment, setComment] = useState('');
  const [photos, setPhotos] = useState<File[]>([]);
  const [showReplyForm, setShowReplyForm] = useState<string | null>(null);
  const [replyText, setReplyText] = useState('');
  const [hasUserEvaluated, setHasUserEvaluated] = useState(false);
  const [evaluations, setEvaluations] = useState<AvaliacaoResumo[]>([]);
  const [userNames, setUserNames] = useState<Record<string, string>>({});
  const { toast } = useToast();
  const { user } = useAuth();

  useEffect(() => {
    const fetchEvaluations = async () => {
      try {
        const avaliacoes = await avaliacaoApi.listarAvaliacoesPorEvento(eventId);
        setEvaluations(avaliacoes);

        // Buscar nomes dos usuários
        const userIds = avaliacoes.map(av => av.usuarioId);
        const uniqueUserIds = [...new Set(userIds)];
        
        const userInfoPromises = uniqueUserIds.map(async (userId) => {
          try {
            const userData = await userApi.getUserById(userId);
            return { id: userId, nome: userData.nome };
          } catch (error) {
            console.error(`Erro ao buscar usuário ${userId}:`, error);
            return { id: userId, nome: 'Usuário' };
          }
        });

        const userInfos = await Promise.all(userInfoPromises);
        const userNamesMap = userInfos.reduce((acc, user) => ({
          ...acc,
          [user.id]: user.nome
        }), {} as Record<string, string>);

        setUserNames(userNamesMap);
      } catch (error) {
        console.error('Erro ao buscar avaliações:', error);
        toast({
          title: "Erro",
          description: "Não foi possível carregar as avaliações do evento.",
          variant: "destructive"
        });
      }
    };

    fetchEvaluations();
  }, [eventId, toast]);

  const handleSubmitEvaluation = async () => {
    if (!user) {
      toast({
        title: "Erro",
        description: "Você precisa estar logado para avaliar o evento.",
        variant: "destructive"
      });
      return;
    }

    if (rating === 0) {
      toast({
        title: "Avaliação necessária",
        description: "Por favor, selecione uma avaliação de 1 a 5 estrelas.",
        variant: "destructive"
      });
      return;
    }

    try {
      await avaliacaoApi.criarAvaliacao({
        eventoId: Number(eventId),
        usuarioId: Number(user.id),
        nota: rating,
        comentario: comment
      });

      // Atualizar lista de avaliações
      const novasAvaliacoes = await avaliacaoApi.listarAvaliacoesPorEvento(eventId);
      setEvaluations(novasAvaliacoes);

      // Reset form
      setRating(0);
      setComment('');
      setPhotos([]);
      setHasUserEvaluated(true);
      
      toast({
        title: "Avaliação enviada!",
        description: "Obrigado por avaliar este evento. Sua opinião é muito importante!"
      });
    } catch (error) {
      console.error('Erro ao enviar avaliação:', error);
      
      const axiosError = error as AxiosError<ApiErrorResponse>;
      
      // Tratamento específico para o erro de evento não finalizado
      if (axiosError.response?.data?.message === "O evento ainda não terminou") {
        toast({
          title: "Avaliação não permitida",
          description: "Você só poderá avaliar este evento após seu término.",
          variant: "destructive"
        });
        return;
      }

      // Tratamento específico para o erro de usuário já avaliou
      if (axiosError.response?.data?.message === "Usuário já avaliou este evento") {
        toast({
          title: "Avaliação já realizada",
          description: "Você já avaliou este evento anteriormente.",
          variant: "destructive"
        });
        setHasUserEvaluated(true);
        return;
      }

      // Tratamento para erros de validação
      if (axiosError.response?.data?.errors?.length > 0) {
        const errorMessage = axiosError.response.data.errors[0].defaultMessage;
        toast({
          title: "Erro de validação",
          description: errorMessage,
          variant: "destructive"
        });
        return;
      }

      toast({
        title: "Erro",
        description: "Não foi possível enviar sua avaliação. Tente novamente mais tarde.",
        variant: "destructive"
      });
    }
  };

  const handlePhotoUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = Array.from(event.target.files || []);
    setPhotos(prev => [...prev, ...files].slice(0, 5)); // Limit to 5 photos
  };

  const handleReplySubmit = (evaluationId: string) => {
    if (!replyText.trim()) return;

    // Here you would submit the reply to your backend
    console.log({
      evaluationId,
      reply: replyText
    });

    setReplyText('');
    setShowReplyForm(null);
    
    toast({
      title: "Resposta enviada!",
      description: "Sua resposta foi publicada com sucesso."
    });
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: 'short',
      year: 'numeric'
    });
  };

  return (
    <div className="max-w-4xl mx-auto p-6 space-y-6">
      {/* User Evaluation Form */}
      {userAttended && !hasUserEvaluated && (
        <Card>
          <CardHeader>
            <CardTitle>Avalie este Evento</CardTitle>
          </CardHeader>
          <CardContent className="space-y-6">
            {/* Rating Stars */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Sua Avaliação *
              </label>
              <div className="flex items-center space-x-1">
                {[1, 2, 3, 4, 5].map((star) => (
                  <Star
                    key={star}
                    className={cn(
                      "w-8 h-8 cursor-pointer transition-colors",
                      (hoverRating || rating) >= star
                        ? "text-yellow-500 fill-current"
                        : "text-gray-300 hover:text-yellow-400"
                    )}
                    onMouseEnter={() => setHoverRating(star)}
                    onMouseLeave={() => setHoverRating(0)}
                    onClick={() => setRating(star)}
                  />
                ))}
                <span className="ml-2 text-sm text-gray-600">
                  {rating > 0 && `${rating} de 5 estrelas`}
                </span>
              </div>
            </div>

            {/* Comment */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Comentário (mínimo 10 caracteres) *
              </label>
              <Textarea
                placeholder="Compartilhe sua experiência no evento..."
                value={comment}
                onChange={(e) => setComment(e.target.value)}
                rows={4}
              />
              <p className="mt-1 text-sm text-gray-500">
                {comment.length}/10 caracteres
              </p>
            </div>

            {/* Photo Upload */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Adicionar Fotos (opcional)
              </label>
              <div className="flex items-center space-x-4">
                <Button variant="outline" className="relative">
                  <Camera className="w-4 h-4 mr-2" />
                  Adicionar Fotos
                  <input
                    type="file"
                    multiple
                    accept="image/*"
                    onChange={handlePhotoUpload}
                    className="absolute inset-0 opacity-0 cursor-pointer"
                  />
                </Button>
                {photos.length > 0 && (
                  <span className="text-sm text-gray-600">
                    {photos.length} foto(s) selecionada(s)
                  </span>
                )}
              </div>
            </div>

            <Button 
              onClick={handleSubmitEvaluation}
              className="bg-purple-600 hover:bg-purple-700"
              disabled={rating === 0 || comment.trim().length < 10}
            >
              Enviar Avaliação
            </Button>
          </CardContent>
        </Card>
      )}

      {/* Thank you message if already evaluated */}
      {hasUserEvaluated && (
        <Card>
          <CardContent className="p-6 text-center">
            <h3 className="text-lg font-semibold text-green-600 mb-2">Obrigado pela sua avaliação!</h3>
            <p className="text-gray-600">Sua opinião foi registrada e ajudará outros usuários.</p>
          </CardContent>
        </Card>
      )}

      {/* Existing Evaluations */}
      <Card>
        <CardHeader>
          <CardTitle className="flex items-center justify-between">
            <span>Avaliações dos Participantes</span>
            <Badge variant="secondary">{evaluations.length} avaliações</Badge>
          </CardTitle>
        </CardHeader>
        <CardContent className="space-y-6">
          {evaluations.map((evaluation) => (
            <div key={evaluation.id} className="border-b border-gray-200 pb-6 last:border-b-0">
              {/* Evaluation Header */}
              <div className="flex items-start justify-between mb-3">
                <div className="flex items-center space-x-3">
                  <Avatar className="w-10 h-10">
                    <AvatarFallback>
                      {userNames[evaluation.usuarioId]?.slice(0, 2).toUpperCase() || 'U'}
                    </AvatarFallback>
                  </Avatar>
                  <div>
                    <h4 className="font-semibold text-gray-900">
                      {userNames[evaluation.usuarioId] || 'Usuário'}
                    </h4>
                    <div className="flex items-center space-x-2">
                      <div className="flex items-center">
                        {[1, 2, 3, 4, 5].map((star) => (
                          <Star
                            key={star}
                            className={cn(
                              "w-4 h-4",
                              star <= evaluation.nota
                                ? "text-yellow-500 fill-current"
                                : "text-gray-300"
                            )}
                          />
                        ))}
                      </div>
                      <span className="text-sm text-gray-600">{formatDate(evaluation.dataCriacao)}</span>
                    </div>
                  </div>
                </div>
              </div>

              {/* Comment */}
              <p className="text-gray-700 mb-3">{evaluation.comentario}</p>

              {/* Actions */}
              <div className="flex items-center space-x-4 text-sm">
                <Button variant="ghost" size="sm" className="text-gray-600 hover:text-purple-600">
                  <ThumbsUp className="w-4 h-4 mr-1" />
                  0
                </Button>
                <Button 
                  variant="ghost" 
                  size="sm" 
                  className="text-gray-600 hover:text-purple-600"
                  onClick={() => setShowReplyForm(evaluation.id)}
                >
                  <MessageCircle className="w-4 h-4 mr-1" />
                  Responder
                </Button>
              </div>
            </div>
          ))}
        </CardContent>
      </Card>
    </div>
  );
};

export default EventEvaluation;
