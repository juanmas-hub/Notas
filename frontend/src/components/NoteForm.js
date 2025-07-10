import React, { useState } from 'react';
import { createNote } from '../api/noteService';

function NoteForm({ onNoteCreated }) {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [tagsInput, setTagsInput] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();

    const tagNames = tagsInput
      .split(',')
      .map(tag => tag.trim())
      .filter(tag => tag.length > 0);

    createNote({ title, content, tagNames })
      .then(() => {
        setTitle('');
        setContent('');
        setTagsInput('');
        if (onNoteCreated) onNoteCreated();
      })
      .catch(err => console.error(err));
  };

  return (
    <div className="container mt-4">
      <h2>Crear Nota</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <input className="form-control" placeholder="Título" value={title} onChange={e => setTitle(e.target.value)} />
        </div>
        <div className="mb-3">
          <textarea className="form-control" placeholder="Contenido" value={content} onChange={e => setContent(e.target.value)} />
        </div>
        <div className="mb-3">
          <input className="form-control" placeholder="Tags (separados por coma)" value={tagsInput} onChange={e => setTagsInput(e.target.value)} />
        </div>
        <button className="btn btn-primary" type="submit">Crear</button>
      </form>
    </div>
  );
}

export default NoteForm;
