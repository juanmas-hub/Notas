import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/notes';

export const getAllNotes = () => axios.get(`${BASE_URL}/list`);
export const getActiveNotes = () => axios.get(`${BASE_URL}/active`);
export const getArchivedNotes = () => axios.get(`${BASE_URL}/archived`);
export const getNotesByTag = (tagName) => axios.get(`${BASE_URL}/by-tag`, { params: { tagName } });

export const createNote = (noteRequestDto) => axios.post(`${BASE_URL}/create`, noteRequestDto);
export const updateNote = (id, noteRequestDto) => axios.put(`${BASE_URL}/${id}`, noteRequestDto);
export const deleteNote = (id) => axios.delete(`${BASE_URL}/${id}`);

export const archiveNote = (id) => axios.put(`${BASE_URL}/${id}/archive`);
export const unarchiveNote = (id) => axios.put(`${BASE_URL}/${id}/unarchive`);

export const addTagToNote = (id, tagName) => axios.post(`${BASE_URL}/${id}/tags`, null, { params: { tagName } });
export const removeTagFromNote = (id, tagName) => axios.delete(`${BASE_URL}/${id}/tags`, { params: { tagName } });

