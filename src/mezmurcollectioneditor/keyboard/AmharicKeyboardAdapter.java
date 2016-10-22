package mezmurcollectioneditor.keyboard;

import java.io.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.util.*;

/**
 *
 * @author Filippo
 */
public class AmharicKeyboardAdapter extends KeyAdapter {

    private Document document;
    private char prevKey = ' ';
    private String digits = "0123456789";
    private String ethiopicPunc = "<>`\',;\":^~<";
    private boolean prevBaseKey = false;
    private boolean regularKeyboard = false;
    private boolean ethiopicNumeral = false;
    private HashMap firstOrder = new HashMap();
    private String modifiers = " uiayeo]";
    private String ethiopicDigits = " \u1369\u136a\u136b\u136c\u136d\u136e\u136f\u1370\u1371\u1372 \u137b";
    private String keymapFile = "mezmurcollectioneditor/assets/kwk_keyboard.txt";
    private final JTextComponent component;

    public AmharicKeyboardAdapter(JTextComponent component) {
        this.component = component;
        this.document = component.getDocument();

        // read KWK first order keymap from kwk_keyboard.txt 
        StringBuilder buffer = new StringBuilder();
        try {
            InputStream is = ClassLoader.getSystemResourceAsStream(keymapFile);
            int c = is.read();
            while (c != -1) {
                buffer.append((char) c);
                c = is.read();
            }
        } catch (IOException e) {
        }

        StringTokenizer st = new StringTokenizer(buffer.toString(), " =\n");
        while (st.hasMoreTokens()) {
            firstOrder.put(st.nextToken().trim(), st.nextToken().trim());
        }
    }

    /**
     * It overrides the method from KeyAdapter. It processes every key stroke.
     * It interprets each key stoke based on the regular or KWK mode. The KWK
     * keyboard system is for Ethiopic.
     *
     * @param e an instance that encloses the key stroke info
     */
    @Override
    public void keyTyped(KeyEvent e) {

        char c = ' ';

        try {
            char key = e.getKeyChar();

            // set tuggles trigerred with Ctrl key
            if (e.getModifiers() == 2) {
                switch ((int) key) {
                    case 5:
                        regularKeyboard = !regularKeyboard;
                        break;
                    case 14:
                        ethiopicNumeral = !ethiopicNumeral;
                        break;
                }
            }

            // if keyboard mode is regular, then terminate
            if (regularKeyboard) {
                return;
            }

            // processes ethiopic letters, numerals, and punctuations
            String character;
            switch (e.getModifiers()) {
                case 0:
                    // if modifier key, update the previous char
                    int index = modifiers.indexOf(key);
                    if (index >= 1 && prevBaseKey) {
                        c = (char) (prevKey + index);
                        this.document.remove(this.component.getCaretPosition() - 1, 1);
                        e.setKeyChar(c);
                        prevBaseKey = false;
                        return;
                    } else if (index >= 1) {
                        e.consume();              // eat this character, don't display it
                    } else if (ethiopicPunc.indexOf(key) >= 0) {
                        // if modifier key, update the previous char
                        character = (String) firstOrder.get("" + e.getModifiers() + key);
                        c = (char) (Integer.parseInt(character));
                        e.setKeyChar(c);
                        prevBaseKey = false;
                        return;
                    }

                    // process Ethiopic number from 0,...,9. No hot key is used
                    if (ethiopicNumeral && digits.indexOf(key) >= 0) {
                        if (ethiopicDigits.indexOf(prevKey) >= 1 && digits.indexOf(key) == 0) {
                            character = (String) firstOrder.get(
                                    "" + ethiopicDigits.indexOf(prevKey) + "" + key);
                            this.document.remove(this.component.getCaretPosition() - 1, 1);
                        } else {
                            character = (String) firstOrder.get("" + e.getModifiers() + key);
                        }

                        if (character != null) {
                            c = (char) (Integer.parseInt(character));
                            e.setKeyChar(c);
                            prevKey = c;
                            prevBaseKey = false;
                            return;
                        }
                    } else if (!ethiopicNumeral && digits.indexOf(key) >= 0) {
                        return;
                    }

            }  // close switch

            switch (e.getModifiers()) {
                case 0:
                case 1:
                    // if basic key, insert it into the document
                    character = (String) firstOrder.get("" + e.getModifiers() + key);
                    if (character != null) {
                        c = (char) (Integer.parseInt(character));
                        e.setKeyChar(c);
                        prevKey = c;
                        prevBaseKey = true;
                        return;
                    } else {
                        prevBaseKey = false;
                        return;
                    }

                case 8:
                    // if (alt + basic key), insert it into the document
                    character = (String) firstOrder.get("" + e.getModifiers() + key);
                    if (character != null) {
                        c = (char) (Integer.parseInt(character));
                        //TODO
                        this.document.insertString(this.component.getCaretPosition(), "" + c, null);
                        prevKey = c;
                        prevBaseKey = true;
                    } else {
                    }
            }
        } catch (Exception msg) {
            System.out.println(msg.getMessage());
        }
    }
}
