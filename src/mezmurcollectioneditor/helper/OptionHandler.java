/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.helper;

import java.awt.print.PrinterJob;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JTextArea;

/**
 *
 * @author Filippo
 */
public class OptionHandler {
    
    public void print(JTextArea pane) {
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
         PrinterJob job = PrinterJob.getPrinterJob() ;
         //job.setPrintable(pane) ;

         if (job.printDialog(pras)) {
            try {
               job.print(pras) ;
            } catch (Exception ex) {
               System.out.println(ex) ;
            }
         }         
      }
}
