import React, { useState, useEffect } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Textarea } from '@/components/ui/textarea';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Badge } from '@/components/ui/badge';
import { MessageCircle, Reply } from 'lucide-react';
import { useToast } from '@/hooks/use-toast';
import { useAuth } from '@/contexts/AuthContext';
import { comentarioApi, ComentarioResponse, userApi, UserResponse, eventApi } from '@/services/api';

interface EventCommentsProps {
  eventId: string;
  isOrganizer: boolean;
}

interface CommentWithUser extends ComentarioResponse {
  userName?: string;
}

const EventComments = ({ eventId, isOrganizer }: EventCommentsProps) => {
  const [newComment, setNewComment] = useState('');
  const [replyText, setReplyText] = useState('');
  const [showReplyForm, setShowReplyForm] = useState<string | null>(null);
  const [comments, setComments] = useState<CommentWithUser[]>([]);
  const [loading, setLoading] = useState(true);
  const [organizerId, setOrganizerId] = useState<string | null>(null);
  const { toast } = useToast();
  const { user } = useAuth();

  useEffect(() => {
    fetchEventData();
    fetchComments();
  }, [eventId]);

  const fetchEventData = async () => {
    try {
      const eventData = await eventApi.getEventById(eventId);
      setOrganizerId(eventData.organizadorId);
    } catch (error) {
      console.error('Erro ao buscar dados do evento:', error);
    }
  };

  const fetchUserData = async (userId: string): Promise<string> => {
    try {
      const userData = await userApi.getUserById(userId);
      return userData.nome;
    } catch (error) {
      console.error('Erro ao buscar dados do usuário:', error);
      return `Usuário ${userId}`;
    }
  };

  const fetchComments = async () => {
    try {
      const data = await comentarioApi.listarComentariosPorEvento(eventId);
      
      // Buscar dados dos usuários para cada comentário
      const commentsWithUsers = await Promise.all(
        data.map(async (comment) => {
          const userName = await fetchUserData(comment.usuarioId);
          return { ...comment, userName };
        })
      );

      setComments(commentsWithUsers);
    } catch (error) {
      toast({
        title: "Erro ao carregar comentários",
        description: "Não foi possível carregar os comentários do evento.",
        variant: "destructive"
      });
    } finally {
      setLoading(false);
    }
  };

  const handleSubmitComment = async () => {
    if (!newComment.trim() || !user) {
      toast({
        title: "Comentário vazio",
        description: "Por favor, escreva seu comentário antes de enviar.",
        variant: "destructive"
      });
      return;
    }

    try {
      await comentarioApi.criarComentario({
        comentario: newComment,
        eventoId: Number(eventId),
        usuarioId: Number(user.id)
      });

      setNewComment('');
      await fetchComments();
      
      toast({
        title: "Comentário publicado!",
        description: "Seu comentário foi adicionado com sucesso."
      });
    } catch (error) {
      toast({
        title: "Erro ao publicar comentário",
        description: "Não foi possível publicar seu comentário.",
        variant: "destructive"
      });
    }
  };

  const handleReplySubmit = async (commentId: string) => {
    if (!replyText.trim() || !user) {
      toast({
        title: "Resposta vazia",
        description: "Por favor, escreva sua resposta antes de enviar.",
        variant: "destructive"
      });
      return;
    }

    try {
      await comentarioApi.responderComentario({
        comentarioPaiId: Number(commentId),
        comentario: replyText,
        usuarioId: Number(user.id),
        eventoId: Number(eventId)
      });

      setReplyText('');
      setShowReplyForm(null);
      await fetchComments();
      
      toast({
        title: "Resposta enviada!",
        description: "Sua resposta foi publicada com sucesso."
      });
    } catch (error) {
      toast({
        title: "Erro ao enviar resposta",
        description: "Não foi possível enviar sua resposta.",
        variant: "destructive"
      });
    }
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: 'short',
      year: 'numeric'
    });
  };

  // Agrupar comentários e respostas
  const mainComments = comments.filter(comment => !comment.comentarioPaiId);
  const replies = comments.filter(comment => comment.comentarioPaiId);

  if (loading) {
    return (
      <div className="text-center py-8">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-purple-600 mx-auto"></div>
        <p className="mt-2 text-gray-600">Carregando comentários...</p>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto p-6 space-y-6">
      {/* New Comment Form */}
      <Card>
        <CardHeader>
          <CardTitle>Deixe seu Comentário</CardTitle>
        </CardHeader>
        <CardContent className="space-y-4">
          <Textarea
            placeholder="Escreva seu comentário sobre o evento..."
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
            rows={3}
          />
          <Button 
            onClick={handleSubmitComment}
            className="bg-purple-600 hover:bg-purple-700"
            disabled={!newComment.trim() || !user}
          >
            <MessageCircle className="w-4 h-4 mr-2" />
            Publicar Comentário
          </Button>
        </CardContent>
      </Card>

      {/* Comments List */}
      <Card>
        <CardHeader>
          <CardTitle className="flex items-center justify-between">
            <span>Comentários</span>
            <Badge variant="secondary">{comments.length} comentários</Badge>
          </CardTitle>
        </CardHeader>
        <CardContent className="space-y-6">
          {mainComments.map((comment) => {
            const commentReplies = replies.filter(reply => reply.comentarioPaiId === comment.id);
            const isCommentFromOrganizer = comment.usuarioId === organizerId;
            
            return (
              <div key={comment.id} className="border-b border-gray-200 pb-6 last:border-b-0">
                {/* Main Comment */}
                <div className="flex items-start space-x-3 mb-3">
                  <Avatar className="w-10 h-10">
                    <AvatarFallback>
                      {comment.userName?.slice(0, 2).toUpperCase() || 'U'}
                    </AvatarFallback>
                  </Avatar>
                  <div className="flex-1">
                    <div className="flex items-center space-x-2 mb-1">
                      <h4 className="font-semibold text-gray-900">{comment.userName}</h4>
                      {isCommentFromOrganizer && (
                        <Badge variant="outline" className="text-xs">Organizador</Badge>
                      )}
                      <span className="text-sm text-gray-500">{formatDate(comment.dataCriacao)}</span>
                    </div>
                    <p className="text-gray-700">{comment.comentario}</p>
                  </div>
                </div>

                {/* Comment Actions */}
                <div className="flex items-center space-x-4 text-sm ml-13">
                  {user && (
                    <Button 
                      variant="ghost" 
                      size="sm" 
                      className="text-gray-600 hover:text-purple-600"
                      onClick={() => setShowReplyForm(comment.id)}
                    >
                      <Reply className="w-4 h-4 mr-1" />
                      Responder
                    </Button>
                  )}
                </div>

                {/* Replies */}
                {commentReplies.length > 0 && (
                  <div className="mt-4 ml-8 space-y-3">
                    {commentReplies.map((reply) => {
                      const isReplyFromOrganizer = reply.usuarioId === organizerId;
                      return (
                        <div key={reply.id} className="flex items-start space-x-3 bg-gray-50 p-3 rounded-lg">
                          <Avatar className="w-8 h-8">
                            <AvatarFallback>
                              {reply.userName?.slice(0, 2).toUpperCase() || 'U'}
                            </AvatarFallback>
                          </Avatar>
                          <div className="flex-1">
                            <div className="flex items-center space-x-2">
                              <h5 className="font-medium text-sm text-gray-900">{reply.userName}</h5>
                              {isReplyFromOrganizer && (
                                <Badge variant="outline" className="text-xs">Organizador</Badge>
                              )}
                              <span className="text-xs text-gray-500">{formatDate(reply.dataCriacao)}</span>
                            </div>
                            <p className="text-sm text-gray-700 mt-1">{reply.comentario}</p>
                          </div>
                        </div>
                      );
                    })}
                  </div>
                )}

                {/* Reply Form */}
                {showReplyForm === comment.id && (
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
                            onClick={() => handleReplySubmit(comment.id)}
                            disabled={!replyText.trim()}
                            className="bg-purple-600 hover:bg-purple-700"
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
            );
          })}

          {comments.length === 0 && (
            <div className="text-center py-8">
              <MessageCircle className="w-12 h-12 text-gray-400 mx-auto mb-3" />
              <h3 className="text-lg font-semibold text-gray-900 mb-2">Ainda não há comentários</h3>
              <p className="text-gray-600">Seja o primeiro a comentar sobre este evento!</p>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  );
};

export default EventComments;
