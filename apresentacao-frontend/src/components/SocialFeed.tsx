import { useState, useEffect } from "react";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { Input } from "@/components/ui/input";
import { Heart, MessageCircle, Share2, Calendar, MapPin, Users, Send } from "lucide-react";
import { Event } from "@/components/EventCard";
import { FilterState } from "@/components/EventFilters";
import { toast } from "@/hooks/use-toast";

interface Comment {
  id: string;
  author: string;
  avatar: string;
  content: string;
  timestamp: string;
}

interface Post {
  id: string;
  author: string;
  avatar: string;
  content: string;
  image?: string;
  event?: Event;
  timestamp: string;
  likes: number;
  comments: Comment[];
  isLiked: boolean;
}

interface SocialFeedProps {
  events: Event[];
  filters?: FilterState;
}

const SocialFeed = ({ events, filters }: SocialFeedProps) => {
  // Mock posts data com imagens
  const [allPosts] = useState<Post[]>([
    {
      id: "1",
      author: "Maria Silva",
      avatar: "MS",
      content: "Que show incrível foi ontem! O som estava perfeito e a energia da galera foi contagiante! 🎵",
      image: "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=600&h=400&fit=crop",
      event: events[0],
      timestamp: "2h",
      likes: 24,
      comments: [
        {
          id: "c1",
          author: "João Santos",
          avatar: "JS",
          content: "Concordo! Foi épico demais!",
          timestamp: "1h"
        },
        {
          id: "c2",
          author: "Ana Costa",
          avatar: "AC",
          content: "Já estou ansiosa pelo próximo 🔥",
          timestamp: "30min"
        }
      ],
      isLiked: false,
    },
    {
      id: "2",
      author: "João Santos",
      avatar: "JS",
      content: "Acabei de me inscrever! Festival de música eletrônica vai ser épico! A line-up está sensacional! 🎵🔥",
      image: "https://images.unsplash.com/photo-1605810230434-7631ac76ec81?w=600&h=400&fit=crop",
      event: events[1],
      timestamp: "4h",
      likes: 18,
      comments: [
        {
          id: "c3",
          author: "Carlos Lima",
          avatar: "CL",
          content: "Vou estar lá também! Vai ser massa!",
          timestamp: "2h"
        }
      ],
      isLiked: true,
    },
    {
      id: "3",
      author: "Ana Costa",
      avatar: "AC",
      content: "Que festa junina incrível! A comida estava deliciosa e a quadrilha foi muito divertida! 🌽🎆",
      image: "https://images.unsplash.com/photo-1500375592092-40eb2168fd21?w=600&h=400&fit=crop",
      timestamp: "6h",
      likes: 15,
      comments: [],
      isLiked: false,
    },
    {
      id: "4",
      author: "Carlos Lima",
      avatar: "CL",
      content: "Show de rock nacional foi sensacional! As bandas arrasaram no palco! 🎸🔥",
      image: "https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=600&h=400&fit=crop",
      event: events[0],
      timestamp: "8h",
      likes: 32,
      comments: [],
      isLiked: false,
    },
    {
      id: "5",
      author: "Pedro Santos",
      avatar: "PS",
      content: "Festa eletrônica underground foi incrível! Os DJs mandaram muito bem! 🎧⚡",
      event: events[1],
      timestamp: "12h",
      likes: 28,
      comments: [],
      isLiked: true,
    },
  ]);

  const [posts, setPosts] = useState<Post[]>(allPosts);
  const [showComments, setShowComments] = useState<Record<string, boolean>>({});
  const [newComment, setNewComment] = useState<Record<string, string>>({});

  // Apply filters to posts
  useEffect(() => {
    if (!filters) {
      setPosts(allPosts);
      return;
    }

    let filtered = [...allPosts];

    // Text search
    if (filters.search) {
      filtered = filtered.filter(post =>
        post.content.toLowerCase().includes(filters.search.toLowerCase()) ||
        post.author.toLowerCase().includes(filters.search.toLowerCase()) ||
        (post.event && (
          post.event.title.toLowerCase().includes(filters.search.toLowerCase()) ||
          post.event.description.toLowerCase().includes(filters.search.toLowerCase()) ||
          post.event.location.toLowerCase().includes(filters.search.toLowerCase())
        ))
      );
    }

    // Category filter (based on event category)
    if (filters.category) {
      filtered = filtered.filter(post => 
        post.event && post.event.category === filters.category
      );
    }

    // Price range filter (based on event price)
    if (filters.priceRange) {
      filtered = filtered.filter(post => {
        if (!post.event) return false;
        if (filters.priceRange === "free") return post.event.price === 0;
        if (filters.priceRange === "0-50") return post.event.price >= 0 && post.event.price <= 50;
        if (filters.priceRange === "50-100") return post.event.price > 50 && post.event.price <= 100;
        if (filters.priceRange === "100-200") return post.event.price > 100 && post.event.price <= 200;
        if (filters.priceRange === "200+") return post.event.price > 200;
        return true;
      });
    }

    // Location filter (based on event location)
    if (filters.location) {
      filtered = filtered.filter(post =>
        post.event && post.event.location.toLowerCase().includes(filters.location.toLowerCase())
      );
    }

    // Date filter (based on event date)
    if (filters.date) {
      const today = new Date();
      const tomorrow = new Date(today);
      tomorrow.setDate(tomorrow.getDate() + 1);

      filtered = filtered.filter(post => {
        if (!post.event) return false;
        const eventDate = new Date(post.event.date);
        if (filters.date === "today") {
          return eventDate.toDateString() === today.toDateString();
        }
        if (filters.date === "tomorrow") {
          return eventDate.toDateString() === tomorrow.toDateString();
        }
        if (filters.date === "week") {
          const weekFromNow = new Date(today);
          weekFromNow.setDate(weekFromNow.getDate() + 7);
          return eventDate >= today && eventDate <= weekFromNow;
        }
        return true;
      });
    }

    // Time filter (based on event time)
    if (filters.time) {
      filtered = filtered.filter(post => {
        if (!post.event) return false;
        const eventHour = parseInt(post.event.time.split(':')[0]);
        if (filters.time === "manha") return eventHour >= 6 && eventHour < 12;
        if (filters.time === "tarde") return eventHour >= 12 && eventHour < 18;
        if (filters.time === "noite") return eventHour >= 18 && eventHour < 24;
        if (filters.time === "madrugada") return eventHour >= 0 && eventHour < 6;
        return true;
      });
    }

    setPosts(filtered);
  }, [filters, allPosts]);

  const handleLike = (postId: string) => {
    setPosts(posts.map(post => 
      post.id === postId 
        ? { 
            ...post, 
            isLiked: !post.isLiked,
            likes: post.isLiked ? post.likes - 1 : post.likes + 1
          }
        : post
    ));
  };

  const handleComment = (postId: string) => {
    setShowComments(prev => ({
      ...prev,
      [postId]: !prev[postId]
    }));
  };

  const handleAddComment = (postId: string) => {
    const comment = newComment[postId]?.trim();
    if (!comment) return;

    const newCommentObj: Comment = {
      id: `c${Date.now()}`,
      author: "Você",
      avatar: "VC",
      content: comment,
      timestamp: "agora"
    };

    setPosts(posts.map(post => 
      post.id === postId 
        ? { ...post, comments: [...post.comments, newCommentObj] }
        : post
    ));

    setNewComment(prev => ({ ...prev, [postId]: "" }));
    
    toast({
      title: "Comentário adicionado!",
      description: "Seu comentário foi publicado com sucesso.",
    });
  };

  const handleShare = (postId: string) => {
    toast({
      title: "Link copiado!",
      description: "O link do post foi copiado para a área de transferência.",
    });
  };

  const getCategoryColor = (category: string) => {
    const colors = {
      musica: "bg-pink-100 text-pink-800",
      festa: "bg-purple-100 text-purple-800",
      show: "bg-blue-100 text-blue-800",
    };
    return colors[category as keyof typeof colors] || "bg-gray-100 text-gray-800";
  };

  const formatPrice = (price: number) => {
    if (price === 0) return "Gratuito";
    return new Intl.NumberFormat("pt-BR", {
      style: "currency",
      currency: "BRL",
    }).format(price);
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between mb-6">
        <h2 className="text-2xl font-bold text-gray-900">
        Feed de Postagens
      </h2>
      <span className="text-muted-foreground">
        {posts.length} postagem{posts.length !== 1 ? "ns" : ""} encontrada{posts.length !== 1 ? "s" : ""}
      </span>
    </div>

      {posts.length === 0 && filters ? (
        <div className="text-center py-16">
          <div className="w-24 h-24 bg-gradient-to-br from-purple-100 to-purple-200 rounded-full mx-auto mb-4 flex items-center justify-center">
            <span className="text-2xl">🔍</span>
          </div>
          <h3 className="text-xl font-semibold mb-2">Nenhuma postagem encontrada</h3>
          <p className="text-muted-foreground">
            Tente ajustar os filtros para encontrar postagens
          </p>
        </div>
      ) : (
        posts.map((post) => (
          <Card key={post.id} className="border-purple-100 hover:shadow-lg transition-shadow">
            <CardHeader className="pb-3">
              <div className="flex items-center space-x-3">
                <Avatar className="w-10 h-10">
                  <AvatarFallback className="bg-gradient-to-br from-purple-400 to-purple-600 text-white text-sm">
                    {post.avatar}
                  </AvatarFallback>
                </Avatar>
                <div className="flex-1">
                  <div className="flex items-center space-x-2">
                    <h3 className="font-semibold text-gray-900">{post.author}</h3>
                    <span className="text-sm text-muted-foreground">•</span>
                    <span className="text-sm text-muted-foreground">{post.timestamp}</span>
                  </div>
                </div>
              </div>
            </CardHeader>
            
            <CardContent className="space-y-4">
              <p className="text-gray-700 line-clamp-3">{post.content}</p>
              
              {/* Post Image */}
              {post.image && (
                <div className="rounded-lg overflow-hidden">
                  <img 
                    src={post.image} 
                    alt="Post"
                    className="w-full h-48 object-cover hover:scale-105 transition-transform duration-300"
                  />
                </div>
              )}
              
              {post.event && (
                <div className="border border-purple-200 rounded-lg p-3 bg-gradient-to-r from-purple-50 to-white">
                  <div className="flex justify-between items-start mb-2">
                    <h4 className="font-semibold text-gray-900 text-sm">{post.event.title}</h4>
                    <Badge className={`${getCategoryColor(post.event.category)} text-xs`}>
                      {post.event.category}
                    </Badge>
                  </div>
                  
                  {/* {post.event.image && (
                    <div className="mb-2 rounded-lg overflow-hidden">
                      <img 
                        src={post.event.image} 
                        alt={post.event.title}
                        className="w-full h-24 object-cover"
                      />
                    </div>
                  )}
                   */}
                  <p className="text-xs text-muted-foreground mb-2 line-clamp-1">
                    {post.event.description}
                  </p>
                  
                  <div className="grid grid-cols-2 gap-2 text-xs">
                    <div className="flex items-center text-muted-foreground">
                      <Calendar className="w-3 h-3 mr-1 text-purple-500" />
                      {post.event.date}
                    </div>
                    <div className="flex items-center text-muted-foreground">
                      <MapPin className="w-3 h-3 mr-1 text-purple-500" />
                      {post.event.location}
                    </div>
                    <div className="flex items-center text-muted-foreground">
                      <Users className="w-3 h-3 mr-1 text-purple-500" />
                      {post.event.enrolled}/{post.event.capacity}
                    </div>
                    <div className="flex items-center text-muted-foreground">
                      <span className="font-semibold text-purple-600">
                        {formatPrice(post.event.price)}
                      </span>
                    </div>
                  </div>
                </div>
              )}
              
              <div className="flex items-center justify-between pt-2 border-t border-purple-100">
                <div className="flex items-center space-x-4">
                  <Button
                    variant="ghost"
                    size="sm"
                    onClick={() => handleLike(post.id)}
                    className={`hover:bg-red-50 ${post.isLiked ? 'text-red-500' : 'text-gray-500'}`}
                  >
                    <Heart className={`w-4 h-4 mr-1 ${post.isLiked ? 'fill-red-500' : ''}`} />
                    {post.likes}
                  </Button>
                  
                  <Button 
                    variant="ghost" 
                    size="sm" 
                    className="text-gray-500 hover:bg-blue-50 hover:text-blue-600"
                    onClick={() => handleComment(post.id)}
                  >
                    <MessageCircle className="w-4 h-4 mr-1" />
                    {post.comments.length}
                  </Button>
                  
                  <Button 
                    variant="ghost" 
                    size="sm" 
                    className="text-gray-500 hover:bg-green-50 hover:text-green-600"
                    onClick={() => handleShare(post.id)}
                  >
                    <Share2 className="w-4 h-4 mr-1" />
                    Compartilhar
                  </Button>
                </div>
              </div>

              {/* Comments Section */}
              {showComments[post.id] && (
                <div className="space-y-4 pt-4 border-t border-purple-100">
                  {post.comments.map((comment) => (
                    <div key={comment.id} className="flex space-x-3">
                      <Avatar className="w-8 h-8">
                        <AvatarFallback className="bg-gray-200 text-gray-600 text-xs">
                          {comment.avatar}
                        </AvatarFallback>
                      </Avatar>
                      <div className="flex-1 bg-gray-50 rounded-lg p-3">
                        <div className="flex items-center space-x-2 mb-1">
                          <span className="font-semibold text-sm">{comment.author}</span>
                          <span className="text-xs text-muted-foreground">{comment.timestamp}</span>
                        </div>
                        <p className="text-sm text-gray-700">{comment.content}</p>
                      </div>
                    </div>
                  ))}
                  
                  {/* Add Comment */}
                  <div className="flex space-x-3">
                    <Avatar className="w-8 h-8">
                      <AvatarFallback className="bg-purple-200 text-purple-600 text-xs">
                        VC
                      </AvatarFallback>
                    </Avatar>
                    <div className="flex-1 flex space-x-2">
                      <Input
                        placeholder="Escreva um comentário..."
                        value={newComment[post.id] || ""}
                        onChange={(e) => setNewComment(prev => ({ ...prev, [post.id]: e.target.value }))}
                        onKeyPress={(e) => e.key === 'Enter' && handleAddComment(post.id)}
                        className="flex-1"
                      />
                      <Button 
                        size="sm" 
                        onClick={() => handleAddComment(post.id)}
                        disabled={!newComment[post.id]?.trim()}
                        className="btn-purple"
                      >
                        <Send className="w-4 h-4" />
                      </Button>
                    </div>
                  </div>
                </div>
              )}
            </CardContent>
          </Card>
        ))
      )}
    </div>
  );
};

export default SocialFeed;
