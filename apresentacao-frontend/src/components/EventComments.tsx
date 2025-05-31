import { useState } from "react";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Input } from "@/components/ui/input";
import { MessageCircle, Send, Heart, X } from "lucide-react";
import { toast } from "@/hooks/use-toast";

interface Comment {
  id: string;
  author: string;
  avatar: string;
  content: string;
  timestamp: string;
  likes: number;
  isLiked: boolean;
}

interface EventCommentsProps {
  eventId: string;
  eventTitle: string;
}

const EventComments = ({ eventId, eventTitle }: EventCommentsProps) => {
  const [comments, setComments] = useState<Comment[]>([
    {
      id: "1",
      author: "Maria Silva",
      avatar: "MS",
      content: "Mal posso esperar por esse evento! Vai ser incrível! 🎵",
      timestamp: "2h",
      likes: 3,
      isLiked: false,
    },
    {
      id: "2",
      author: "João Santos",
      avatar: "JS",
      content: "Já comprei meu ingresso! Quem mais vai estar lá?",
      timestamp: "4h",
      likes: 5,
      isLiked: true,
    },
    {
      id: "3",
      author: "Ana Costa",
      avatar: "AC",
      content: "Esse line-up está sensacional! Vale muito a pena ir!",
      timestamp: "6h",
      likes: 2,
      isLiked: false,
    },
  ]);

  const [newComment, setNewComment] = useState("");
  const [showModal, setShowModal] = useState(false);

  const handleAddComment = () => {
    if (!newComment.trim()) return;

    const comment: Comment = {
      id: `c${Date.now()}`,
      author: "Você",
      avatar: "VC",
      content: newComment,
      timestamp: "agora",
      likes: 0,
      isLiked: false,
    };

    setComments(prev => [comment, ...prev]);
    setNewComment("");
    
    toast({
      title: "Comentário adicionado!",
      description: "Seu comentário foi publicado com sucesso.",
    });
  };

  const handleLikeComment = (commentId: string) => {
    setComments(prev => prev.map(comment => 
      comment.id === commentId 
        ? { 
            ...comment, 
            isLiked: !comment.isLiked,
            likes: comment.isLiked ? comment.likes - 1 : comment.likes + 1
          }
        : comment
    ));
  };

  return (
    <>
      <Button
        variant="outline"
        onClick={() => setShowModal(true)}
        className="w-full border-purple-200 hover:bg-purple-50"
      >
        <MessageCircle className="w-4 h-4 mr-2" />
        Ver Comentários ({comments.length})
      </Button>

      {showModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-lg w-full max-w-2xl max-h-[90vh] flex flex-col">
            <div className="flex items-center justify-between p-4 border-b">
              <h3 className="font-semibold text-lg">Comentários sobre {eventTitle}</h3>
              <Button
                variant="ghost"
                size="sm"
                onClick={() => setShowModal(false)}
                className="hover:bg-gray-100"
              >
                <X className="w-5 h-5" />
              </Button>
            </div>
            
            <div className="flex-1 overflow-y-auto p-4">
              {/* Add Comment */}
              <div className="flex space-x-3 mb-4">
                <Avatar className="w-8 h-8">
                  <AvatarFallback className="bg-purple-200 text-purple-600 text-xs">
                    VC
                  </AvatarFallback>
                </Avatar>
                <div className="flex-1 flex space-x-2">
                  <Input
                    placeholder="Escreva um comentário sobre este evento..."
                    value={newComment}
                    onChange={(e) => setNewComment(e.target.value)}
                    onKeyPress={(e) => e.key === 'Enter' && handleAddComment()}
                    className="flex-1 border-purple-200 focus:border-purple-500"
                  />
                  <Button 
                    size="sm" 
                    onClick={handleAddComment}
                    disabled={!newComment.trim()}
                    className="btn-purple"
                  >
                    <Send className="w-4 h-4" />
                  </Button>
                </div>
              </div>

              {/* Comments List */}
              <div className="space-y-4">
                {comments.map((comment) => (
                  <div key={comment.id} className="flex space-x-3">
                    <Avatar className="w-8 h-8">
                      <AvatarFallback className="bg-gray-200 text-gray-600 text-xs">
                        {comment.avatar}
                      </AvatarFallback>
                    </Avatar>
                    <div className="flex-1">
                      <div className="bg-gray-50 rounded-lg p-3">
                        <div className="flex items-center space-x-2 mb-1">
                          <span className="font-semibold text-sm">{comment.author}</span>
                          <span className="text-xs text-muted-foreground">{comment.timestamp}</span>
                        </div>
                        <p className="text-sm text-gray-700">{comment.content}</p>
                      </div>
                      <div className="flex items-center mt-2">
                        <Button
                          variant="ghost"
                          size="sm"
                          onClick={() => handleLikeComment(comment.id)}
                          className={`text-xs ${comment.isLiked ? 'text-red-500' : 'text-gray-500'} hover:bg-red-50`}
                        >
                          <Heart className={`w-3 h-3 mr-1 ${comment.isLiked ? 'fill-red-500' : ''}`} />
                          {comment.likes}
                        </Button>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default EventComments;
