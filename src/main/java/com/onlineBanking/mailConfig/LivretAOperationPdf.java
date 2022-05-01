package com.onlineBanking.mailConfig;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.onlineBanking.model.LivretAOperation;
import com.onlineBanking.model.UserDetails;

public class LivretAOperationPdf {
	
		 private List<LivretAOperation> livretAOperations;
		 private UserDetails userDetails;
		    public LivretAOperationPdf(List<LivretAOperation> livretAOperations, UserDetails userDetails) {
			super();
			this.livretAOperations = livretAOperations;
			this.userDetails = userDetails;
		}

			private void writeTableHeader(PdfPTable table) {
		        PdfPCell cell = new PdfPCell();
		        cell.setBackgroundColor(Color.GRAY);
		        cell.setPadding(5);
		         
		        Font font = FontFactory.getFont(FontFactory.HELVETICA);
		       font.setColor(Color.WHITE);
		         
		        cell.setPhrase(new Phrase("Date", font));
		         
		        table.addCell(cell);
		         
		        cell.setPhrase(new Phrase("Déscription", font));
		        table.addCell(cell);
		         
		        cell.setPhrase(new Phrase("Status", font));
		        table.addCell(cell);
		         
		        cell.setPhrase(new Phrase("Type", font));
		        table.addCell(cell);
		         
		        cell.setPhrase(new Phrase("Montant", font));
		        table.addCell(cell);       
		    }
		     
		    private void writeTableData(PdfPTable table) {
		        for (LivretAOperation livOp : livretAOperations) {
		        	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    		String myDateString = simpleDateFormat.format(livOp.getCreationDateTime());
		            table.addCell(myDateString);
		            table.addCell(livOp.getDescription());
		            table.addCell(livOp.getStatus().toString());
		            table.addCell(livOp.getType());
		            table.addCell(livOp.getAmount().toString());
		        }
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
		        img.scaleToFit(100, 50);
		        document.add(img);
		       
		      
		         
		        Paragraph p = new Paragraph("Relevé de vos comptes", fontTitle);
		        p.setAlignment(Paragraph.ALIGN_CENTER);
		        document.add(p);
		        
		       
		        Paragraph banque = new Paragraph("Bonne heure ", font);
		        banque.setAlignment(Paragraph.ALIGN_LEFT);
		        document.add(banque);
		        Paragraph banqueDet = new Paragraph("La banque en ligne ", fontM);
		        banqueDet.setAlignment(Paragraph.ALIGN_LEFT);
		        document.add(banqueDet);
		        Paragraph banqueTel = new Paragraph("Téléphone: 09 25 31 24 22", fontM);
		        banqueTel.setAlignment(Paragraph.ALIGN_LEFT);
		        document.add(banqueTel);
		        Paragraph banqueMail = new Paragraph("E-mail: bonne_heure@bonneHeure.fr", fontM);
		        banqueMail.setAlignment(Paragraph.ALIGN_LEFT);
		        document.add(banqueMail);
		        Paragraph userDet = new Paragraph(userDetails.getGenre()+" "+userDetails.getFirstName()+" "+userDetails.getLastName(), fontM);
		        userDet.setAlignment(Paragraph.ALIGN_RIGHT);
		        document.add(userDet);
		        Paragraph userAddress= new Paragraph(userDetails.getAddress(), fontM);
		        userAddress.setAlignment(Paragraph.ALIGN_RIGHT);
		        document.add(userAddress);
		        Paragraph userVille= new Paragraph(userDetails.getCodePostal()+", "+userDetails.getVille(), fontM);
		        userVille.setAlignment(Paragraph.ALIGN_RIGHT);
		        document.add(userVille);
		        document.add(Chunk.NEWLINE);
		        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    		String nowDate = simpleDateFormat.format(new Date());
	    		String month=livretAOperations.get(0).getCreationDateTime().toString().split("-")[1];
	    		String nowMonth=nowDate.split("/")[1];
		        Paragraph p1 = new Paragraph("Situation de vos comptes ", font);
		        p1.setAlignment(Paragraph.ALIGN_LEFT);
		        if(month.equalsIgnoreCase(nowMonth)) {
			        p1.add(new Phrase("Entre 01/"+month+"/2022 et "+nowDate, fontSmall));
			        }
			        else {
			        	p1.add(new Phrase("Entre 01/"+month+"/2022 et 30/"+month+"/2022", fontSmall));
					       	
			        }  
		       // p1.add(new Phrase("au 01/"+(Integer.parseInt(livretAOperations.get(0).getCreationDateTime().toString().split("-")[1])+01)+"/2022", fontSmall));
		        document.add(p1);
		       
		         
		        PdfPTable table1 = new PdfPTable(2);
		        table1.setWidthPercentage(100f);
		        
		        PdfPCell cell = new PdfPCell();
		        cell.setPadding(5);
		         
		        cell.setPhrase(new Phrase("CCP n° "+userDetails.getCcpAccount().getId(), fontM));
		         table1.addCell(cell);
		        cell.setPhrase(new Phrase("+ "+userDetails.getCcpAccount().getBalance(), fontM));
		        table1.addCell(cell);
		        cell.setPhrase(new Phrase("Livret A n° "+userDetails.getLivretA().getId(), fontM));
		        table1.addCell(cell);
		        cell.setPhrase(new Phrase("+ "+userDetails.getLivretA().getBalance(), fontM));
		       
		         table1.addCell(cell);
		         document.add(table1);
		         document.add(Chunk.NEWLINE);
		         Paragraph p2 = new Paragraph("Livret A ", font);
			        p2.setAlignment(Paragraph.ALIGN_LEFT);
			        p2.add(new Phrase("n° "+livretAOperations.get(0).getLivretA().getId(), fontSmall));
			        document.add(p2);	
			        Paragraph p3 = new Paragraph("IBAN: "+livretAOperations.get(0).getLivretA().getNumIban(), fontSmall);  
			        document.add(p3);
			        Paragraph p4 = new Paragraph("BIC: BHBQFRYHPPL", fontSmall);  
			        document.add(p4);
			        document.add(Chunk.NEWLINE);
		        PdfPTable table = new PdfPTable(5);
		        table.setWidthPercentage(100f);
		        table.setWidths(new float[] {2.5f, 3.5f, 3.0f, 3.0f, 1.5f});
		        table.setSpacingBefore(10);
		         
		        writeTableHeader(table);
		        writeTableData(table);
		         
		        document.add(table);
		         
		        document.close();
		         
		    }

	
}
