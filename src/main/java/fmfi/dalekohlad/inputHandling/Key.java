package fmfi.dalekohlad.inputHandling;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class Key<T extends KeyEvent> implements EventHandler {
    @Override
    public void handle(Event event) {
        if(event.getEventType() != KEY_PRESSED)
            return;
        KeyEvent key = (KeyEvent) event;
        if (key.getCode().isLetterKey()) {
            this.handleLetter(key); //toto treba dat inam, nie do this
        }
        else {
            this.handleOtherKey(key);
        }
    }

    private void handleLetter(KeyEvent key) {
        String text = key.getText();

        if(key.isShiftDown()) {
            if(text.compareTo("a") >= 0 && text.compareTo("z") <=0) {
                text = text.toUpperCase();
            }
            else {
                text = text.toLowerCase();
            }
        }
        char ch = (text.toCharArray())[0];
        switch (ch) {
            case 'f':
                System.out.println("f - pouzivatel zadava double hodnotu, nastavuje sa rýchlosť kupoly (0 - 60)");
                break;
            case 'y':
                System.out.println("y - synchronizacia kupoly");
                break;
            case 'a':
                System.out.println("a - nastavenie azimutu pre kupolu, pouzivatel zadava double hodnotu (0 - 360)");
                break;
            case 'L':
                System.out.println("L - dvojitý prompt na pouzivatela, jedna hodnota nastavi RA, druha DE (obe double)");
                break;
            case 'G':
                System.out.println("G - automatický pohyb/zastavenie pohybu na v minulosti zadaný ciel");
                break;
            case 'P':
                System.out.println("P - nezaimplementovane");
                break;
            case 'c':
                System.out.println("c - kalibracia polohy dalekohladu podla poslednej redukovanej snimky (t.j. takej, na ktorej bola spravena astrometria a vieme, kam sa pozerame)");
                break;
            case 'z':
                System.out.println("z - kalibracia polohy dalekohladu na zenit");
                break;
            case 'p':
                System.out.println("p - toggle na pole crossing");
                break;
            case 's':
                System.out.println("s - double prompt pouzivatela na zelanú teplotu kamery (aj negativne hodnoty)");
                break;
            case 'r':
                System.out.println("r - int prompt na pouzivatela - pocet opakovani snimkovania kamery (iba pozitivne hodnoty, väcsie ako 0)");
                break;
            case 'O':
                System.out.println("O - string prompt, mena pozorovatelov");
                break;
            case 'o':
                System.out.println("o - string prompt, nazov pozorovaneho objektu");
                break;
            case 'n':
                System.out.println("n - string prompt, lubovolný komentar ku pozorovaniu");
                break;
            case 't':
                System.out.println("t - toggle na zmenu typu snimky (stlacenie klavesy premeni typ na dalsi, pouzivatel nic nezadava)");
                break;
            case 'm':
                System.out.println("m - analogicky ako pri t, tentokrat pre mód kamery");
                break;
            case 'e':
                System.out.println("e - cas expozicie kamery - double prompt (hodnoty vyssie ako 0)");
                break;
            case 'd':
                System.out.println("d - delay medzi jednotlivými snimkami kameri, double prompt (hodnoty vyssie ako 0)");
                break;
            case 'S':
                System.out.println("S - nezaimplementovane");
                break;
            case 'E':
                System.out.println("E - zapnutie expozicie");
                break;
            case 'X':
                System.out.println("X - analogicky ako E");
                break;
            case 'A':
                System.out.println("A - analogicky ako X");
                break;
            case 'C':
                System.out.println("C - zmena filtra na C");
                break;
            case 'B':
                System.out.println("B - analogicky ako C");
                break;
            case 'V':
                System.out.println("V - analogicky ako C");
                break;
            case 'R':
                System.out.println("R - analogicky ako C");
                break;
            case 'I':
                System.out.println("I - analogicky ako C");
                break;
            case 'D':
                System.out.println("D - spustenie Dumpovania informacii do .txt súboru");
                break;
            case 'T':
                System.out.println("T - automaticka korekcia dalekohladu, ak sa dostal do nebezpecnej výsky");
                break;
            case 'H':
                System.out.println("H - goto zo Schedulera, este nezaimplementovane");
                break;
            case 'J':
                System.out.println("J - spustenie Schedulera");
                break;
            case 'K':
                System.out.println("K - zastavenie Schedulera");
                break;
            default:
                break;
        }
    }

    private void handleOtherKey(KeyEvent key) {
        KeyCode keyCode = key.getCode();
        switch (keyCode) {
            case LEFT:
                System.out.println("Left (sipka vlavo) - po stlaceni klavesy sa da zadať hodnota od 1 - 250 000 a po konfirmacii sa pohne motor v RA smere, double prompt");
                break;
            case RIGHT:
                System.out.println("Right - analogicky ako Left, ale do opacneho smeru, double prompt");
                break;
            case UP:
                System.out.println("Up - analogicky ako Left a Right, ale v osi DE, double prompt");
                break;
            case DOWN:
                System.out.println("Down - analogicky ako Up, ale v opacnom smere, double prompt");
                break;
            case INSERT:
                System.out.println("Insert - pohyb kupoly na východ, ziaden input od pouzivatela");
                break;
            case PAGE_UP:
                System.out.println("PageUp - pohyb kupoly na zapad, ziaden input od pouzivatela");
                break;
            case PAGE_DOWN:
                System.out.println("PageDown - stop RA motora");
                break;
            case HOME:
                System.out.println("Home - stop pohybu kupoly, ziaden input od pouzivatela");
                break;
            case END:
                System.out.println("End - stop RA aj DE motorov");
                break;
            case DELETE:
                System.out.println("Delete - stop DE motora");
                break;
            case OPEN_BRACKET:
                System.out.println("[ - enable RA aj DE motorov");
                break;
            case CLOSE_BRACKET:
                System.out.println("] - disable RA aj DE motorov");
                break;

            default:
                break;
        }
    }
}

