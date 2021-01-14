import axios from 'axios'
const baseUrl = '/api/persons'

const getAll = () => {
    const request = axios.get(baseUrl)
    return request.then(response => response.data)
  }
  
  const update = newPerson => {
    const request = axios.post(baseUrl, newPerson)
    return request.then(response => response.data)
  }
  
  const remove = ( id , name, updatePersonsList) => {
    if (window.confirm(`Delete ${name}?`)) {
    const request = axios.delete(`${baseUrl}/${id}`)
    return request.then(response => response.data).then(updatePersonsList)
  }}
  
  export default { getAll , update , remove }