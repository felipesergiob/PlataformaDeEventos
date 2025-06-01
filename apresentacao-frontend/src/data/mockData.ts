
// Mock data for the application
// This file centralizes all mock data to make it easy to replace with real backend integration later

export interface User {
  id: string;
  name: string;
  bio: string;
  avatar: string;
  joinedDate: string;
  location: string;
  followers: number;
  following: number;
  eventsOrganized: number;
  eventsAttended: number;
  averageRating: number;
}

export interface Event {
  id: number;
  title: string;
  description: string;
  date: string;
  time: string;
  endTime?: string;
  location: string;
  address?: string;
  genre: string;
  price: number;
  image?: string;
  organizerId: string;
  participants: number;
  maxParticipants?: number;
  rating?: number;
  reviews?: number;
  isPast?: boolean;
  totalRatings?: number;
  comments?: number;
  revenue?: number;
}

export interface Comment {
  id: string;
  userId: string;
  userName: string;
  userAvatar: string;
  comment: string;
  date: string;
  likes: number;
  replies: Reply[];
}

export interface Reply {
  id: string;
  userId: string;
  userName: string;
  comment: string;
  date: string;
  isOrganizer?: boolean;
}

export interface Review {
  id: number;
  eventTitle: string;
  rating: number;
  comment: string;
  author: string;
  date: string;
}

// Mock Users
export const mockUsers: Record<string, User> = {
  'current-user': {
    id: 'current-user',
    name: 'Maria Silva',
    bio: 'Organizadora de eventos de tecnologia e educação. Apaixonada por conectar pessoas e criar experiências memoráveis.',
    avatar: '/placeholder.svg',
    joinedDate: '2023-01-15',
    location: 'São Paulo, SP',
    followers: 245,
    following: 89,
    eventsOrganized: 12,
    eventsAttended: 34,
    averageRating: 4.8
  },
  'org1': {
    id: 'org1',
    name: 'Tech Academy',
    bio: 'Especialista em eventos de tecnologia e programação. Mais de 5 anos organizando workshops e palestras para desenvolvedores.',
    avatar: '/placeholder.svg',
    joinedDate: '2022-03-10',
    location: 'São Paulo, SP',
    followers: 318,
    following: 124,
    eventsOrganized: 18,
    eventsAttended: 42,
    averageRating: 4.7
  },
  'user1': {
    id: 'user1',
    name: 'João Santos',
    bio: 'Desenvolvedor full-stack apaixonado por aprender novas tecnologias.',
    avatar: '/placeholder.svg',
    joinedDate: '2023-06-20',
    location: 'Rio de Janeiro, RJ',
    followers: 89,
    following: 156,
    eventsOrganized: 3,
    eventsAttended: 28,
    averageRating: 4.5
  },
  'user2': {
    id: 'user2',
    name: 'Ana Oliveira',
    bio: 'Designer UX/UI sempre em busca de inspiração em eventos criativos.',
    avatar: '/placeholder.svg',
    joinedDate: '2023-04-08',
    location: 'Belo Horizonte, MG',
    followers: 134,
    following: 203,
    eventsOrganized: 7,
    eventsAttended: 45,
    averageRating: 4.6
  }
};

