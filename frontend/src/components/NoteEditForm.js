import React, { useState, useEffect } from 'react';
import { updateNote } from '../api/noteService';

function NoteEditForm({ note, onCancel, onUpdated }) {
  const [title, setTitle] = useState(note.title);
  const [content, setContent] = useState(note.content);
  const [tagsInput, setTagsInput] = useState(note.tags.join(', '));

  useEffect(() => {
    setTitle(note.title);
    setContent(note.content);
    setTagsInput(note.tags.join(', '));
  }, [note]);

  const handleSubmit = (e) => {
    e.preventDefault();

    const tagNames = tagsInput
      .split(',')
      .map(tag => tag.trim())
      .filter(tag => tag.length > 0);

    updateNote(note.id, { title, content, tagNames })
      .then(() => {
        onUpdated(); // para recargar lista
      })
      .catch(err => console.error(err));
  };

  return (
    <div className="card mt-3">
      <div className="card-body">
        <h5 className="card-title">Edit note</h5>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <input className="form-control" value={title} onChange={e => setTitle(e.target.value)} />
          </div>
          <div className="mb-3">
            <textarea className="form-control" value={content} onChange={e => setContent(e.target.value)} />
          </div>
          <div className="mb-3">
            <input className="form-control" placeholder="Tags (separated by comma)" value={tagsInput} onChange={e => setTagsInput(e.target.value)} />
          </div>
          <button className="btn btn-success me-2" type="submit">Save changes</button>
          <button className="btn btn-secondary" type="button" onClick={onCancel}>Cancel</button>
        </form>
      </div>
    </div>
  );
}

export default NoteEditForm;
