import { API_URL } from '../Constants/config.json';

async function fetchGroups() {
  return await fetch(`${API_URL}/groups/`, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    }
  }).then(res => res.json());
}

export { fetchGroups }