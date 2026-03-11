import React from 'react';

function FloorPlan({ tables, reservations, recommendedIds, mergeableTables, filters, onTableClick }) {

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
  const zoneNames = { INDOOR: '🏠 Sisesaal', TERRACE: '🌿 Terrass', PRIVATE: '🔒 Privaatruum' };

  return (
    <div className="floor-plan">
      <h2>Saali plaan</h2>

      {mergeableTables.length > 0 && (
        <div className="merge-suggestion">
          🔗 Seltskond on suur — soovitame liita järgmised lauad:
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
                    {table.windowSeat && <span title="Aknakoht">🪟</span>}
                    {table.quietCorner && <span title="Vaikne nurk">🤫</span>}
                    {table.nearPlayground && <span title="Laste mängunurga lähedal">🧒</span>}
                  </div>
                  {recommendedIds[0] === table.id && (
                    <div className="badge">⭐ Parim</div>
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
        <span className="legend-item available">Vaba</span>
        <span className="legend-item occupied">Hõivatud</span>
        <span className="legend-item recommended">Soovitatud</span>
        <span className="legend-item best-recommendation">Parim soovitus</span>
        <span className="legend-item mergeable">Liitmiseks</span>
      </div>
    </div>
  );
}

export default FloorPlan;