import React, { useEffect, useState } from 'react';
import {
  getAllNotes,
  getActiveNotes,
  getArchivedNotes,
  getNotesByTag
} from '../api/noteService';
import NoteCard from './NoteCard';
import NoteFilter from './NoteFilter';

function NoteList() {
  const [notes, setNotes] = useState([]);

  const loadNotes = ({ tag = '', status = 'all' } = {}) => {
    let fetchFunction;

    if (tag) {
      fetchFunction = () => getNotesByTag(tag);
    } else {
      switch (status) {
        case 'active':
          fetchFunction = getActiveNotes;
          break;
        case 'archived':
          fetchFunction = getArchivedNotes;
          break;
        default:
          fetchFunction = getAllNotes;
      }
    }

    fetchFunction()
      .then(res => setNotes(res.data))
      .catch(err => console.error(err));
  };

  useEffect(() => {
    loadNotes();
  }, []);

  return (
    <div className="container mt-4">
      <NoteFilter onFilterChange={loadNotes} />
      {notes.length === 0 && <p>There are no notes to show.</p>}
      {notes.map(note => (
        <NoteCard key={note.id} note={note} onAction={() => loadNotes()} />
      ))}
    </div>
  );
}

export default NoteList;
