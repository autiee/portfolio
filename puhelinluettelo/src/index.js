import React, { useState, useEffect } from 'react'
import ReactDOM from 'react-dom'
import ShowNames from './components/ShowNames'
import Filter from './components/Filter'
import AddPersonForm from './components/AddPersonForm'
import personService from './services/persons'
import './index.css'

const App = () => {
  const [ persons, setPersons] = useState([])
  const [ newName, setNewName ] = useState('')
  const [ newNumber, setNewNumber ] = useState('')
  const [ newFilterString, setNewFilterString ] = useState('')
  const [ errorMessage, setErrorMessage ] = useState(null)

  useEffect(() => {
    personService
      .getAll()
      .then(response => {
        setPersons(response)
      })
  }, [])

  const addPerson = (event) => {
      event.preventDefault()
      if (typeof (persons.find(x => x.name === newName)) != "undefined") {
          window.alert(`${newName} already exists in the phonebook`)
      } else {
          setPersons([...persons, { name: newName, number: newNumber }])
          personService
          .update({ name: newName, number: newNumber })
          .then(updatePersonsList)
          setErrorMessage(`${newName} was added to the phonebook`)
          setTimeout(() => {
          setErrorMessage(null)
        }, 5000)
      }
  }

  const handleFilterChange = (event) => {
      setNewFilterString(event.target.value)
  }

  const handleNameChange = (event) => {
      setNewName(event.target.value)
  }

  const handleNumberChange = (event) => {
      setNewNumber(event.target.value)
  }

  const updatePersonsList = () => {
    personService
      .getAll()
      .then(response => {
        setPersons(response)
      })}

  const Notification = ({ message }) => {
    if (message === null) {
    return null
  }

  return (
    <div className="error">
      {message}
    </div>
  )
}

  return (
    <div>
      <h2>Phonebook</h2>

      <Notification message={errorMessage} />

      <Filter newFilterString={newFilterString}  handleFilterChange={handleFilterChange} />

      <h3>Add a new</h3>

      <AddPersonForm addPerson={addPerson} newName={newName} handleNameChange={handleNameChange} newNumber={newNumber}
      handleNumberChange={handleNumberChange} />

      <h2>Numbers</h2>

      <ShowNames persons={persons} updatePersonsList={updatePersonsList} newFilterString={newFilterString}/>

    </div>
  )
}

ReactDOM.render(<App />, document.getElementById('root'))