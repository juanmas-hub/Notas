import React, { useState } from 'react';
import { archiveNote, unarchiveNote, deleteNote } from '../api/noteService';
import NoteEditForm from './NoteEditForm';

function NoteCard({ note, onAction }) {
  const [editing, setEditing] = useState(false);

  const handleArchive = () => {
    const action = note.archived ? unarchiveNote : archiveNote;
    action(note.id).then(() => onAction());
  };

  const handleDelete = () => {
    if (window.confirm('¿Delete note?')) {
      deleteNote(note.id).then(() => onAction());
    }
  };

  if (editing) {
    return (
      <NoteEditForm
        note={note}
        onCancel={() => setEditing(false)}
        onUpdated={() => {
          setEditing(false);
          onAction();
        }}
      />
    );
  }

  return (
    <div className="card mb-3">
      <div className="card-body">
        <h5 className="card-title">{note.title}</h5>
        <p className="card-text">{note.content}</p>

        {note.tags?.length > 0 && (
          <p>
            <strong>Tags:</strong>{' '}
            {note.tags.map(tag => (
              <span key={tag} className="badge bg-secondary me-1">{tag}</span>
            ))}
          </p>
        )}

        <p className="text-muted">
          {note.archived ? '📦 Archived' : '📝 Active'}
        </p>

        <button className="btn btn-sm btn-outline-primary me-2" onClick={() => setEditing(true)}>
          Edit
        </button>
        <button className="btn btn-sm btn-outline-secondary me-2" onClick={handleArchive}>
          {note.archived ? 'Unarchive' : 'Archive'}
        </button>
        <button className="btn btn-sm btn-outline-danger" onClick={handleDelete}>
          Delete
        </button>
      </div>
    </div>
  );
}

export default NoteCard;
