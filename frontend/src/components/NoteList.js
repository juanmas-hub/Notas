import React, { useEffect, useState } from 'react';
import { getAllNotes } from '../api/noteService';

function NoteList() {
  const [notes, setNotes] = useState([]);

  useEffect(() => {
    getAllNotes()
      .then(res => setNotes(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div className="container mt-5">
      <h2>Notas</h2>
      <div className="list-group">
        {notes.map(note => (
          <div key={note.id} className="list-group-item">
            <h5>{note.title}</h5>
            <p>{note.content}</p>
            {note.tags && note.tags.length > 0 && (
              <div>
                <strong>Tags:</strong>{' '}
                {note.tags.map(tag => (
                  <span key={tag} className="badge bg-secondary me-1">{tag}</span>
                ))}
              </div>
            )}
            <p className="text-muted">
              {note.archived ? 'Archivada' : 'Activa'}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default NoteList;

