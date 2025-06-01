
import React from 'react';
import PersonalCalendar from '@/components/PersonalCalendar';
import Navbar from '@/components/Navbar';

const Calendar = () => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100">
      <Navbar />
      <PersonalCalendar />
    </div>
  );
};

export default Calendar;
