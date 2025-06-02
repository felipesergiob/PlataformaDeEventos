import React, { createContext, useContext, useState, useEffect } from 'react';
import { User } from '@/data/mockData';

interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  login: (email: string, password: string) => Promise<void>;
  register: (name: string, email: string, password: string) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  // Simular verificação de autenticação ao carregar
  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
      setIsAuthenticated(true);
    }
  }, []);

  const login = async (email: string, password: string) => {
    try {
      // TODO: Implementar chamada real à API
      // Por enquanto, vamos usar dados mockados
      const mockUser: User = {
        id: 'current-user',
        name: 'Maria Silva',
        bio: 'Organizadora de eventos de tecnologia e educação.',
        avatar: '/placeholder.svg',
        joinedDate: new Date().toISOString(),
        location: 'São Paulo, SP',
        followers: 245,
        following: 89,
        eventsOrganized: 12,
        eventsAttended: 34,
        averageRating: 4.8
      };

      // Simular delay de rede
      await new Promise(resolve => setTimeout(resolve, 1000));

      setUser(mockUser);
      setIsAuthenticated(true);
      localStorage.setItem('user', JSON.stringify(mockUser));
    } catch (error) {
      console.error('Erro no login:', error);
      throw new Error('Falha no login');
    }
  };

  const register = async (name: string, email: string, password: string) => {
    try {
      // TODO: Implementar chamada real à API
      // Por enquanto, vamos usar dados mockados
      const mockUser: User = {
        id: 'new-user',
        name,
        bio: '',
        avatar: '/placeholder.svg',
        joinedDate: new Date().toISOString(),
        location: '',
        followers: 0,
        following: 0,
        eventsOrganized: 0,
        eventsAttended: 0,
        averageRating: 0
      };

      // Simular delay de rede
      await new Promise(resolve => setTimeout(resolve, 1000));

      setUser(mockUser);
      setIsAuthenticated(true);
      localStorage.setItem('user', JSON.stringify(mockUser));
    } catch (error) {
      console.error('Erro no registro:', error);
      throw new Error('Falha no registro');
    }
  };

  const logout = () => {
    setUser(null);
    setIsAuthenticated(false);
    localStorage.removeItem('user');
  };

  return (
    <AuthContext.Provider value={{ user, isAuthenticated, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth deve ser usado dentro de um AuthProvider');
  }
  return context;
}; 