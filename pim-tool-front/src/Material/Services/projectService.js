import { API_URL } from '../Constants/config.json';

async function createProject(data) {
  return await fetch(`${API_URL}/projects/new/`, {
    method: 'POST',
    body: JSON.stringify(data),
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    }
  }).then(res => res.json());
}

async function getProjects(data) {
  console.log(data);
  const { currentPage, pageSize, status, searchValue } = data;
  return await fetch(`${API_URL}/projects/?page=${currentPage}&pageSize=${pageSize}&status=${status}&searchValue=${searchValue}`, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    }
  }).then(res => res.json());
}

async function updateProject(data) {
  return await fetch(`${API_URL}/projects/update/`, {
    method: 'PUT',
    body: JSON.stringify(data),
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    }
  }).then(res => res.json());
}

async function deleteProject(id) {
  return await fetch(`${API_URL}/projects/delete/`, {
    method: 'DELETE',
    body: JSON.stringify(id),
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    }
  }).then(res => res.json());
}

export { createProject, getProjects, updateProject, deleteProject }