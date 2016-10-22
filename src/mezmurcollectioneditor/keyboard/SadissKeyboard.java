/*
 * @(#)SadissComboBoxEditor.java 1.0 10/24/2002
 * Copyright 2002 Senamirmir Project
 *
 * You may distribute and/or modify Sadiss under the conditions 
 * of the LaTeX Project Public License. The license is located at
 * http://www.latex-project.org/lppl.txt
 */

package mezmurcollectioneditor.keyboard ;

import java.io.* ;
import javax.swing.* ;
import javax.swing.text.* ;
import java.awt.event.* ;
import java.util.* ;

/**
 * <!-- ----------------------------------------------------------------------- -->
 * SadissKeyboard is based on modified KWK keyboard layout
 * @author abass alamene
 * @version 1.0 10/24/2002
 * <!-- ----------------------------------------------------------------------- -->
 */
public class SadissKeyboard extends KeyAdapter {
    
   private StyledDocument doc ;
   private JTextPane textPane ;
   private static char prevKey = ' ' ;
   private static String digits = "0123456789" ;
   private static String ethiopicPunc = "<>`\',;\":^~<" ;
   private static boolean prevBaseKey = false ;
   private static boolean regularKeyboard = false ;
   private static boolean ethiopicNumeral = false ;
   private static HashMap firstOrder ;
   private static SadissKeyboard sadissKeyboard ;
   private static String modifiers = " uiayeo]" ; 
   private static String ethiopicDigits = 
      " \u1369\u136a\u136b\u136c\u136d\u136e\u136f\u1370\u1371\u1372 \u137b" ;
   private String keymapFile = "mezmurcollectioneditor/assets/kwk_keyboard.txt" ;
         
   /**
    * Private constructor. Prevents object instantiation
    * @param textPane document container
    * @param doc the document to which modified key strokes are directed
    */
   public SadissKeyboard(JTextPane textPane, StyledDocument doc) {
      this.textPane = textPane ;
      this.doc = textPane.getStyledDocument() ;  

      if (firstOrder == null) {
         firstOrder =  new HashMap() ;           	            
         //SadissFileService sfs = new SadissFileService() ;         

            // read KWK first order keymap from kwk_keyboard.txt 
         StringBuffer buffer = new StringBuffer() ;
         try {
            InputStream is = ClassLoader.getSystemResourceAsStream(keymapFile) ;
            int c=0 ;
            c = is.read() ;

            while (c != -1) {
               buffer.append((char) c) ;
               c = is.read() ;
            }

         } catch (IOException e) {
            e.printStackTrace() ;
         }                    

         StringTokenizer st = new StringTokenizer(buffer.toString(), " =\n") ;      
         while (st.hasMoreTokens()) {
            firstOrder.put(st.nextToken().trim(), st.nextToken().trim()) ;
         }
      }
   }

   /**
    * It overrides the method from KeyAdapter. It processes every key 
    * stroke. It interprets each key stoke based on the regular or KWK mode. 
    * The KWK keyboard system is for Ethiopic. 
    * @param e an instance that encloses the key stroke info
    */
   public void keyTyped(KeyEvent e) {

      char c = ' ' ;
      
      try {
         char key = e.getKeyChar() ;

            // set tuggles trigerred with Ctrl key
         if ( e.getModifiers() == 2) {
            switch( (int) key) {
               case  5:  
                  regularKeyboard = !regularKeyboard ;  
                  //SadissJournal.getSadissControl().getSadissToolBar().getKWKLabel().setEnabled(!regularKeyboard) ;
                  break ;
               case 14:  ethiopicNumeral = !ethiopicNumeral ;  break ;
            }
         }

    	      // if keyboard mode is regular, then terminate
	      if (regularKeyboard) return ;

            // processes ethiopic letters, numerals, and punctuations
         String character ;      
         switch (e.getModifiers()) {
            case 0:
                     // if modifier key, update the previous char
               int index = modifiers.indexOf(key) ;
               if (index >= 1 && prevBaseKey) {
                  c = (char) (prevKey + index) ;
                  doc.remove(textPane.getCaretPosition() - 1, 1) ;
	               e.setKeyChar(c) ; 
                  prevBaseKey = false ;
                  return ;
               } else if (index >= 1) {
                  e.consume() ;              // eat this character, don't display it
               } else if (ethiopicPunc.indexOf(key) >= 0) {
                     // if modifier key, update the previous char
                  character = (String) firstOrder.get( "" + e.getModifiers() + key ) ;
                  c = (char) (Integer.parseInt(character)) ;
	               e.setKeyChar(c) ; 
                  prevBaseKey = false ;
                  return ;
               } 
             
                     // process Ethiopic number from 0,...,9. No hot key is used
               if (ethiopicNumeral && digits.indexOf(key) >= 0) {
                  if ( ethiopicDigits.indexOf(prevKey) >= 1 && digits.indexOf(key) == 0 ) {
                     character = (String) firstOrder.get( 
                                 "" + ethiopicDigits.indexOf(prevKey) + "" + key ) ;   
                     doc.remove(textPane.getCaretPosition() - 1, 1) ;
                  } else
                     character = (String) firstOrder.get( "" + e.getModifiers() + key ) ;

                  if (character != null) {
                     c = (char) (Integer.parseInt(character)) ;
	                  e.setKeyChar(c) ; 
                     prevKey = c ;
                     prevBaseKey = false ;
                     return ;
                  }
               } else if (!ethiopicNumeral && digits.indexOf(key) >= 0) {
                  return ;
               }

         }  // close switch


         switch (e.getModifiers()) {
            case 0:
            case 1:
                  // if basic key, insert it into the document
               character = (String) firstOrder.get( "" + e.getModifiers() + key ) ;
               if (character != null) {
                  c = (char) (Integer.parseInt(character)) ;
                  e.setKeyChar(c) ; 
                  prevKey = c ;
                  prevBaseKey = true ;
                  return ;
               } else {
                  prevBaseKey = false ;
                  return ;
               }

            case 8:
                  // if (alt + basic key), insert it into the document
               character = (String) firstOrder.get( "" + e.getModifiers() + key ) ;
               if (character != null) {
                  c = (char) (Integer.parseInt(character)) ;
    	            doc.insertString(textPane.getCaretPosition(),"" + c, null) ;
                  prevKey = c ;
                  prevBaseKey = true ;
                  return ;
               } else {
                  return ;
               }
         }
      } catch (Exception msg) {
         System.out.println(msg.getMessage()) ;
      }
   }  // close method
} // close class
