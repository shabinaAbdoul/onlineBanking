package com.onlineBanking.mailConfig;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.onlineBanking.model.CcpOperation;
import com.onlineBanking.model.UserDetails;

public class LivretARibPdf {
	
		 
		 private UserDetails userDetails;
		    public LivretARibPdf( UserDetails userDetails) {
			super();
			this.userDetails = userDetails;
		}

			private void writeTableHeader(PdfPTable table) {
				float[] columnWidths = new float[]{25f, 20f, 20f, 10f, 30f};
				try {
					table.setWidths(columnWidths);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        PdfPCell cell = new PdfPCell();
		        cell.setBackgroundColor(Color.GRAY);
		        cell.setPadding(5);
		         
		        Font font = FontFactory.getFont(FontFactory.HELVETICA);
		       font.setColor(Color.WHITE);
		         
		        cell.setPhrase(new Phrase("ETABLISSEMENT", font));
		         
		        table.addCell(cell);
		         
		        cell.setPhrase(new Phrase("GUICHET", font));
		        table.addCell(cell);
		         
		        cell.setPhrase(new Phrase("N° DE COMPTE", font));
		        table.addCell(cell);
		         
		        cell.setPhrase(new Phrase("CLE RIB", font));
		        table.addCell(cell);
		         
		        cell.setPhrase(new Phrase("DOMICILIATION", font));
		        table.addCell(cell);       
		    }
		     
		    private void writeTableData(PdfPTable table) {
		    	
		        String Str = new String(userDetails.getLivretA().getNumIban());
		        	table.addCell(Str.substring(4, 9));
		            table.addCell(Str.substring(9, 14));
		            table.addCell(Str.substring(14, 25));
		            table.addCell(Str.substring(25, 27));
		            table.addCell("Bon heure la Banque enligne");
		       
		    }
		     
		    public void export(HttpServletResponse response) throws DocumentException, IOException {
		        Document document = new Document(PageSize.A4);
		        PdfWriter.getInstance(document, response.getOutputStream());
		         
		        document.open();
		        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		        fontTitle.setSize(18);
		        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		        font.setSize(15);
		        Font fontM = FontFactory.getFont(FontFactory.HELVETICA);
		        fontM.setSize(12);
		        Font fontSmall = FontFactory.getFont(FontFactory.HELVETICA);
		        fontSmall.setSize(10);
		        fontSmall.setColor(Color.BLACK);
		        
		        Image img=Image.getInstance("https://media.istockphoto.com/vectors/letter-linked-business-logo-bh-logo-design-bh-logo-design-for-real-vector-id1339017004?b=1&k=20&m=1339017004&s=170667a&w=0&h=f1X72CJ3_E4rhkpgmjzkIs4l1HcXmv_enlAXNmZSC24=");
		        img.scaleToFit(100, 100);
		        document.add(img);
		         
		        Paragraph p = new Paragraph("Relevé d'Identité Bancaire", fontTitle);
		        p.setAlignment(Paragraph.ALIGN_CENTER);
		        document.add(p);
		        LineSeparator ls = new LineSeparator();
		        document.add(new Chunk(ls));
		       
		        Paragraph banque = new Paragraph("RIB- Identifiant national de compte", font);
		        banque.setAlignment(Paragraph.ALIGN_LEFT);
		        document.add(banque);
		        PdfPTable table = new PdfPTable(5);
		        table.setWidthPercentage(100f);
		        table.setWidths(new float[] {2.5f, 3.5f, 3.0f, 3.0f, 1.5f});
		        table.setSpacingBefore(10);
		         
		        writeTableHeader(table);
		        writeTableData(table);
		         
		        document.add(table);
		        document.add(Chunk.NEWLINE);
		        
		        PdfPTable table1 = new PdfPTable(2);
		        table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		        table1.setWidthPercentage(100f);
		        table1.setSpacingBefore(10);
		        PdfPCell cell = new PdfPCell();
		        cell.setPadding(5);
		        cell.setPhrase(new Phrase("IBAN- Identifiant International de compte", font));
		        table1.addCell(cell);	
		        
		        cell.setPhrase(new Phrase("BIC- Banque Identification Code", font));
		        table1.addCell(cell);
		        
		        cell.setPhrase(new Phrase(userDetails.getLivretA().getNumIban(), fontM));
		        table1.addCell(cell);
		        
		        cell.setPhrase(new Phrase("BHBQFRYHPPL", fontM));
		        table1.addCell(cell);
		        
		        document.add(table1);	        
		        document.add(Chunk.NEWLINE);
		        
		        Paragraph banque2 = new Paragraph("Titulaire du compte", font);
		        banque2.setAlignment(Paragraph.ALIGN_LEFT);
		        document.add(banque2);
		        
		        
		        Paragraph userDet = new Paragraph(userDetails.getGenre()+" "+userDetails.getFirstName()+" "+userDetails.getLastName(), fontM);
		        userDet.setAlignment(Paragraph.ALIGN_LEFT);
		        document.add(userDet);
		        Paragraph userAddress= new Paragraph(userDetails.getAddress(), fontM);
		        userAddress.setAlignment(Paragraph.ALIGN_LEFT);
		        document.add(userAddress);
		        Paragraph userVille= new Paragraph(userDetails.getCodePostal()+", "+userDetails.getVille(), fontM);
		        userVille.setAlignment(Paragraph.ALIGN_LEFT);
		        document.add(userVille);
		        document.add(Chunk.NEWLINE);		       
		        document.close();
		         
		    }

	
}
