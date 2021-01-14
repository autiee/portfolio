######################################################################
# CT60A0202 Ohjelmoinnin ja data-analytiikan perusteet
# Tekijä: Eeli Autio
# Opiskelijanumero: 0600490
# Päivämäärä: 31.7.2020
# Yhteistyö ja lähteet, nimi ja yhteistyön muoto:
# HUOM! KAIKKI KURSSIN TEHTÄVÄT OVAT HENKILÖKOHTAISIA!
######################################################################

versionumero = 1.0

def kelvinToCelsius(kelvin):
    return kelvin-273.15

def celsiusToKelvin(celsius):
    return celsius+273.15

def celsiusToFahrenheit(celsius):
    return celsius*1.8+32.00

def fahrenheitToCelsius(fahrenheit):
    return (fahrenheit-32.00)/1.8

def fahrenheitToKelvin(fahrenheit):
    return (fahrenheit+459.67)*(5/9)

def kelvinToFahrenheit(kelvin):
    return kelvin*(9/5)-459.67
