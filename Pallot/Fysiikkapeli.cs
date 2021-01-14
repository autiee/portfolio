using System;
using System.Collections.Generic;
using Jypeli;
using Jypeli.Assets;
using Jypeli.Controls;
using Jypeli.Widgets;

public class Pallot : PhysicsGame
{
    public override void Begin()
    {
        Level.Size = new Vector(1024, 768); // Kentän koko
        Level.BackgroundColor = Color.DarkBlue; // Kentän taustaväri
        SetWindowSize(1024, 768); // Ikkunan koko

        GameObject liikuteltava = new GameObject(50, 50, Shape.Circle); // Liikuteltava pallo
        liikuteltava.Position = RandomGen.NextVector(Level.BoundingRect);
        Add(liikuteltava);

        List<GameObject> oliot = new List<GameObject>();
        for (int i = 0; i < 300; i++)
        {
            PhysicsObject p = new PhysicsObject(10, 10, Shape.Circle);
            oliot.Add(p);
            p.Position = RandomGen.NextVector(Level.BoundingRect);
            Add(p);
        }
        // TODO: Luo tässä 300 palloa (koko 10x10) satunnaisiin paikkoihin
        // tasaisesti pelialueen sisälle. Lisää luomasi pallot oliot-listaan.

        VarjaaPallot(liikuteltava, oliot);

        Keyboard.Listen(Key.Escape, ButtonState.Pressed, ConfirmExit, "Lopeta peli");

        // Tästä alkaa fancy-koodi, joka liikuttaa palloja sekä lisää hiirelle
        // kuuntelijan ja tapahtumankäsittelijän. Toimii vain jos teet Visual Studiossa.
        // Tällä ei ole vaikutusta, jos teet ohjelmaa Timissä.
        Timer ajastin = new Timer();
        ajastin.Interval = 0.1;
        ajastin.Timeout += delegate
        {
            foreach (GameObject olio in oliot)
            {
                Vector uusiPaikka = olio.Position + RandomGen.NextVector(-20, 20);
                olio.MoveTo(uusiPaikka, 50);
            }
        };
        ajastin.Start();

        Mouse.ListenMovement(0.1, Liikuta, null, liikuteltava);
        Mouse.ListenMovement(0.1, VarjaaPallot, null, liikuteltava, oliot);
        // Tähän päättyy fancy-koodi.
    }

    public void Liikuta(GameObject liikuteltava)
    {
        liikuteltava.Position = Mouse.PositionOnWorld;
    }

    /// <summary>
    /// Aliohjelma värjää liikuteltava-listan oliot.
    /// Ne oliot joiden etäisyys liikuteltava-oliosta on enemmän
    /// kuin 255 värjätään mustaksi. Ne oliot joiden etäisyys on
    /// alle 255 värjätään siten että jokaisen värikanavan (red, green, blue)
    /// arvo on 255-etäisyys, esimerkiksi jos etäisyys on 10 niin värin arvo on
    /// (245, 245, 245). Arvo 0 tarkoittaa täysin tummaa ja 255
    /// vaaleaa. Toisin sanoen ne joiden etäisyys liikuteltavasta on 0,
    /// ovat täysin valkoisia.
    /// </summary>
    /// <param name="liikuteltava">Liikuteltava olio.</param>
    /// <param name="oliot">Pikkupallot.</param>
    public void VarjaaPallot(GameObject liikuteltava, List<GameObject> oliot)
    {
        int x = 0;
        int y = 0;
        for(int i = 0; i < oliot.Count; i++)
        {
            x = Convert.ToInt32(liikuteltava.X) - Convert.ToInt32(oliot[i].X);
            y = Convert.ToInt32(liikuteltava.Y) - Convert.ToInt32(oliot[i].Y);
            int etaisyys = Convert.ToInt32(Math.Sqrt((float)(x * x + y * y)));
            if (etaisyys > 255) etaisyys = 255;
            Color c = new Color(255 - etaisyys, 255 - etaisyys, 255 - etaisyys);
            oliot[i].Color = c;
        }
        
        // TODO: Toteuta aliohjelma

        // Vinkki 1: Käytä uuden värin luomiseen Color-luokkaa:
        //   Color c = new Color(...);
        // ja laita pisteiden paikalle kolme int-tyyppistä kokonaislukua väliltä 0-255.

        // Vinkki 2: Voit muuttaa double-luvun int-luvuksi esimerkiksi seuraavasti:
        //   double d = 5.0;
        //   int kokonaisluku = Convert.ToInt32(d);
        // (ks. https://docs.microsoft.com/en-us/dotnet/api/system.convert.toint32?view=netframework-4.8#System_Convert_ToInt32_System_Double_)

    }
}