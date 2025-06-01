
import React from 'react';
import { Card, CardContent } from '@/components/ui/card';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { X } from 'lucide-react';

interface FilterPanelProps {
  filters: {
    genre: string;
    date: string;
    time: string;
    price: string;
  };
  onFiltersChange: (filters: any) => void;
}

const FilterPanel = ({ filters, onFiltersChange }: FilterPanelProps) => {
  const updateFilter = (key: string, value: string) => {
    onFiltersChange({ ...filters, [key]: value });
  };

  const clearFilter = (key: string) => {
    onFiltersChange({ ...filters, [key]: '' });
  };

  const clearAllFilters = () => {
    onFiltersChange({ genre: '', date: '', time: '', price: '' });
  };

  const activeFilters = Object.entries(filters).filter(([_, value]) => value !== '');

  return (
    <Card className="animate-fade-in border-purple-200">
      <CardContent className="p-6">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">Gênero</label>
            <Select value={filters.genre} onValueChange={(value) => updateFilter('genre', value)}>
              <SelectTrigger className="border-purple-200 focus:border-purple-500">
                <SelectValue placeholder="Selecione o gênero" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="Tecnologia">Tecnologia</SelectItem>
                <SelectItem value="Música">Música</SelectItem>
                <SelectItem value="Educação">Educação</SelectItem>
                <SelectItem value="Esporte">Esporte</SelectItem>
                <SelectItem value="Arte">Arte</SelectItem>
                <SelectItem value="Gastronomia">Gastronomia</SelectItem>
                <SelectItem value="Design">Design</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">Data</label>
            <Select value={filters.date} onValueChange={(value) => updateFilter('date', value)}>
              <SelectTrigger className="border-purple-200 focus:border-purple-500">
                <SelectValue placeholder="Quando?" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="today">Hoje</SelectItem>
                <SelectItem value="tomorrow">Amanhã</SelectItem>
                <SelectItem value="weekend">Fim de semana</SelectItem>
                <SelectItem value="next_week">Próxima semana</SelectItem>
                <SelectItem value="next_month">Próximo mês</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">Horário</label>
            <Select value={filters.time} onValueChange={(value) => updateFilter('time', value)}>
              <SelectTrigger className="border-purple-200 focus:border-purple-500">
                <SelectValue placeholder="Que horas?" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="morning">Manhã (6h-12h)</SelectItem>
                <SelectItem value="afternoon">Tarde (12h-18h)</SelectItem>
                <SelectItem value="evening">Noite (18h-24h)</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">Preço</label>
            <Select value={filters.price} onValueChange={(value) => updateFilter('price', value)}>
              <SelectTrigger className="border-purple-200 focus:border-purple-500">
                <SelectValue placeholder="Faixa de preço" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="free">Gratuito</SelectItem>
                <SelectItem value="paid">Pago</SelectItem>
                <SelectItem value="0-50">R$ 0 - R$ 50</SelectItem>
                <SelectItem value="50-100">R$ 50 - R$ 100</SelectItem>
                <SelectItem value="100+">R$ 100+</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </div>

        {activeFilters.length > 0 && (
          <div className="flex items-center gap-2 pt-4 border-t border-purple-200">
            <span className="text-sm text-gray-600">Filtros ativos:</span>
            {activeFilters.map(([key, value]) => (
              <Badge key={key} variant="secondary" className="flex items-center gap-1 bg-purple-100 text-purple-800">
                {value}
                <X 
                  className="w-3 h-3 cursor-pointer hover:text-red-500" 
                  onClick={() => clearFilter(key)}
                />
              </Badge>
            ))}
            <Button 
              variant="ghost" 
              size="sm" 
              onClick={clearAllFilters}
              className="text-red-600 hover:text-red-700 hover:bg-red-50"
            >
              Limpar todos
            </Button>
          </div>
        )}
      </CardContent>
    </Card>
  );
};

export default FilterPanel;