// Mock Events
export const mockEvents: Event[] = [
  {
    id: 1,
    title: 'Workshop React Avançado',
    description: 'Aprenda técnicas avançadas de React com especialistas da área. Neste workshop intensivo, vamos cobrir tópicos como hooks avançados, otimização de performance, padrões de componentização escaláveis e muito mais.',
    date: '2024-06-15',
    time: '19:00',
    endTime: '22:00',
    location: 'Centro de Tecnologia - São Paulo',
    address: 'Av. Paulista, 1000 - Bela Vista, São Paulo - SP',
    genre: 'Educação',
    price: 150,
    image: '/placeholder.svg',
    organizerId: 'org1',
    participants: 45,
    maxParticipants: 60,
    rating: 4.8,
    reviews: 23
  },
  {
    id: 2,
    title: 'Festival de Jazz ao Ar Livre',
    description: 'Uma noite mágica com os melhores artistas de jazz da cidade',
    date: '2024-06-20',
    time: '20:00',
    location: 'Parque Ibirapuera - SP',
    genre: 'Música',
    price: 0,
    organizerId: 'current-user',
    participants: 120,
    maxParticipants: 200
  },
  {
    id: 3,
    title: 'Palestra: O Futuro da IA',
    description: 'Discussão sobre as tendências e impactos da Inteligência Artificial',
    date: '2024-05-25',
    time: '14:00',
    location: 'Auditório da USP - SP',
    genre: 'Tecnologia',
    price: 50,
    organizerId: 'org1',
    participants: 80,
    rating: 4.7,
    totalRatings: 45,
    comments: 23,
    revenue: 4000,
    isPast: true
  },
  {
    id: 4,
    title: 'Meetup de Desenvolvedores',
    description: 'Encontro para networking entre desenvolvedores web',
    date: '2024-05-18',
    time: '18:30',
    location: 'Coworking Tech - SP',
    genre: 'Tecnologia',
    price: 0,
    organizerId: 'current-user',
    participants: 35,
    rating: 4.8,
    totalRatings: 28,
    comments: 15,
    revenue: 0,
    isPast: true
  }
];

// Mock Comments
export const mockComments: Record<string, Comment[]> = {
  '1': [
    {
      id: '1',
      userId: 'user1',
      userName: 'João Santos',
      userAvatar: '/placeholder.svg',
      comment: 'Estou muito animado para este evento! Será que terão coffee break?',
      date: '2024-05-20',
      likes: 5,
      replies: [
        {
          id: 'reply1',
          userId: 'org1',
          userName: 'Tech Academy',
          comment: 'Sim, teremos coffee break com salgados e doces!',
          date: '2024-05-20',
          isOrganizer: true
        }
      ]
    },
    {
      id: '2',
      userId: 'user2',
      userName: 'Ana Oliveira',
      userAvatar: '/placeholder.svg',
      comment: 'Haverá certificado de participação?',
      date: '2024-05-19',
      likes: 3,
      replies: []
    }
  ]
};

// Mock Reviews for users
export const mockUserReviews: Record<string, Review[]> = {
  'org1': [
    {
      id: 1,
      eventTitle: 'Workshop React Avançado',
      rating: 5,
      comment: 'Evento incrível! Muito bem organizado e conteúdo de alta qualidade.',
      author: 'João Santos',
      date: '2024-05-21'
    },
    {
      id: 2,
      eventTitle: 'Palestra: Futuro da IA',
      rating: 5,
      comment: 'Palestrantes excelentes e organização impecável. Recomendo!',
      author: 'Ana Oliveira',
      date: '2024-04-16'
    }
  ],
  'current-user': [
    {
      id: 3,
      eventTitle: 'Meetup de Desenvolvedores',
      rating: 4,
      comment: 'Bom networking, mas poderia ter mais tempo para interação.',
      author: 'Carlos Mendes',
      date: '2024-05-19'
    }
  ]
};

// Helper functions to get data (these would be replaced with API calls later)
export const getUser = (userId: string): User | undefined => {
  return mockUsers[userId];
};

export const getEvent = (eventId: number): Event | undefined => {
  return mockEvents.find(event => event.id === eventId);
};

export const getEventsByOrganizer = (organizerId: string): Event[] => {
  return mockEvents.filter(event => event.organizerId === organizerId);
};

export const getComments = (eventId: string): Comment[] => {
  return mockComments[eventId] || [];
};

export const getUserReviews = (userId: string): Review[] => {
  return mockUserReviews[userId] || [];
};

export const getUpcomingEvents = (): Event[] => {
  return mockEvents.filter(event => !event.isPast);
};

export const getPastEvents = (): Event[] => {
  return mockEvents.filter(event => event.isPast);
};
