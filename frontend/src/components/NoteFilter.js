import React, { useEffect, useState } from 'react';
import { getAllTags } from '../api/tagService';

function NoteFilter({ onFilterChange }) {
  const [tag, setTag] = useState('');
  const [status, setStatus] = useState('all'); // all, active, archived
  const [tags, setTags] = useState([]);

  useEffect(() => {
    getAllTags()
      .then(res => setTags(res.data))
      .catch(err => console.error(err));
  }, []);

  const handleApply = () => {
    onFilterChange({ tag, status });
  };

  return (
    <div className="container mt-4 mb-4">
      <div className="row g-3 align-items-end">
        <div className="col-md-4">
          <label className="form-label">Estado</label>
          <select className="form-select" value={status} onChange={e => setStatus(e.target.value)}>
            <option value="all">Todas</option>
            <option value="active">Activas</option>
            <option value="archived">Archivadas</option>
          </select>
        </div>

        <div className="col-md-4">
          <label className="form-label">Tag</label>
          <select className="form-select" value={tag} onChange={e => setTag(e.target.value)}>
            <option value="">Todos</option>
            {tags.map(t => (
              <option key={t.id} value={t.name}>{t.name}</option>
            ))}
          </select>
        </div>

        <div className="col-md-4">
          <button className="btn btn-primary" onClick={handleApply}>
            Aplicar Filtro
          </button>
        </div>
      </div>
    </div>
  );
}

export default NoteFilter;
