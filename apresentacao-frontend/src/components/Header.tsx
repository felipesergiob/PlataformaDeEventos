
import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Plus, User, Calendar, BarChart3, Home, LogIn } from "lucide-react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";

interface HeaderProps {
  onCreateEvent: () => void;
  onNavigate: (page: string) => void;
  currentPage: string;
}

const Header = ({ onCreateEvent, onNavigate, currentPage }: HeaderProps) => {
  const [showLoginModal, setShowLoginModal] = useState(false);
  const [loginData, setLoginData] = useState({
    email: "",
    password: "",
  });

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    // Simulate login
    setShowLoginModal(false);
    console.log("Login attempt:", loginData);
  };

  const navItems = [
    { id: "home", label: "Início", icon: Home },
    { id: "my-events", label: "Meus Eventos", icon: Calendar },
    { id: "reports", label: "Relatórios", icon: BarChart3 },
    { id: "profile", label: "Perfil", icon: User },
  ];

  return (
    <header className="glass-effect border-b border-purple-200 sticky top-0 z-50">
      <div className="container mx-auto px-4 py-4">
        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-8">
            <h1 
              className="text-2xl font-bold text-gradient cursor-pointer"
              onClick={() => onNavigate("home")}
            >
              Se Liga AI
            </h1>
            
            <nav className="hidden md:flex items-center space-x-6">
              {navItems.map((item) => {
                const Icon = item.icon;
                return (
                  <button
                    key={item.id}
                    onClick={() => onNavigate(item.id)}
                    className={`flex items-center space-x-2 px-3 py-2 rounded-lg transition-colors ${
                      currentPage === item.id
                        ? "bg-purple-100 text-purple-700"
                        : "text-gray-600 hover:text-purple-600 hover:bg-purple-50"
                    }`}
                  >
                    <Icon className="w-4 h-4" />
                    <span className="font-medium">{item.label}</span>
                  </button>
                );
              })}
            </nav>
          </div>

          <div className="flex items-center space-x-4">
            <Button
              onClick={onCreateEvent}
              className="btn-purple hidden sm:flex"
            >
              <Plus className="w-4 h-4 mr-2" />
              Criar Evento
            </Button>

            <Dialog open={showLoginModal} onOpenChange={setShowLoginModal}>
              <DialogTrigger asChild>
                <Button variant="outline" className="border-purple-200 hover:bg-purple-50">
                  <LogIn className="w-4 h-4 mr-2" />
                  Entrar
                </Button>
              </DialogTrigger>
              <DialogContent className="sm:max-w-md">
                <DialogHeader>
                  <DialogTitle className="text-gradient">Fazer Login</DialogTitle>
                  <DialogDescription>
                    Entre na sua conta para gerenciar seus eventos
                  </DialogDescription>
                </DialogHeader>
                <form onSubmit={handleLogin} className="space-y-4">
                  <div className="space-y-2">
                    <Label htmlFor="email">Email</Label>
                    <Input
                      id="email"
                      type="email"
                      value={loginData.email}
                      onChange={(e) => setLoginData(prev => ({ ...prev, email: e.target.value }))}
                      className="border-purple-200 focus:border-purple-500"
                      required
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="password">Senha</Label>
                    <Input
                      id="password"
                      type="password"
                      value={loginData.password}
                      onChange={(e) => setLoginData(prev => ({ ...prev, password: e.target.value }))}
                      className="border-purple-200 focus:border-purple-500"
                      required
                    />
                  </div>
                  <Button type="submit" className="w-full btn-purple">
                    Entrar
                  </Button>
                </form>
              </DialogContent>
            </Dialog>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;
