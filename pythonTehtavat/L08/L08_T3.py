######################################################################
# CT60A0202 Ohjelmoinnin ja data-analytiikan perusteet
# Tekijä: Eeli Autio
# Opiskelijanumero: 0600490
# Päivämäärä: 29.7.2020
# Yhteistyö ja lähteet, nimi ja yhteistyön muoto:
# HUOM! KAIKKI KURSSIN TEHTÄVÄT OVAT HENKILÖKOHTAISIA!
######################################################################

import L08_T3Kirjasto

def paaohjelma():
    valikko()
    return 0

def valikko():
    print("Käytetään lämpötilamuunnoskirjaston versiota", L08_T3Kirjasto.versionumero)
    while(True):
        print("Minkä lämpötilamuunnoksen haluat tehdä?")
        print("1) Celsius->Fahrenheit")
        print("2) Celsius->Kelvin")
        print("3) Fahrenheit->Kelvin")
        print("4) Fahrenheit->Celsius")
        print("5) Kelvin->Celsius")
        print("6) Kelvin->Fahrenheit")
        print("0) Lopeta")
        valinta = int(input("Valintasi: "))
        if(valinta == 0):
            print("Kiitos ohjelman käytöstä.")
            break
        elif(valinta == 1):
            lampotila = float(input("Anna lähtölämpötila: "))
            print("Lämpötila Fahrenheit asteina:",str(round(L08_T3Kirjasto.celsiusToFahrenheit(lampotila),2))+ "\n")
        elif(valinta == 2):
            lampotila = float(input("Anna lähtölämpötila: "))
            print("Lämpötila Kelvin asteina:",str(round(L08_T3Kirjasto.celsiusToKelvin(lampotila),2))+ "\n")
        elif(valinta == 3):
            lampotila = float(input("Anna lähtölämpötila: "))
            print("Lämpötila Kelvin asteina:",str(round(L08_T3Kirjasto.fahrenheitToKelvin(lampotila),2))+ "\n")
        elif(valinta == 4):
            lampotila = float(input("Anna lähtölämpötila: "))
            print("Lämpötila Celsius asteina:",str(round(L08_T3Kirjasto.fahrenheitToCelsius(lampotila),2))+ "\n")
        elif(valinta == 5):
            lampotila = float(input("Anna lähtölämpötila: "))
            print("Lämpötila Celsius asteina:",str(round(L08_T3Kirjasto.kelvinToCelsius(lampotila),2))+ "\n")
        elif(valinta == 6):
            lampotila = float(input("Anna lähtölämpötila: "))
            print("Lämpötila Fahrenheit asteina:",str(round(L08_T3Kirjasto.kelvinToFahrenheit(lampotila),2))+ "\n")
        else:
            print("Tuntematon valinta, yritä uudestaan." + "\n")

paaohjelma()
