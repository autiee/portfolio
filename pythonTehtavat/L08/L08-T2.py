######################################################################
# CT60A0202 Ohjelmoinnin ja data-analytiikan perusteet
# Tekijä: Eeli Autio
# Opiskelijanumero: 0600490
# Päivämäärä: 27.7.2020
# Yhteistyö ja lähteet, nimi ja yhteistyön muoto:
# HUOM! KAIKKI KURSSIN TEHTÄVÄT OVAT HENKILÖKOHTAISIA!
######################################################################

import datetime

def paaohjelma():
    valikko()
    return 0

def valikko():
    print("Tämä ohjelma käyttää datetime-kirjastoa tehtävien ratkaisemiseen.")
    while(True):
        print("Mitä haluat tehdä:")
        print("1) Tunnistaa aika-olion komponentit")
        print("2) Laskea iän päivinä")
        print("3) Tulostaa viikonpäivät")
        print("4) Tulostaa kuukaudet")
        print("0) Lopeta")
        valinta = int(input("Valintasi: "))
        if(valinta == 0):
            print("Kiitos ohjelman käytöstä.")
            break
        elif(valinta == 1):
            komponentit()
        elif(valinta == 2):
            ika()
        elif(valinta == 3):
            paivat()
        elif(valinta == 4):
            kuukaudet()
        else:
            print("Tuntematon valinta, yritä uudestaan." + "\n")

def komponentit():
    syote = input("Anna päivämäärä ja kello muodossa 'pp.kk.vvvv hh:mm': ")
    aika = datetime.datetime.strptime(syote, "%d.%m.%Y %H:%M")
    print(aika.strftime("Annoit vuoden %Y"))
    print(aika.strftime("Annoit kuukauden %m"))
    print(aika.strftime("Annoit päivän %d"))
    print(aika.strftime("Annoit tunnin %H"))
    print(aika.strftime("Annoit minuutin %M"))
    print("")


def ika():
    syote = input("Anna syntymäpäiväsi muodossa pp.kk.vvvv: ")
    aika = datetime.datetime.strptime(syote, "%d.%m.%Y")
    ika = datetime.datetime.strptime("01.01.2000", "%d.%m.%Y")-aika
    print("1.1.2000 sinä olit", ika.days ,"päivää vanha.")
    print("")

def paivat():
    paiva = datetime.date(2020, 7, 26)
    for i in range(7):
        paiva += datetime.timedelta(days=1)
        print(datetime.datetime.strftime(paiva,"%A"))
    print("")

def kuukaudet():
    kuukausi = datetime.date(2020, 12, 1)
    for i in range(12):
        kuukausi = kuukausi.replace(day=1)
        kuukausi += datetime.timedelta(days=32)
        kuukausi = kuukausi.replace(day=1)
        print(datetime.datetime.strftime(kuukausi,"%b"))
    print("")

paaohjelma()
