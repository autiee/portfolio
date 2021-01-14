package joukkuerekisteri.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import joukkuerekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.08.05 13:26:06 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class JoukkueTest {



  // Generated by ComTest BEGIN
  /** testRekisteroi85 */
  @Test
  public void testRekisteroi85() {    // Joukkue: 85
    Joukkue joukkue1 = new Joukkue(); 
    assertEquals("From: Joukkue line: 87", 0, joukkue1.getTunnusNro()); 
    joukkue1.rekisteroi(); 
    Joukkue joukkue2 = new Joukkue(); 
    joukkue2.rekisteroi(); 
    int n1 = joukkue1.getTunnusNro(); 
    int n2 = joukkue2.getTunnusNro(); 
    assertEquals("From: Joukkue line: 93", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString223 */
  @Test
  public void testToString223() {    // Joukkue: 223
    Joukkue joukkue = new Joukkue(); 
    joukkue.parse("   1  |  FC Honkola   | ��nekoski"); 
    assertEquals("From: Joukkue line: 226", true, joukkue.toString().startsWith("1|FC Honkola|��nekoski|")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse240 */
  @Test
  public void testParse240() {    // Joukkue: 240
    Joukkue joukkue = new Joukkue(); 
    joukkue.parse("   3  |  FC Honkola   | ��nekoski"); 
    assertEquals("From: Joukkue line: 243", 3, joukkue.getTunnusNro()); 
    assertEquals("From: Joukkue line: 244", true, joukkue.toString().startsWith("3|FC Honkola|��nekoski|")); 
    joukkue.rekisteroi(); 
    int n = joukkue.getTunnusNro(); 
    joukkue.parse(""+(n+20)); 
    joukkue.rekisteroi(); 
    assertEquals("From: Joukkue line: 250", n+20, joukkue.getTunnusNro()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testClone280 
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testClone280() throws CloneNotSupportedException {    // Joukkue: 280
    Joukkue joukkue = new Joukkue(); 
    joukkue.parse("   3  |  FC Honkola   | ��nekoski"); 
    Joukkue kopio = joukkue.clone(); 
    assertEquals("From: Joukkue line: 285", joukkue.toString(), kopio.toString()); 
    joukkue.parse("   4  |  FC Konginkangas   | ��nekoski"); 
    assertEquals("From: Joukkue line: 287", false, kopio.toString().equals(joukkue.toString())); 
  } // Generated by ComTest END
}