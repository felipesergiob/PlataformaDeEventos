import axios, { AxiosError } from 'axios';

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

export interface UserResponse {
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

export type FeaturedEventResponse = EventResponse & { 
  posicaoRanking: number;
  mediaNotas: number;
};

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

export type ParticipationResponse = {
  id: string;
  eventoId: string;
  usuarioId: string;
  status: 'SALVO' | 'CONFIRMADO';
};

export type EventoFiltro = {
  genero?: string;
  dataInicio?: string;
  dataFim?: string;
  precoMinimo?: number;
  precoMaximo?: number;
  gratuito?: boolean;
  periodoHorario?: 'MANHA' | 'TARDE' | 'NOITE';
};

export type CriarAvaliacaoRequest = {
  eventoId: number;
  usuarioId: number;
  nota: number;
  comentario: string;
};

export type AvaliacaoResumo = {
  id: string;
  eventoId: string;
  usuarioId: string;
  nota: number;
  comentario: string;
  dataCriacao: string;
};

export type PublicacaoResponse = {
  id: string;
  titulo: string;
  conteudo: string;
  fotos: string;
  dataCriacao: string;
  usuarioId: string;
  eventoId: string;
};

export type CriarPublicacaoRequest = {
  eventoId: number;
  usuarioId: number;
  titulo: string;
  conteudo: string;
  fotos: string;
};

export type ComentarioResponse = {
  id: string;
  comentario: string;
  comentarioPaiId: string | null;
  dataCriacao: string;
  usuarioId: string;
  eventoId: string;
};

export type CriarComentarioRequest = {
  comentario: string;
  eventoId: number;
  usuarioId: number;
};

export type ResponderComentarioRequest = {
  comentarioPaiId: number;
  comentario: string;
  usuarioId: number;
  eventoId: number;
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

  getUserById: async (userId: string): Promise<UserResponse> => {
    const response = await api.get<UserResponse>(`/usuario/${userId}`);
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
  filtrarEventos: async (filtros: EventoFiltro): Promise<EventResponse[]> => {
    const params = new URLSearchParams();
    if (filtros.genero) params.append('genero', filtros.genero);
    if (filtros.dataInicio) params.append('dataInicio', filtros.dataInicio);
    if (filtros.dataFim) params.append('dataFim', filtros.dataFim);
    if (filtros.precoMinimo) params.append('precoMinimo', filtros.precoMinimo.toString());
    if (filtros.precoMaximo) params.append('precoMaximo', filtros.precoMaximo.toString());
    if (filtros.gratuito !== undefined) params.append('gratuito', filtros.gratuito.toString());
    if (filtros.periodoHorario) params.append('periodoHorario', filtros.periodoHorario);
    
    const response = await api.get<EventResponse[]>(`/evento/filtrar?${params.toString()}`);
    return response.data;
  },
};

export const participationApi = {
  createParticipation: async (data: { eventoId: number; usuarioId: number; status: string }): Promise<void> => {
    await api.post('/participante', data);
  },
  getParticipation: async (eventoId: string | number, usuarioId: string | number): Promise<ParticipationResponse | null> => {
    try {
      const response = await api.get<ParticipationResponse>(`/participante/${eventoId}/${usuarioId}`);
      return response.data;
    } catch (error) {
      // Se for erro 500 ou 404, assume que não há participação
      if (error instanceof AxiosError && (error.response?.status === 500 || error.response?.status === 404)) {
        return null;
      }
      // Para outros erros, propaga o erro
      throw error;
    }
  },
  updateParticipationStatus: async (eventoId: number, usuarioId: number, status: string): Promise<void> => {
    await api.put(`/participante/${eventoId}/${usuarioId}/status`, { status });
  },
  getUserParticipations: async (usuarioId: string | number): Promise<ParticipationResponse[]> => {
    const response = await api.get<ParticipationResponse[]>(`/participante/usuario/${usuarioId}`);
    return response.data;
  },
  getEventParticipations: async (eventoId: string | number): Promise<ParticipationResponse[]> => {
    const response = await api.get<ParticipationResponse[]>(`/participante/evento/${eventoId}`);
    return response.data;
  }
};

export const avaliacaoApi = {
  criarAvaliacao: async (data: CriarAvaliacaoRequest): Promise<void> => {
    await api.post('/avaliacao', data);
  },

  listarAvaliacoesPorEvento: async (eventoId: string | number): Promise<AvaliacaoResumo[]> => {
    const response = await api.get<AvaliacaoResumo[]>(`/avaliacao/evento/${eventoId}`);
    return response.data;
  },

  listarAvaliacoesPorUsuario: async (usuarioId: string | number): Promise<AvaliacaoResumo[]> => {
    const response = await api.get<AvaliacaoResumo[]>(`/avaliacao/usuario/${usuarioId}`);
    return response.data;
  }
};

export const publicacaoApi = {
  criarPublicacao: async (data: CriarPublicacaoRequest): Promise<void> => {
    await api.post('/publicacao', data);
  },

  listarPublicacoesPorEvento: async (eventoId: string | number): Promise<PublicacaoResponse[]> => {
    const response = await api.get<PublicacaoResponse[]>(`/publicacao/evento/${eventoId}`);
    return response.data;
  }
};

export const comentarioApi = {
  criarComentario: async (data: CriarComentarioRequest): Promise<void> => {
    await api.post('/comentario', data);
  },

  responderComentario: async (data: ResponderComentarioRequest): Promise<void> => {
    await api.post('/comentario/responder', data);
  },

  listarComentariosPorEvento: async (eventoId: string | number): Promise<ComentarioResponse[]> => {
    const response = await api.get<ComentarioResponse[]>(`/comentario/evento/${eventoId}`);
    return response.data;
  },

  listarComentariosPorUsuario: async (usuarioId: string | number): Promise<ComentarioResponse[]> => {
    const response = await api.get<ComentarioResponse[]>(`/comentario/usuario/${usuarioId}`);
    return response.data;
  },

  listarRespostas: async (comentarioPaiId: string | number): Promise<ComentarioResponse[]> => {
    const response = await api.get<ComentarioResponse[]>(`/comentario/respostas/${comentarioPaiId}`);
    return response.data;
  }
};

export default api;