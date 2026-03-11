import React, { useState, useEffect } from 'react';
import axios from 'axios';

function ReservationModal({ table, onConfirm, onClose }) {
  const [customerName, setCustomerName] = useState('');
  const [meal, setMeal] = useState(null);

  useEffect(() => {
    axios.get('https://www.themealdb.com/api/json/v1/1/random.php')
      .then(res => setMeal(res.data.meals[0]))
      .catch(() => setMeal(null));
  }, []);

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2>Broneeri laud {table.name}</h2>
        <p>Mahtuvus: {table.capacity} inimest</p>
        <p>Tsoon: {table.zone}</p>

        {meal && (
          <div className="meal-suggestion">
            <h3>🍽️ Tänane soovitus</h3>
            <img src={meal.strMealThumb} alt={meal.strMeal} />
            <p><strong>{meal.strMeal}</strong></p>
            <p className="meal-category">{meal.strCategory} • {meal.strArea}</p>
          </div>
        )}

        <label>Sinu nimi</label>
        <input
          type="text"
          placeholder="Sisesta nimi..."
          value={customerName}
          onChange={e => setCustomerName(e.target.value)}
        />

        <div className="modal-buttons">
          <button
            onClick={() => customerName && onConfirm(customerName)}
            disabled={!customerName}
          >
            Broneeri
          </button>
          <button onClick={onClose}>Tühista</button>
        </div>
      </div>
    </div>
  );
}

export default ReservationModal;