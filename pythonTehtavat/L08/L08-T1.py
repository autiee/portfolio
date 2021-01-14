######################################################################
# CT60A0202 Ohjelmoinnin ja data-analytiikan perusteet
# Tekijä: Eeli Autio
# Opiskelijanumero: 0600490
# Päivämäärä: 22.7.2020
# Yhteistyö ja lähteet, nimi ja yhteistyön muoto:
# HUOM! KAIKKI KURSSIN TEHTÄVÄT OVAT HENKILÖKOHTAISIA!
######################################################################

import math
import random

def paaohjelma():
    valikko()

def valikko():
    print("Tämä ohjelma käyttää kirjastoja tehtävien ratkaisemiseen.")
    while(True):
        print("Mitä haluat tehdä:")
        print("1) Laskea ympyrän pinta-alan")
        print("2) Arvata luvun")
        print("0) Lopeta")
        valinta = int(input("Valintasi: "))
        if(valinta == 0):
            print("Kiitos ohjelman käytöstä.")
            break
        elif(valinta == 1):
            pintaAla()
        elif(valinta == 2):
            arpa()
        else:
            print("Tuntematon valinta, yritä uudestaan." + "\n")


def pintaAla():
    sade = int(input("Anna ympyrän säde kokonaislukuna: "))
    print("Säteellä",sade,"ympyrän pinta-ala on",str(round(math.pi*math.pow(sade,2),2))+".")
    print("")

def arpa():
    print("Arvaa ohjelman arpoma kokonaisluku.")
    random.seed(37)
    luku = random.randint(0,1000)
    kierros = 0
    while(True):
        kierros = kierros + 1
        arvaus = int(input("Anna kokonaisluku välillä 0-1000: "))
        if(arvaus == luku):
            print("Oikein! Käytit arvaamiseen",kierros,"kierrosta.")
            print("")
            break
        if(arvaus > luku):
            print("Haettu luku on pienempi.")
        if(arvaus < luku):
            print("Haettu luku on suurempi.")

paaohjelma()
