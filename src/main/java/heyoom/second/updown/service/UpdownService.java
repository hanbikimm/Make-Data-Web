package heyoom.second.updown.service;

import java.io.IOException;


public interface UpdownService {
	
	String downloadTxt() throws IOException;
	String downloadExcel();
	String downloadXml();
	
	String uploadTxt();
	String uploadExcel();
	String uploadXml();

}
