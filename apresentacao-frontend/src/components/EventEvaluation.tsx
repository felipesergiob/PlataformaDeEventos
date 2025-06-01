import React, { useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Textarea } from '@/components/ui/textarea';
import { Badge } from '@/components/ui/badge';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Star, Camera, MessageCircle, ThumbsUp, Reply } from 'lucide-react';
import { cn } from '@/lib/utils';
import { useToast } from '@/hooks/use-toast';
import { getEventEvaluations, EventEvaluation as EventEvaluationData } from '@/data/mockData';

interface EventEvaluationProps {
  eventId: string;
  userAttended: boolean;
}

const EventEvaluation = ({ eventId, userAttended }: EventEvaluationProps) => {
  const [rating, setRating] = useState(0);
  const [hoverRating, setHoverRating] = useState(0);
  const [comment, setComment] = useState('');
  const [photos, setPhotos] = useState<File[]>([]);
  const [showReplyForm, setShowReplyForm] = useState<string | null>(null);
  const [replyText, setReplyText] = useState('');
  const [hasUserEvaluated, setHasUserEvaluated] = useState(false);
  const { toast } = useToast();

  // Get evaluations from centralized mock
  const evaluations = getEventEvaluations(eventId);

  const handleSubmitEvaluation = () => {
    if (rating === 0) {
      toast({
        title: "Avaliação necessária",
        description: "Por favor, selecione uma avaliação de 1 a 5 estrelas.",
        variant: "destructive"
      });
      return;
    }

    // Here you would submit the evaluation to your backend
    console.log({
      eventId,
      rating,
      comment,
      photos
    });

    // Reset form
    setRating(0);
    setComment('');
    setPhotos([]);
    setHasUserEvaluated(true);
    
    toast({
      title: "Avaliação enviada!",
      description: "Obrigado por avaliar este evento. Sua opinião é muito importante!"
    });
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
                Comentário (opcional)
              </label>
              <Textarea
                placeholder="Compartilhe sua experiência no evento..."
                value={comment}
                onChange={(e) => setComment(e.target.value)}
                rows={4}
              />
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
              disabled={rating === 0}
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
                    <AvatarImage src={evaluation.userAvatar} alt={evaluation.userName} />
                    <AvatarFallback>
                      {evaluation.userName.split(' ').map(n => n[0]).join('')}
                    </AvatarFallback>
                  </Avatar>
                  <div>
                    <h4 className="font-semibold text-gray-900">{evaluation.userName}</h4>
                    <div className="flex items-center space-x-2">
                      <div className="flex items-center">
                        {[1, 2, 3, 4, 5].map((star) => (
                          <Star
                            key={star}
                            className={cn(
                              "w-4 h-4",
                              star <= evaluation.rating
                                ? "text-yellow-500 fill-current"
                                : "text-gray-300"
                            )}
                          />
                        ))}
                      </div>
                      <span className="text-sm text-gray-600">{formatDate(evaluation.date)}</span>
                    </div>
                  </div>
                </div>
              </div>

              {/* Comment */}
              <p className="text-gray-700 mb-3">{evaluation.comment}</p>

              {/* Photos */}
              {evaluation.photos.length > 0 && (
                <div className="flex space-x-2 mb-3">
                  {evaluation.photos.map((photo, index) => (
                    <div key={index} className="w-20 h-20 bg-gray-200 rounded-lg overflow-hidden">
                      <img src={photo} alt={`Foto ${index + 1}`} className="w-full h-full object-cover" />
                    </div>
                  ))}
                </div>
              )}

              {/* Actions */}
              <div className="flex items-center space-x-4 text-sm">
                <Button variant="ghost" size="sm" className="text-gray-600 hover:text-purple-600">
                  <ThumbsUp className="w-4 h-4 mr-1" />
                  {evaluation.likes}
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

              {/* Replies */}
              {evaluation.replies.length > 0 && (
                <div className="mt-4 ml-8 space-y-3">
                  {evaluation.replies.map((reply) => (
                    <div key={reply.id} className="flex items-start space-x-3">
                      <Avatar className="w-8 h-8">
                        <AvatarFallback className="text-xs">
                          {reply.userName.split(' ').map(n => n[0]).join('')}
                        </AvatarFallback>
                      </Avatar>
                      <div className="flex-1">
                        <div className="flex items-center space-x-2">
                          <h5 className="font-medium text-sm text-gray-900">{reply.userName}</h5>
                          {reply.isOrganizer && (
                            <Badge variant="outline" className="text-xs">Organizador</Badge>
                          )}
                          <span className="text-xs text-gray-500">{formatDate(reply.date)}</span>
                        </div>
                        <p className="text-sm text-gray-700 mt-1">{reply.comment}</p>
                      </div>
                    </div>
                  ))}
                </div>
              )}

              {/* Reply Form */}
              {showReplyForm === evaluation.id && (
                <div className="mt-4 ml-8">
                  <div className="flex space-x-3">
                    <Avatar className="w-8 h-8">
                      <AvatarFallback className="text-xs">EU</AvatarFallback>
                    </Avatar>
                    <div className="flex-1 space-y-2">
                      <Textarea
                        placeholder="Digite sua resposta..."
                        value={replyText}
                        onChange={(e) => setReplyText(e.target.value)}
                        rows={2}
                      />
                      <div className="flex space-x-2">
                        <Button 
                          size="sm" 
                          onClick={() => handleReplySubmit(evaluation.id)}
                          disabled={!replyText.trim()}
                        >
                          Responder
                        </Button>
                        <Button 
                          size="sm" 
                          variant="outline"
                          onClick={() => setShowReplyForm(null)}
                        >
                          Cancelar
                        </Button>
                      </div>
                    </div>
                  </div>
                </div>
              )}
            </div>
          ))}
        </CardContent>
      </Card>
    </div>
  );
};

export default EventEvaluation;
