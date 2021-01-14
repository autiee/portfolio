import React from 'react'
import personService from '../services/persons'

  const ShowNames = ({ persons, updatePersonsList, newFilterString }) => persons.map((entry, i) => {
      if (entry.name.toLowerCase().includes(newFilterString.toLowerCase())) {
          return (
          <p key={i}>{entry.name} {entry.number} <button onClick={() => personService.remove(entry.id, entry.name, updatePersonsList)}>Delete</button></p>
      )} else {
          return ("")
          }
        }
   )

export default ShowNames