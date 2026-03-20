import React from 'react';

function FilterPanel({ filters, setFilters, onRecommend }) {
  return (
    <div className="filter-panel">
      <h2>Find a table</h2>

      <label>Date</label>
      <input
        type="date"
        value={filters.date}
        onChange={e => setFilters({ ...filters, date: e.target.value })}
      />

      <label>Time</label>
      <input
        type="time"
        value={filters.time}
        onChange={e => setFilters({ ...filters, time: e.target.value })}
      />

      <label>Number of guests</label>
      <input
        type="number"
        min="1"
        max="20"
        value={filters.partySize}
        onChange={e => setFilters({ ...filters, partySize: parseInt(e.target.value) })}
      />

      <label>Zone</label>
      <select
        value={filters.zone}
        onChange={e => setFilters({ ...filters, zone: e.target.value })}
      >
        <option value="">All zones</option>
        <option value="INDOOR">Indoor</option>
        <option value="TERRACE">Terrace</option>
        <option value="PRIVATE">Private room</option>
      </select>

      <h3>Preferences</h3>

      <label>
        <input
          type="checkbox"
          checked={filters.quietCorner}
          onChange={e => setFilters({ ...filters, quietCorner: e.target.checked })}
        />
        Quiet corner
      </label>

      <label>
        <input
          type="checkbox"
          checked={filters.windowSeat}
          onChange={e => setFilters({ ...filters, windowSeat: e.target.checked })}
        />
        Window seat
      </label>

      <label>
        <input
          type="checkbox"
          checked={filters.nearPlayground}
          onChange={e => setFilters({ ...filters, nearPlayground: e.target.checked })}
        />
        Near playground
      </label>

      <button onClick={onRecommend}>Recommend a table</button>
    </div>
  );
}

export default FilterPanel;