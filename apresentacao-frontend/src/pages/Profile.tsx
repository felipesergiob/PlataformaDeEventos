
import React from 'react';
import Navbar from '@/components/Navbar';
import UserProfile from '@/components/UserProfile';

const Profile = () => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100">
      <Navbar />
      <UserProfile userId="current-user" isOwnProfile={true} />
    </div>
  );
};

export default Profile;
