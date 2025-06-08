import React from 'react';
import Navbar from '@/components/Navbar';
import UserProfile from '@/components/UserProfile';
import { useAuth } from '@/contexts/AuthContext';

const Profile = () => {
  const { user } = useAuth();

  if (!user) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100">
        <Navbar />
        <div className="max-w-4xl mx-auto p-6">
          <div className="text-center">
            <h2 className="text-xl font-semibold text-gray-900 mb-2">Usuário não autenticado</h2>
            <p className="text-gray-600">Por favor, faça login para ver seu perfil.</p>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100">
      <Navbar />
      <UserProfile userId={user.id} isOwnProfile={true} />
    </div>
  );
};

export default Profile;
