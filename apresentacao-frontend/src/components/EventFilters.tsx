
import { useState } from "react";
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Search, Filter, X, Clock, Calendar as CalendarIcon, DollarSign, Tag } from "lucide-react";

interface EventFiltersProps {
  onFilterChange: (filters: FilterState) => void;
}

export interface FilterState {
  search: string;
  category: string;
  priceRange: string;
  date: string;
  time: string;
  location: string;
}

const EventFilters = ({ onFilterChange }: EventFiltersProps) => {
  const [filters, setFilters] = useState<FilterState>({
    search: "",
    category: "",
    priceRange: "",
    date: "",
    time: "",
    location: "",
  });

  const [showFilters, setShowFilters] = useState(false);

  const updateFilter = (key: keyof FilterState, value: string) => {
    const newFilters = { ...filters, [key]: value };
    setFilters(newFilters);
    onFilterChange(newFilters);
  };

  const clearFilters = () => {
    const clearedFilters = {
      search: "",
      category: "",
      priceRange: "",
      date: "",
      time: "",
      location: "",
    };
    setFilters(clearedFilters);
    onFilterChange(clearedFilters);
  };

  const hasActiveFilters = Object.values(filters).some(value => value !== "");

  return (
    <div className="space-y-4">
      {/* Search Bar */}
      <div className="relative">
        <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground w-4 h-4" />
        <Input
          placeholder="Buscar eventos..."
          value={filters.search}
          onChange={(e) => updateFilter("search", e.target.value)}
          className="pl-10 h-12 text-lg border-purple-200 focus:border-purple-500"
        />
      </div>

      {/* Filter Toggle */}
      <div className="flex items-center justify-between">
        <Button
          variant="outline"
          onClick={() => setShowFilters(!showFilters)}
          className="border-purple-200 hover:bg-purple-50"
        >
          <Filter className="w-4 h-4 mr-2" />
          Filtros Avançados
          {hasActiveFilters && (
            <span className="ml-2 bg-purple-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">
              !
            </span>
          )}
        </Button>

        {hasActiveFilters && (
          <Button
            variant="ghost"
            onClick={clearFilters}
            className="text-purple-600 hover:text-purple-700 hover:bg-purple-50"
          >
            <X className="w-4 h-4 mr-1" />
            Limpar filtros
          </Button>
        )}
      </div>

      {/* Filters Panel */}
      {showFilters && (
        <Card className="p-6 border-purple-200 animate-in slide-in-from-top-2 duration-300">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            {/* Gênero Musical */}
            <div className="space-y-2">
              <label className="text-sm font-medium text-gray-700 flex items-center">
                <Tag className="w-4 h-4 mr-1" />
                Gênero Musical
              </label>
              <Select value={filters.category} onValueChange={(value) => updateFilter("category", value)}>
                <SelectTrigger className="border-purple-200 focus:border-purple-500">
                  <SelectValue placeholder="Todos os gêneros" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="">Todos os gêneros</SelectItem>
                  <SelectItem value="rock">Rock</SelectItem>
                  <SelectItem value="pop">Pop</SelectItem>
                  <SelectItem value="eletronica">Eletrônica</SelectItem>
                  <SelectItem value="samba">Samba</SelectItem>
                  <SelectItem value="pagode">Pagode</SelectItem>
                  <SelectItem value="funk">Funk</SelectItem>
                  <SelectItem value="jazz">Jazz</SelectItem>
                  <SelectItem value="mpb">MPB</SelectItem>
                  <SelectItem value="sertanejo">Sertanejo</SelectItem>
                  <SelectItem value="rap">Rap/Hip-Hop</SelectItem>
                  <SelectItem value="reggae">Reggae</SelectItem>
                  <SelectItem value="festa">Festa</SelectItem>
                  <SelectItem value="show">Show</SelectItem>
                </SelectContent>
              </Select>
            </div>

            {/* Data */}
            <div className="space-y-2">
              <label className="text-sm font-medium text-gray-700 flex items-center">
                <CalendarIcon className="w-4 h-4 mr-1" />
                Data
              </label>
              <Select value={filters.date} onValueChange={(value) => updateFilter("date", value)}>
                <SelectTrigger className="border-purple-200 focus:border-purple-500">
                  <SelectValue placeholder="Qualquer data" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="">Qualquer data</SelectItem>
                  <SelectItem value="today">Hoje</SelectItem>
                  <SelectItem value="tomorrow">Amanhã</SelectItem>
                  <SelectItem value="week">Esta semana</SelectItem>
                  <SelectItem value="weekend">Final de semana</SelectItem>
                  <SelectItem value="month">Este mês</SelectItem>
                </SelectContent>
              </Select>
            </div>

            {/* Horário */}
            <div className="space-y-2">
              <label className="text-sm font-medium text-gray-700 flex items-center">
                <Clock className="w-4 h-4 mr-1" />
                Horário
              </label>
              <Select value={filters.time} onValueChange={(value) => updateFilter("time", value)}>
                <SelectTrigger className="border-purple-200 focus:border-purple-500">
                  <SelectValue placeholder="Qualquer horário" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="">Qualquer horário</SelectItem>
                  <SelectItem value="manha">Manhã (06:00 - 12:00)</SelectItem>
                  <SelectItem value="tarde">Tarde (12:00 - 18:00)</SelectItem>
                  <SelectItem value="noite">Noite (18:00 - 00:00)</SelectItem>
                  <SelectItem value="madrugada">Madrugada (00:00 - 06:00)</SelectItem>
                </SelectContent>
              </Select>
            </div>

            {/* Preço */}
            <div className="space-y-2">
              <label className="text-sm font-medium text-gray-700 flex items-center">
                <DollarSign className="w-4 h-4 mr-1" />
                Preço
              </label>
              <Select value={filters.priceRange} onValueChange={(value) => updateFilter("priceRange", value)}>
                <SelectTrigger className="border-purple-200 focus:border-purple-500">
                  <SelectValue placeholder="Qualquer preço" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="">Qualquer preço</SelectItem>
                  <SelectItem value="free">Gratuito</SelectItem>
                  <SelectItem value="0-50">R$ 0 - R$ 50</SelectItem>
                  <SelectItem value="50-100">R$ 50 - R$ 100</SelectItem>
                  <SelectItem value="100-200">R$ 100 - R$ 200</SelectItem>
                  <SelectItem value="200+">R$ 200+</SelectItem>
                </SelectContent>
              </Select>
            </div>
          </div>

          {/* Local - Full width */}
          <div className="mt-6 space-y-2">
            <label className="text-sm font-medium text-gray-700">Local</label>
            <Input
              placeholder="Cidade ou região..."
              value={filters.location}
              onChange={(e) => updateFilter("location", e.target.value)}
              className="border-purple-200 focus:border-purple-500"
            />
          </div>
        </Card>
      )}
    </div>
  );
};

export default EventFilters;
