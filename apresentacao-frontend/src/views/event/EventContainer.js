import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import Event from './Event';
import { mockEvents } from '../home/HomeContainer';
import { Loading } from '../../components';

function EventContainer() {
  const { id } = useParams();
  const [event, setEvent] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchEvent = async () => {
      try {
        setLoading(true);
        // Simulando uma chamada de API com delay
        await new Promise(resolve => setTimeout(resolve, 1000));
        const foundEvent = mockEvents.find(e => e.id === parseInt(id));
        
        if (!foundEvent) {
          throw new Error('Evento não encontrado');
        }
        
        setEvent(foundEvent);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchEvent();
  }, [id]);

  if (loading) {
    return <Loading />;
  }

  if (error) {
    return <div>Erro: {error}</div>;
  }

  return <Event event={event} />;
}

export default EventContainer; 