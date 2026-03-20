import React from 'react';

// AI-assisted: floor plan component structure and table status logic generated with Claude AI
function FloorPlan({ tables, reservations, recommendedIds, mergeableTables, filters, onTableClick }) {

  // Checks if a table is occupied at the selected date/time (2 hour visit duration)
  const isTableOccupied = (tableId) => {
    const startTime = new Date(`${filters.date}T${filters.time}:00`);
    const endTime = new Date(startTime.getTime() + 2 * 60 * 60 * 1000);

    return reservations.some(r => {
      if (r.table.id !== tableId) return false;
      const resStart = new Date(r.startTime);
      const resEnd = new Date(r.endTime);
      return resStart < endTime && resEnd > startTime;
    });
  };

  // Returns the time when the table becomes available again
  const getAvailableFrom = (tableId) => {
    const startTime = new Date(`${filters.date}T${filters.time}:00`);
    const endTime = new Date(startTime.getTime() + 2 * 60 * 60 * 1000);

    const occupyingReservation = reservations.find(r => {
      if (r.table.id !== tableId) return false;
      const resStart = new Date(r.startTime);
      const resEnd = new Date(r.endTime);
      return resStart < endTime && resEnd > startTime;
    });

    if (!occupyingReservation) return null;

    const availableTime = new Date(occupyingReservation.endTime);
    return availableTime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  };

  const getMergeGroup = (tableId) => {
    return mergeableTables.findIndex(pair => pair.some(t => t.id === tableId));
  };

  const getTableClass = (table) => {
    if (isTableOccupied(table.id)) return 'table occupied';
    if (recommendedIds[0] === table.id) return 'table best-recommendation';
    if (recommendedIds.includes(table.id)) return 'table recommended';
    if (getMergeGroup(table.id) >= 0) return 'table mergeable';
    return 'table available';
  };

  const zones = ['INDOOR', 'TERRACE', 'PRIVATE'];
  const zoneNames = { INDOOR: '🏠 Indoor', TERRACE: '🌿 Terrace', PRIVATE: '🔒 Private room' };

  return (
    <div className="floor-plan">
      <h2>Floor plan</h2>

      {mergeableTables.length > 0 && (
        <div className="merge-suggestion">
          🔗 Large group detected — we suggest merging the following tables:
          {mergeableTables.slice(0, 3).map((pair, i) => (
            <span key={i}> <strong>{pair[0].name} + {pair[1].name}</strong>{i < Math.min(mergeableTables.length, 3) - 1 ? ',' : ''}</span>
          ))}
        </div>
      )}

      {zones.map(zone => (
        <div key={zone} className="zone">
          <h3>{zoneNames[zone]}</h3>
          <div className="tables-grid">
            {tables
              .filter(t => t.zone === zone)
              .map(table => (
                <div
                  key={table.id}
                  className={getTableClass(table)}
                  onClick={() => !isTableOccupied(table.id) && onTableClick(table)}
                >
                  <div className="table-name">{table.name}</div>
                  <div className="table-capacity">👤 {table.capacity}</div>
                  <div className="table-icons">
                    {table.windowSeat && <span title="Window seat">🪟</span>}
                    {table.quietCorner && <span title="Quiet corner">🤫</span>}
                    {table.nearPlayground && <span title="Near playground">🧒</span>}
                  </div>
                  {isTableOccupied(table.id) && getAvailableFrom(table.id) && (
                    <div className="available-from">🕐 Free at {getAvailableFrom(table.id)}</div>
                  )}
                  {recommendedIds[0] === table.id && (
                    <div className="badge">⭐ Best</div>
                  )}
                  {getMergeGroup(table.id) >= 0 && (
                    <div className="badge merge-badge">🔗 {getMergeGroup(table.id) + 1}</div>
                  )}
                </div>
              ))}
          </div>
        </div>
      ))}
      <div className="legend">
        <span className="legend-item available">Available</span>
        <span className="legend-item occupied">Occupied</span>
        <span className="legend-item recommended">Recommended</span>
        <span className="legend-item best-recommendation">Best match</span>
        <span className="legend-item mergeable">Mergeable</span>
      </div>
    </div>
  );
}

export default FloorPlan;