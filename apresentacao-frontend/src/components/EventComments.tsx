
import React, { useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Textarea } from '@/components/ui/textarea';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Badge } from '@/components/ui/badge';
import { MessageCircle, Reply, Heart } from 'lucide-react';
import { useToast } from '@/hooks/use-toast';
import { getComments, getUser } from '@/data/mockData';

interface EventCommentsProps {
  eventId: string;
  isOrganizer: boolean;
}

const EventComments = ({ eventId, isOrganizer }: EventCommentsProps) => {
  const [newComment, setNewComment] = useState('');
  const [replyText, setReplyText] = useState('');
  const [showReplyForm, setShowReplyForm] = useState<string | null>(null);
  const { toast } = useToast();

  // Get comments from centralized mock data
  const comments = getComments(eventId);

  const handleSubmitComment = () => {
    if (!newComment.trim()) {
      toast({
        title: "Comentário vazio",
        description: "Por favor, escreva seu comentário antes de enviar.",
        variant: "destructive"
      });
      return;
    }

    // Here you would submit the comment to your backend
    console.log({
      eventId,
      comment: newComment
    });

    setNewComment('');
    
    toast({
      title: "Comentário publicado!",
      description: "Seu comentário foi adicionado com sucesso."
    });
  };

  const handleReplySubmit = (commentId: string) => {
    if (!replyText.trim()) {
      toast({
        title: "Resposta vazia",
        description: "Por favor, escreva sua resposta antes de enviar.",
        variant: "destructive"
      });
      return;
    }

    // Here you would submit the reply to your backend
    console.log({
      commentId,
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
            disabled={!newComment.trim()}
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
          {comments.map((comment) => {
            const user = getUser(comment.userId);
            return (
              <div key={comment.id} className="border-b border-gray-200 pb-6 last:border-b-0">
                {/* Comment Header */}
                <div className="flex items-start space-x-3 mb-3">
                  <Avatar className="w-10 h-10">
                    <AvatarImage src={comment.userAvatar} alt={comment.userName} />
                    <AvatarFallback>
                      {comment.userName.split(' ').map(n => n[0]).join('')}
                    </AvatarFallback>
                  </Avatar>
                  <div className="flex-1">
                    <div className="flex items-center space-x-2 mb-1">
                      <h4 className="font-semibold text-gray-900">{comment.userName}</h4>
                      <span className="text-sm text-gray-500">{formatDate(comment.date)}</span>
                    </div>
                    <p className="text-gray-700">{comment.comment}</p>
                  </div>
                </div>

                {/* Comment Actions */}
                <div className="flex items-center space-x-4 text-sm ml-13">
                  <Button variant="ghost" size="sm" className="text-gray-600 hover:text-red-600">
                    <Heart className="w-4 h-4 mr-1" />
                    {comment.likes}
                  </Button>
                  {isOrganizer && (
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
                {comment.replies.length > 0 && (
                  <div className="mt-4 ml-8 space-y-3">
                    {comment.replies.map((reply) => (
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
