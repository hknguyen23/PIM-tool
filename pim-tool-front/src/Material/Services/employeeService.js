import { API_URL } from '../Constants/config.json';

async function fetchEmployees() {
  return await fetch(`${API_URL}/employees/`, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    }
  }).then(res => res.json());
}

export { fetchEmployees }