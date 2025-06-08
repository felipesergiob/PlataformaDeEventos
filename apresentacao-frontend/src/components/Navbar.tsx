import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Calendar, Plus, User, Menu, LogOut } from 'lucide-react';
import { Link, useLocation } from 'react-router-dom';
import { cn } from '@/lib/utils';
import { AuthModal } from './AuthModal';
import { useAuth } from '@/contexts/AuthContext';

const Navbar = () => {
  const location = useLocation();
  const [isAuthModalOpen, setIsAuthModalOpen] = useState(false);
  const { isAuthenticated, user, logout } = useAuth();

  const navItems = [
    { href: '/', label: 'Explorar', icon: Calendar },
    { href: '/calendar', label: 'Calendário', icon: Calendar },
    { href: '/my-events', label: 'Meus Eventos', icon: User },
    { href: '/profile', label: 'Perfil', icon: User },
  ];

  return (
    <>
      <header className="bg-white/90 backdrop-blur-sm border-b border-purple-200 sticky top-0 z-50 shadow-sm">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <Link to="/" className="flex items-center space-x-2">
              <div className="w-8 h-8 bg-gradient-to-br from-purple-600 to-purple-800 rounded-lg flex items-center justify-center">
                <Calendar className="w-5 h-5 text-white" />
              </div>
              <h1 className="text-xl font-bold bg-gradient-to-r from-purple-600 to-purple-800 bg-clip-text text-transparent">
                SeLigaAi
              </h1>
            </Link>
            
            <nav className="hidden md:flex items-center space-x-6">
              {navItems.map((item) => (
                <Link
                  key={item.href}
                  to={item.href}
                  className={cn(
                    "text-gray-700 hover:text-purple-600 transition-colors font-medium",
                    location.pathname === item.href && "text-purple-600"
                  )}
                >
                  {item.label}
                </Link>
              ))}
            </nav>

            <div className="flex items-center space-x-3">
              {isAuthenticated ? (
                <div className="flex items-center space-x-3">
                  <Link to="/profile">
                    <Button variant="outline" size="sm" className="border-purple-200 text-purple-600 hover:bg-purple-50">
                      <User className="w-4 h-4 mr-2" />
                      {user?.nome.split(' ')[0]}
                    </Button>
                  </Link>
                  <Button 
                    variant="ghost" 
                    size="sm" 
                    className="text-gray-600 hover:text-purple-600"
                    onClick={logout}
                  >
                    <LogOut className="w-4 h-4" />
                  </Button>
                </div>
              ) : (
                <Button 
                  variant="outline" 
                  size="sm" 
                  className="border-purple-200 text-purple-600 hover:bg-purple-50"
                  onClick={() => setIsAuthModalOpen(true)}
                >
                  Login
                </Button>
              )}
              <Link to="/create-event">
                <Button size="sm" className="bg-gradient-to-r from-purple-600 to-purple-800 hover:from-purple-700 hover:to-purple-900 text-white">
                  <Plus className="w-4 h-4 mr-2" />
                  Criar Evento
                </Button>
              </Link>
            </div>
          </div>
        </div>
      </header>

      <AuthModal 
        isOpen={isAuthModalOpen} 
        onClose={() => setIsAuthModalOpen(false)} 
      />
    </>
  );
};

export default Navbar;
