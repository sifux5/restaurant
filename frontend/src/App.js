import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';
import FilterPanel from './components/FilterPanel';
import FloorPlan from './components/FloorPlan';
import ReservationModal from './components/ReservationModal';

const API = 'http://localhost:8080/api';

function App() {
  const [tables, setTables] = useState([]);
  const [reservations, setReservations] = useState([]);
  const [recommendedIds, setRecommendedIds] = useState([]);
  const [mergeableTables, setMergeableTables] = useState([]);
  const [selectedTable, setSelectedTable] = useState(null);
  const [filters, setFilters] = useState({
    date: new Date().toISOString().split('T')[0],
    time: '12:00',
    partySize: 2,
    zone: '',
    windowSeat: false,
    quietCorner: false,
    accessible: false,
    nearPlayground: false,
  });

  useEffect(() => {
    fetchTables();
    fetchReservations();
  }, []);

  const fetchTables = async () => {
    const res = await axios.get(`${API}/tables`);
    setTables(res.data);
  };

  const fetchReservations = async () => {
    const res = await axios.get(`${API}/reservations`);
    setReservations(res.data);
  };

  const handleRecommend = async () => {
    const startTime = `${filters.date}T${filters.time}:00`;
    const endTime = `${filters.date}T${filters.time}:00`;
    try {
      const res = await axios.get(`${API}/tables/recommend`, {
        params: {
          partySize: filters.partySize,
          startTime,
          endTime,
          zone: filters.zone || null,
          windowSeat: filters.windowSeat,
          quietCorner: filters.quietCorner,
          accessible: filters.accessible,
          nearPlayground: filters.nearPlayground,
        }
      });
      setRecommendedIds(res.data.map(t => t.id));

      // If no recommendations found, search for mergeable tables
      if (res.data.length === 0) {
        const mergeRes = await axios.get(`${API}/tables/merge`, {
          params: { partySize: filters.partySize, startTime, endTime }
        });
        setMergeableTables(mergeRes.data);
      } else {
        setMergeableTables([]);
      }
    } catch (err) {
      alert('Error loading recommendations!');
    }
  };

  const handleReservation = async (customerName) => {
    const startTime = `${filters.date}T${filters.time}:00`;
    try {
      await axios.post(`${API}/reservations`, null, {
        params: {
          tableId: selectedTable.id,
          customerName,
          partySize: filters.partySize,
          startTime,
        }
      });
      alert('Reservation created!');
      setSelectedTable(null);
      setRecommendedIds([]);
      setMergeableTables([]);
      fetchTables();
      fetchReservations();
    } catch (err) {
      alert(err.response?.data || 'Error creating reservation!');
    }
  };

  return (
    <div className="app">
      <h1>🍽️ Restaurant Reservation System</h1>
      <div className="main">
        <FilterPanel
          filters={filters}
          setFilters={setFilters}
          onRecommend={handleRecommend}
        />
        <FloorPlan
          tables={tables}
          reservations={reservations}
          recommendedIds={recommendedIds}
          mergeableTables={mergeableTables}
          filters={filters}
          onTableClick={setSelectedTable}
        />
      </div>
      {selectedTable && (
        <ReservationModal
          table={selectedTable}
          onConfirm={handleReservation}
          onClose={() => setSelectedTable(null)}
        />
      )}
    </div>
  );
}

export default App;