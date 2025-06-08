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

interface EventResponse {
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
}

// User related endpoints
export const userApi = {
  register: async (userData: RegisterUserData): Promise<void> => {
    await api.post('/usuario/register', userData);
  },

  login: async (userData: LoginUserData): Promise<UserResponse> => {
    const response = await api.post<UserResponse>('/usuario/login', userData);
    return response.data;
  },
};

export const eventApi = {
  createEvent: async (eventData: CreateEventData): Promise<EventResponse> => {
    const response = await api.post<EventResponse>('/evento', eventData);
    return response.data;
  },
};

export default api;