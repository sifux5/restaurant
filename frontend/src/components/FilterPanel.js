import React from 'react';

function FilterPanel({ filters, setFilters, onRecommend }) {
  return (
    <div className="filter-panel">
      <h2>Otsi lauda</h2>

      <label>Kuupäev</label>
      <input
        type="date"
        value={filters.date}
        onChange={e => setFilters({ ...filters, date: e.target.value })}
      />

      <label>Kellaaeg</label>
      <input
        type="time"
        value={filters.time}
        onChange={e => setFilters({ ...filters, time: e.target.value })}
      />

      <label>Inimeste arv</label>
      <input
        type="number"
        min="1"
        max="20"
        value={filters.partySize}
        onChange={e => setFilters({ ...filters, partySize: parseInt(e.target.value) })}
      />

      <label>Tsoon</label>
      <select
        value={filters.zone}
        onChange={e => setFilters({ ...filters, zone: e.target.value })}
      >
        <option value="">Kõik tsoonid</option>
        <option value="INDOOR">Sisesaal</option>
        <option value="TERRACE">Terrass</option>
        <option value="PRIVATE">Privaatruum</option>
      </select>

      <h3>Eelistused</h3>

      <label>
        <input
          type="checkbox"
          checked={filters.quietCorner}
          onChange={e => setFilters({ ...filters, quietCorner: e.target.checked })}
        />
        Vaikne nurk
      </label>

      <label>
        <input
          type="checkbox"
          checked={filters.windowSeat}
          onChange={e => setFilters({ ...filters, windowSeat: e.target.checked })}
        />
        Akna all
      </label>

      <label>
        <input
          type="checkbox"
          checked={filters.nearPlayground}
          onChange={e => setFilters({ ...filters, nearPlayground: e.target.checked })}
        />
        Laste mängunurga lähedal
      </label>

      <button onClick={onRecommend}>Soovita lauda</button>
    </div>
  );
}

export default FilterPanel;