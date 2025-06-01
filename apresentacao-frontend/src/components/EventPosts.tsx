import React, { useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Textarea } from '@/components/ui/textarea';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Calendar, Camera, MessageCircle, ThumbsUp, Image, Share } from 'lucide-react';
import { cn } from '@/lib/utils';
import { useToast } from '@/hooks/use-toast';

interface EventPostsProps {
  eventId: string;
  hasAttended: boolean;
}

const EventPosts = ({ eventId, hasAttended }: EventPostsProps) => {
  const { toast } = useToast();
  const [postContent, setPostContent] = useState('');
  const [postPhotos, setPostPhotos] = useState<File[]>([]);
  const [showPostForm, setShowPostForm] = useState(false);

  // Mock posts data
  const posts = [
    {
      id: '1',
      userId: 'user1',
      userName: 'João Santos',
      userAvatar: '/placeholder.svg',
      content: 'Acabei de participar deste incrível workshop sobre React! Aprendi muitas técnicas avançadas que com certeza vou aplicar nos meus projetos. O networking também foi excelente, conheci muitas pessoas interessantes da área. Recomendo demais!',
      date: '2024-05-21',
      photos: ['/placeholder.svg', '/placeholder.svg'],
      likes: 24,
      comments: 5
    },
    {
      id: '2',
      userId: 'user2',
      userName: 'Maria Oliveira',
      userAvatar: '/placeholder.svg',
      content: 'O palestrante tinha um domínio incrível do assunto. Consegui tirar todas as minhas dúvidas e saí do evento com uma visão muito mais clara sobre o futuro da IA e como podemos nos preparar para essas mudanças.',
      date: '2024-05-20',
      photos: ['/placeholder.svg'],
      likes: 18,
      comments: 3
    }
  ];

  const handlePhotoUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = Array.from(event.target.files || []);
    setPostPhotos(prev => [...prev, ...files].slice(0, 5)); // Limit to 5 photos
  };

  const handleSubmitPost = () => {
    if (!postContent.trim() && postPhotos.length === 0) return;

    // Here you would submit the post to your backend
    console.log({
      eventId,
      content: postContent,
      photos: postPhotos
    });

    // Reset form
    setPostContent('');
    setPostPhotos([]);
    setShowPostForm(false);
    
    toast({
      title: "Publicação enviada",
      description: "Sua experiência foi compartilhada com sucesso!",
      variant: "default"
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
    <Card>
      <CardHeader>
        <CardTitle>Relatos e Experiências</CardTitle>
      </CardHeader>
      <CardContent className="space-y-6">
        {/* Post Form */}
        {hasAttended && (
          <div className="mb-6">
            {!showPostForm ? (
              <Button 
                onClick={() => setShowPostForm(true)}
                variant="outline"
                className="w-full border-dashed border-purple-200 text-purple-600 hover:bg-purple-50"
              >
                <Image className="w-4 h-4 mr-2" />
                Compartilhe sua experiência neste evento
              </Button>
            ) : (
              <div className="space-y-4 p-4 border border-purple-200 rounded-lg">
                <h3 className="text-lg font-semibold">Criar Publicação</h3>
                <Textarea
                  placeholder="Conte como foi sua experiência neste evento..."
                  value={postContent}
                  onChange={(e) => setPostContent(e.target.value)}
                  rows={4}
                />
                
                <div className="flex flex-wrap gap-2">
                  {postPhotos.map((photo, index) => (
                    <div key={index} className="relative w-20 h-20 bg-gray-100 rounded-md overflow-hidden">
                      <img 
                        src={URL.createObjectURL(photo)} 
                        alt={`Foto ${index + 1}`} 
                        className="w-full h-full object-cover"
                      />
                      <button 
                        className="absolute top-1 right-1 w-5 h-5 bg-red-500 text-white rounded-full flex items-center justify-center text-xs"
                        onClick={() => setPostPhotos(prev => prev.filter((_, i) => i !== index))}
                      >
                        ×
                      </button>
                    </div>
                  ))}
                  
                  {postPhotos.length < 5 && (
                    <div className="w-20 h-20 flex items-center justify-center bg-gray-50 border border-dashed border-gray-300 rounded-md cursor-pointer relative">
                      <Camera className="w-6 h-6 text-gray-400" />
                      <input
                        type="file"
                        multiple
                        accept="image/*"
                        onChange={handlePhotoUpload}
                        className="opacity-0 absolute inset-0 cursor-pointer"
                      />
                    </div>
                  )}
                </div>
                
                <div className="flex space-x-2 justify-end">
                  <Button 
                    variant="outline"
                    onClick={() => {
                      setShowPostForm(false);
                      setPostContent('');
                      setPostPhotos([]);
                    }}
                  >
                    Cancelar
                  </Button>
                  <Button 
                    onClick={handleSubmitPost}
                    disabled={!postContent.trim() && postPhotos.length === 0}
                    className="bg-purple-600 hover:bg-purple-700"
                  >
                    Publicar
                  </Button>
                </div>
              </div>
            )}
          </div>
        )}

        {/* Existing Posts */}
        <div className="space-y-8">
          {posts.map((post) => (
            <div key={post.id} className="border-b border-gray-200 pb-8 last:border-b-0">
              {/* Post Header */}
              <div className="flex items-start space-x-3 mb-3">
                <Avatar className="w-10 h-10">
                  <AvatarImage src={post.userAvatar} alt={post.userName} />
                  <AvatarFallback>
                    {post.userName.split(' ').map(n => n[0]).join('')}
                  </AvatarFallback>
                </Avatar>
                <div>
                  <h4 className="font-semibold text-gray-900">{post.userName}</h4>
                  <div className="flex items-center text-xs text-gray-500">
                    <Calendar className="w-3 h-3 mr-1" />
                    <span>{formatDate(post.date)}</span>
                  </div>
                </div>
              </div>

              {/* Post Content */}
              <p className="text-gray-700 mb-4 whitespace-pre-line">{post.content}</p>

              {/* Post Photos */}
              {post.photos.length > 0 && (
                <div className="mb-4">
                  {post.photos.length === 1 ? (
                    <div className="bg-gray-100 rounded-lg overflow-hidden">
                      <img 
                        src={post.photos[0]} 
                        alt="Foto do evento"
                        className="w-full h-auto object-cover max-h-96"
                      />
                    </div>
                  ) : (
                    <div className="grid grid-cols-2 gap-2">
                      {post.photos.map((photo, index) => (
                        <div key={index} className="bg-gray-100 rounded-lg overflow-hidden">
                          <img 
                            src={photo} 
                            alt={`Foto ${index + 1}`}
                            className="w-full h-48 object-cover"
                          />
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              )}

              {/* Post Actions */}
              <div className="flex items-center justify-between">
                <div className="flex space-x-4">
                  <Button variant="ghost" size="sm" className="text-gray-600 hover:text-purple-600">
                    <ThumbsUp className="w-4 h-4 mr-1" />
                    <span>{post.likes}</span>
                  </Button>
                  <Button variant="ghost" size="sm" className="text-gray-600 hover:text-purple-600">
                    <MessageCircle className="w-4 h-4 mr-1" />
                    <span>{post.comments}</span>
                  </Button>
                </div>
                <Button variant="ghost" size="sm" className="text-gray-600 hover:text-purple-600">
                  <Share className="w-4 h-4 mr-1" />
                  Compartilhar
                </Button>
              </div>
            </div>
          ))}

          {posts.length === 0 && (
            <div className="text-center py-6">
              <Image className="w-12 h-12 text-gray-300 mx-auto mb-2" />
              <p className="text-gray-600">Nenhum relato publicado ainda. Seja o primeiro a compartilhar sua experiência!</p>
            </div>
          )}
        </div>
      </CardContent>
    </Card>
  );
};

export default EventPosts;
