import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/notes';

export const getAllNotes = () => axios.get(`${BASE_URL}/list`);
export const createNote = (noteRequestDto) =>
  axios.post(`${BASE_URL}/create`, noteRequestDto);

