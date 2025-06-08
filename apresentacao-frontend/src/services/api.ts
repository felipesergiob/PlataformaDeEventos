import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8081',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Types
interface RegisterUserData {
  nome: string;
  email: string;
  senha: string;
}

interface LoginUserData {
  email: string;
  senha: string;
}

interface UserResponse {
  id: string;
  nome: string;
  email: string;
  senha: string;
}

interface CreateEventData {
  titulo: string;
  descricao: string;
  dataInicio: string;
  dataFim: string;
  local: string;
  genero: string;
  capacidade: number;
  organizadorId: number;
  valor: number;
}

export type EventResponse = {
  id: string | null;
  titulo: string;
  descricao: string;
  dataInicio: string;
  dataFim: string;
  local: string;
  genero: string;
  valor: number;
  imagem: string | null;
  participantes: number;
  dataCriacao: string;
  organizadorId: string;
};

export type FeaturedEventResponse = EventResponse & { posicaoRanking: number };

export type AvaliacaoResponse = {
  id: string;
  eventoId: string;
  usuarioId: string;
  nota: number;
  comentario: string;
  dataCriacao: string;
};

export type DashboardEventResponse = {
  eventoId: number;
  titulo: string;
  descricao: string;
  totalConfirmacoes: number;
  totalAvaliacoes: number;
  mediaNotas: number;
  totalComentarios: number;
  status: 'FUTURO' | 'PASSADO';
};

// User related endpoints
export const userApi = {
  register: async (userData: RegisterUserData): Promise<void> => {
    await api.post('/usuario/register', userData);
  },

  login: async (userData: LoginUserData): Promise<UserResponse> => {
    const response = await api.post<UserResponse>('/usuario/login', userData);
    return response.data;
  },

  getOrganizerEvents: async (organizerId: string): Promise<EventResponse[]> => {
    const response = await api.get<EventResponse[]>(`/evento/organizador/${organizerId}`);
    return response.data;
  },

  getUserReviews: async (userId: string): Promise<AvaliacaoResponse[]> => {
    const response = await api.get<AvaliacaoResponse[]>(`/avaliacao/usuario/${userId}`);
    return response.data;
  },

  getOrganizerDashboard: async (organizerId: string): Promise<DashboardEventResponse[]> => {
    const response = await api.get<DashboardEventResponse[]>(`/evento/organizador/${organizerId}/dashboard`);
    return response.data;
  }
};

export const eventApi = {
  createEvent: async (eventData: CreateEventData): Promise<EventResponse> => {
    const response = await api.post<EventResponse>('/evento', eventData);
    return response.data;
  },
  getAllEvents: async (): Promise<EventResponse[]> => {
    const response = await api.get<EventResponse[]>('/evento');
    return response.data;
  },
  getFeaturedEvents: async (): Promise<FeaturedEventResponse[]> => {
    const response = await api.get<FeaturedEventResponse[]>('/evento/destaques');
    return response.data;
  },
  getEventById: async (id: string | number): Promise<EventResponse> => {
    const response = await api.get<EventResponse>(`/evento/${id}`);
    return response.data;
  },
  getEventEvaluations: async (eventId: string | number): Promise<AvaliacaoResponse[]> => {
    const response = await api.get<AvaliacaoResponse[]>(`/avaliacao/evento/${eventId}`);
    return response.data;
  },
  getEventDashboard: async (eventId: string | number): Promise<DashboardEventResponse> => {
    const response = await api.get(`/evento/${eventId}/dashboard`);
    return response.data;
  },
};

export default api;