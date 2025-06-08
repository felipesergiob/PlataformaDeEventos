import React, { useState, useEffect } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Textarea } from '@/components/ui/textarea';
import { Input } from '@/components/ui/input';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Calendar, Camera, MessageCircle, ThumbsUp, Image, Share, Send } from 'lucide-react';
import { cn } from '@/lib/utils';
import { useToast } from '@/hooks/use-toast';
import { getEventPosts, EventPost } from '@/data/mockData';
import { publicacaoApi, PublicacaoResponse, userApi } from '@/services/api';
import { useAuth } from '@/contexts/AuthContext';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';

interface EventPostsProps {
  eventId: string;
  hasAttended: boolean;
}

interface UserInfo {
  [key: string]: string;
}

const EventPosts: React.FC<EventPostsProps> = ({ eventId, hasAttended }) => {
  const { toast } = useToast();
  const { user } = useAuth();
  const [posts, setPosts] = useState<PublicacaoResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [userInfo, setUserInfo] = useState<UserInfo>({});
  const [newPost, setNewPost] = useState({
    titulo: '',
    conteudo: '',
    fotos: ''
  });
  const [selectedImage, setSelectedImage] = useState<File | null>(null);

  useEffect(() => {
    fetchPosts();
  }, [eventId]);

  const fetchPosts = async () => {
    try {
      const data = await publicacaoApi.listarPublicacoesPorEvento(eventId);
      setPosts(data);
      
      // Buscar informações dos usuários
      const userIds = [...new Set(data.map(post => post.usuarioId))];
      const userInfoMap: UserInfo = {};
      
      for (const userId of userIds) {
        try {
          const userData = await userApi.getUserById(userId);
          userInfoMap[userId] = userData.nome;
        } catch (error) {
          console.error(`Erro ao buscar usuário ${userId}:`, error);
          userInfoMap[userId] = 'Usuário';
        }
      }
      
      setUserInfo(userInfoMap);
    } catch (error) {
      console.error('Erro ao buscar publicações:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setSelectedImage(e.target.files[0]);
      // Aqui você pode implementar o upload da imagem para um serviço de armazenamento
      // e obter a URL da imagem
      setNewPost(prev => ({ ...prev, fotos: e.target.files![0].name }));
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user) return;

    try {
      await publicacaoApi.criarPublicacao({
        eventoId: Number(eventId),
        usuarioId: Number(user.id),
        titulo: newPost.titulo,
        conteudo: newPost.conteudo,
        fotos: newPost.fotos
      });

      setNewPost({ titulo: '', conteudo: '', fotos: '' });
      setSelectedImage(null);
      fetchPosts();
    } catch (error) {
      console.error('Erro ao criar publicação:', error);
    }
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: 'short',
      year: 'numeric'
    });
  };

  if (loading) {
    return <div className="text-center py-4">Carregando publicações...</div>;
  }

  return (
    <div className="space-y-6">
      {hasAttended && user && (
        <Card>
          <CardHeader>
            <CardTitle>Criar Publicação</CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-4">
              <Input
                placeholder="Título da publicação"
                value={newPost.titulo}
                onChange={(e) => setNewPost(prev => ({ ...prev, titulo: e.target.value }))}
                required
              />
              <Textarea
                placeholder="Compartilhe sua experiência..."
                value={newPost.conteudo}
                onChange={(e) => setNewPost(prev => ({ ...prev, conteudo: e.target.value }))}
                required
              />
              <div className="flex items-center gap-4">
                <label className="cursor-pointer">
                  <Input
                    type="file"
                    accept="image/*"
                    onChange={handleImageChange}
                    className="hidden"
                  />
                  <div className="flex items-center gap-2 text-purple-600 hover:text-purple-700">
                    <Image className="w-5 h-5" />
                    <span>Adicionar foto</span>
                  </div>
                </label>
                {selectedImage && (
                  <span className="text-sm text-gray-500">{selectedImage.name}</span>
                )}
              </div>
              <Button type="submit" className="w-full bg-purple-600 hover:bg-purple-700 text-white">
                <Send className="w-4 h-4 mr-2" />
                Publicar
              </Button>
            </form>
          </CardContent>
        </Card>
      )}

      <div className="space-y-4">
        {posts.map((post) => (
          <Card key={post.id}>
            <CardContent className="p-6">
              <div className="flex items-start gap-4">
                <Avatar>
                  <AvatarImage src={`/imagens/${post.fotos}`} />
                  <AvatarFallback>{userInfo[post.usuarioId]?.[0] || 'U'}</AvatarFallback>
                </Avatar>
                <div className="flex-1">
                  <div className="flex items-center gap-2">
                    <h3 className="font-semibold text-lg">{post.titulo}</h3>
                    <span className="text-sm text-gray-500">•</span>
                    <span className="text-sm text-gray-500">{userInfo[post.usuarioId] || 'Usuário'}</span>
                  </div>
                  <p className="text-gray-600 mt-2">{post.conteudo}</p>
                  {post.fotos && (
                    <img
                      src={`/imagens/${post.fotos}`}
                      alt="Publicação"
                      className="mt-4 rounded-lg max-h-96 object-cover"
                    />
                  )}
                  <p className="text-sm text-gray-500 mt-2">
                    {format(new Date(post.dataCriacao), "d 'de' MMMM 'às' HH:mm", { locale: ptBR })}
                  </p>
                </div>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
};

export default EventPosts;
