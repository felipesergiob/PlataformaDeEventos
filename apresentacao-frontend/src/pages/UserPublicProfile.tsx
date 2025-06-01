
import React from 'react';
import Navbar from '@/components/Navbar';
import UserProfile from '@/components/UserProfile';
import { useParams } from 'react-router-dom';

const UserPublicProfile = () => {
  const { userId } = useParams<{ userId: string }>();

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100">
      <Navbar />
      <UserProfile userId={userId || "1"} isOwnProfile={false} />
    </div>
  );
};

export default UserPublicProfile;
