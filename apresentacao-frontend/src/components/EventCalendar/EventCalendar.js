import React, { useState } from 'react';
import { EventCalendar as StyledEventCalendar } from './EventCalendar.styles';

function EventCalendar({ events }) {
  const [currentDate, setCurrentDate] = useState(new Date());

  const getDaysInMonth = (date) => {
    return new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();
  };

  const getFirstDayOfMonth = (date) => {
    return new Date(date.getFullYear(), date.getMonth(), 1).getDay();
  };

  const getMonthName = (date) => {
    return date.toLocaleString('pt-BR', { month: 'long' });
  };

  const getYear = (date) => {
    return date.getFullYear();
  };

  const getEventsForDay = (day) => {
    return events.filter(event => {
      const eventDate = new Date(event.date);
      return eventDate.getDate() === day &&
             eventDate.getMonth() === currentDate.getMonth() &&
             eventDate.getFullYear() === currentDate.getFullYear();
    });
  };

  const renderCalendarDays = () => {
    const daysInMonth = getDaysInMonth(currentDate);
    const firstDayOfMonth = getFirstDayOfMonth(currentDate);
    const days = [];

    // Adiciona dias vazios no início
    for (let i = 0; i < firstDayOfMonth; i++) {
      days.push(<StyledEventCalendar.EmptyDay key={`empty-${i}`} />);
    }

    // Adiciona os dias do mês
    for (let day = 1; day <= daysInMonth; day++) {
      const dayEvents = getEventsForDay(day);
      days.push(
        <StyledEventCalendar.Day key={day} hasEvents={dayEvents.length > 0}>
          <StyledEventCalendar.DayNumber>{day}</StyledEventCalendar.DayNumber>
          {dayEvents.length > 0 && (
            <StyledEventCalendar.EventIndicator>
              {dayEvents.length} {dayEvents.length === 1 ? 'evento' : 'eventos'}
            </StyledEventCalendar.EventIndicator>
          )}
        </StyledEventCalendar.Day>
      );
    }

    return days;
  };

  const previousMonth = () => {
    setCurrentDate(new Date(currentDate.getFullYear(), currentDate.getMonth() - 1));
  };

  const nextMonth = () => {
    setCurrentDate(new Date(currentDate.getFullYear(), currentDate.getMonth() + 1));
  };

  return (
    <StyledEventCalendar>
      <StyledEventCalendar.Header>
        <StyledEventCalendar.ArrowButton onClick={previousMonth}>
          ←
        </StyledEventCalendar.ArrowButton>
        <StyledEventCalendar.Title>
          {getMonthName(currentDate)} {getYear(currentDate)}
        </StyledEventCalendar.Title>
        <StyledEventCalendar.ArrowButton onClick={nextMonth}>
          →
        </StyledEventCalendar.ArrowButton>
      </StyledEventCalendar.Header>

      <StyledEventCalendar.WeekDays>
        <div>Dom</div>
        <div>Seg</div>
        <div>Ter</div>
        <div>Qua</div>
        <div>Qui</div>
        <div>Sex</div>
        <div>Sáb</div>
      </StyledEventCalendar.WeekDays>

      <StyledEventCalendar.Days>
        {renderCalendarDays()}
      </StyledEventCalendar.Days>
    </StyledEventCalendar>
  );
}

export default EventCalendar; 