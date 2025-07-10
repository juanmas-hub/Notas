import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/tags';

export const getAllTags = () => axios.get(BASE_URL);
export const createTag = (tagName) => axios.post(`${BASE_URL}/create`, { name: tagName });
export const deleteTag = (id) => axios.delete(`${BASE_URL}/${id}`);
export const updateTag = (id, name) => axios.put(`${BASE_URL}/${id}`, { name });
