import React, { useState } from 'react';
import NoteForm from './components/NoteForm';
import NoteList from './components/NoteList';

function App() {
  const [reloadKey, setReloadKey] = useState(0);

  const handleNoteCreated = () => {
    setReloadKey(prev => prev + 1);
  };

  return (
    <div className="App">
      <NoteForm onNoteCreated={handleNoteCreated} />
      <NoteList key={reloadKey} />
    </div>
  );
}

export default App;

