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

export interface EventAnalytics {
  id: number;
  title: string;
  description: string;
  date: string;
  time: string;
  location: string;
  genre: string;
  price: number;
  participants: number;
  maxParticipants: number;
  rating: number;
  totalRatings: number;
  comments: number;
  revenue: number;
  expenses: number;
  profit: number;
}

export interface RatingDistribution {
  rating: string;
  count: number;
  percentage: number;
}

export interface DemographicsData {
  name: string;
  value: number;
  fill: string;
}

export interface TopComment {
  id: string;
  userName: string;
  userAvatar: string;
  rating: number;
  comment: string;
  date: string;
}

export interface CalendarEvent {
  id: number;
  title: string;
  date: string;
  time: string;
  location: string;
  status: 'confirmed' | 'maybe' | 'saved';
  type: 'confirmed' | 'maybe' | 'saved';
}

export interface EventPost {
  id: string;
  userId: string;
  userName: string;
  userAvatar: string;
  content: string;
  date: string;
  photos: string[];
  likes: number;
  comments: number;
}

export interface EventEvaluation {
  id: string;
  userId: string;
  userName: string;
  userAvatar: string;
  rating: number;
  comment: string;
  date: string;
  photos: string[];
  likes: number;
  replies: {
    id: string;
    userId: string;
    userName: string;
    comment: string;
    date: string;
    isOrganizer: boolean;
  }[];
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

// Mock Analytics Data
export const mockEventAnalytics: Record<number, EventAnalytics> = {
  3: {
    id: 3,
    title: 'Palestra: O Futuro da IA',
    description: 'Discussão sobre as tendências e impactos da Inteligência Artificial',
    date: '2024-05-25',
    time: '14:00',
    location: 'Auditório da USP - SP',
    genre: 'Tecnologia',
    price: 50,
    participants: 80,
    maxParticipants: 100,
    rating: 4.7,
    totalRatings: 45,
    comments: 23,
    revenue: 4000,
    expenses: 1200,
    profit: 2800
  },
  4: {
    id: 4,
    title: 'Meetup de Desenvolvedores',
    description: 'Encontro para networking entre desenvolvedores web',
    date: '2024-05-18',
    time: '18:30',
    location: 'Coworking Tech - SP',
    genre: 'Tecnologia',
    price: 0,
    participants: 35,
    maxParticipants: 50,
    rating: 4.8,
    totalRatings: 28,
    comments: 15,
    revenue: 0,
    expenses: 500,
    profit: -500
  }
};

export const mockRatingDistribution: Record<number, RatingDistribution[]> = {
  3: [
    { rating: '5 ⭐', count: 25, percentage: 56 },
    { rating: '4 ⭐', count: 12, percentage: 27 },
    { rating: '3 ⭐', count: 5, percentage: 11 },
    { rating: '2 ⭐', count: 2, percentage: 4 },
    { rating: '1 ⭐', count: 1, percentage: 2 }
  ],
  4: [
    { rating: '5 ⭐', count: 18, percentage: 64 },
    { rating: '4 ⭐', count: 7, percentage: 25 },
    { rating: '3 ⭐', count: 2, percentage: 7 },
    { rating: '2 ⭐', count: 1, percentage: 4 },
    { rating: '1 ⭐', count: 0, percentage: 0 }
  ]
};

export const mockDemographicsData: Record<number, DemographicsData[]> = {
  3: [
    { name: '18-25', value: 30, fill: '#8B5CF6' },
    { name: '26-35', value: 35, fill: '#A78BFA' },
    { name: '36-45', value: 20, fill: '#C4B5FD' },
    { name: '46+', value: 15, fill: '#DDD6FE' }
  ],
  4: [
    { name: '18-25', value: 40, fill: '#8B5CF6' },
    { name: '26-35', value: 45, fill: '#A78BFA' },
    { name: '36-45', value: 10, fill: '#C4B5FD' },
    { name: '46+', value: 5, fill: '#DDD6FE' }
  ]
};

export const mockTopComments: Record<number, TopComment[]> = {
  3: [
    {
      id: '1',
      userName: 'João Santos',
      userAvatar: '/placeholder.svg',
      rating: 5,
      comment: 'Evento incrível! Muito bem organizado e conteúdo de alta qualidade.',
      date: '2024-05-25'
    },
    {
      id: '2',
      userName: 'Maria Oliveira',
      userAvatar: '/placeholder.svg',
      rating: 4,
      comment: 'Muito bom! Única sugestão seria melhorar o coffee break.',
      date: '2024-05-25'
    },
    {
      id: '3',
      userName: 'Carlos Silva',
      userAvatar: '/placeholder.svg',
      rating: 5,
      comment: 'Palestrante excelente, conteúdo muito relevante para o mercado atual.',
      date: '2024-05-25'
    }
  ],
  4: [
    {
      id: '1',
      userName: 'Carlos Mendes',
      userAvatar: '/placeholder.svg',
      rating: 4,
      comment: 'Bom networking, mas poderia ter mais tempo para interação.',
      date: '2024-05-19'
    },
    {
      id: '2',
      userName: 'Ana Silva',
      userAvatar: '/placeholder.svg',
      rating: 5,
      comment: 'Excelente oportunidade para conhecer outros desenvolvedores e trocar experiências.',
      date: '2024-05-19'
    },
    {
      id: '3',
      userName: 'Pedro Santos',
      userAvatar: '/placeholder.svg',
      rating: 5,
      comment: 'Local muito bom e organização impecável. Recomendo!',
      date: '2024-05-19'
    }
  ]
};

// Mock Calendar Events
export const mockCalendarEvents: CalendarEvent[] = [
  {
    id: 1,
    title: 'Workshop React Avançado',
    date: '2024-06-15',
    time: '19:00',
    location: 'Centro de Tecnologia',
    status: 'confirmed',
    type: 'saved'
  },
  {
    id: 2,
    title: 'Festival de Jazz',
    date: '2024-06-20',
    time: '20:00',
    location: 'Parque Ibirapuera',
    status: 'confirmed',
    type: 'confirmed'
  },
  {
    id: 3,
    title: 'Palestra IA',
    date: '2024-06-25',
    time: '14:00',
    location: 'USP',
    status: 'maybe',
    type: 'maybe'
  },
  {
    id: 4,
    title: 'Meetup Desenvolvedores',
    date: '2024-06-18',
    time: '18:30',
    location: 'Coworking Tech',
    status: 'confirmed',
    type: 'confirmed'
  },
  {
    id: 5,
    title: 'Workshop UX Design',
    date: '2024-06-05',
    time: '09:00',
    location: 'Design Studio',
    status: 'confirmed',
    type: 'confirmed'
  },
  {
    id: 6,
    title: 'Feira de Startups',
    date: '2024-06-12',
    time: '10:00',
    location: 'Centro de Convenções',
    status: 'maybe',
    type: 'maybe'
  },
  {
    id: 7,
    title: 'Happy Hour Tech',
    date: '2024-06-19',
    time: '18:00',
    location: 'Pub Central',
    status: 'saved',
    type: 'saved'
  }
];

// Mock Event Posts
export const mockEventPosts: Record<string, EventPost[]> = {
  '1': [
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
  ]
};

// Mock Event Evaluations
export const mockEventEvaluations: Record<string, EventEvaluation[]> = {
  '1': [
    {
      id: '1',
      userId: 'user1',
      userName: 'João Santos',
      userAvatar: '/placeholder.svg',
      rating: 5,
      comment: 'Evento incrível! Muito bem organizado e conteúdo de alta qualidade. Os palestrantes eram experts no assunto e a organização foi impecável.',
      date: '2024-05-21',
      photos: ['/placeholder.svg', '/placeholder.svg'],
      likes: 12,
      replies: [
        {
          id: 'reply1',
          userId: 'organizer1',
          userName: 'Ana Silva',
          comment: 'Muito obrigada pelo feedback! Ficamos felizes que tenha gostado.',
          date: '2024-05-22',
          isOrganizer: true
        }
      ]
    },
    {
      id: '2',
      userId: 'user2',
      userName: 'Maria Oliveira',
      userAvatar: '/placeholder.svg',
      rating: 4,
      comment: 'Muito bom! Única sugestão seria melhorar o coffee break, mas o conteúdo foi excelente.',
      date: '2024-05-20',
      photos: [],
      likes: 8,
      replies: []
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

// Funções para acessar dados de analytics
export const getEventAnalytics = (eventId: number): EventAnalytics | undefined => {
  return mockEventAnalytics[eventId];
};

export const getRatingDistribution = (eventId: number): RatingDistribution[] => {
  return mockRatingDistribution[eventId] || [];
};

export const getDemographicsData = (eventId: number): DemographicsData[] => {
  return mockDemographicsData[eventId] || [];
};

export const getTopComments = (eventId: number): TopComment[] => {
  return mockTopComments[eventId] || [];
};

// Funções para acessar dados de eventos do calendário, posts e avaliações
export const getCalendarEvents = (): CalendarEvent[] => {
  return mockCalendarEvents;
};

export const getEventPosts = (eventId: string): EventPost[] => {
  return mockEventPosts[eventId] || [];
};

export const getEventEvaluations = (eventId: string): EventEvaluation[] => {
  return mockEventEvaluations[eventId] || [];
};
