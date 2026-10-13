package com.nkd.pos.posapi.service;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JTextArea;

public class ReceiptPrint implements Printable {

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		
		final String SPACES = "         ";
		  int lineNumber;
	        int lineCount;
	        String strText = null;
	        int lineStart;           // start index of line in textarea
	        int lineEnd;             // end index of line in textarea
		
		 Graphics2D g2d = (Graphics2D) graphics;
	        Font font = new Font("MONOSPACED", Font.BOLD, 9);
	        
	        if (pageIndex < 0 || pageIndex >= 1) {
	            return Printable.NO_SUCH_PAGE;
	        }
	        
	        JTextArea textarea = new JTextArea(10, 40);
	        textarea.append(SPACES + "sadasdsadfsdfsdf" + "\n");
	        
	        System.out.println(2);
	        
	        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

	        g2d.setFont(font);
	        
	        lineNumber = 0;
	        lineCount = textarea.getLineCount();
	        strText = textarea.getText();
	        
	        

	        while (lineCount != 0) {
	            try {

	                lineStart = textarea.getLineStartOffset(lineNumber);
	                lineEnd = textarea.getLineEndOffset(lineNumber);
	                strText = textarea.getText(lineStart, lineEnd - lineStart);
	            } catch (Exception exception) {
	                System.out.println("Printing error:" + exception);                  // have to catch BadLocationException
	            }

	            g2d.drawString(strText, 1, (lineNumber + 1) * 18);
	            //spacing    between lines
	            lineNumber = lineNumber + 1;
	            lineCount--;
	        }
	        return Printable.PAGE_EXISTS;
	    }

}
