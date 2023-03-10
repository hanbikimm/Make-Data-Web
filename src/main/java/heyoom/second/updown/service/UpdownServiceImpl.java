package heyoom.second.updown.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import heyoom.second.updown.domain.DownloadData;
import heyoom.second.updown.domain.UploadData;
import heyoom.second.updown.mapper.UpdownMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdownServiceImpl implements UpdownService {
	
	private final UpdownMapper updownMapper;
	
	@Value("${download.path}")
	private String downloadPath;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@Value("${stored.path}")
	private String storedPath;
	
	private String getMonthAndFileName(String type, String extension) {
		//?????? ?????? ???????????? ??????
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedDate = now.format(dateFormatter);
        String fileName = type + formatedDate + extension;
        
        // ?????? ??????
        String monthFolder = formatedDate.substring(0, 6) + "/";
		File folder = new File(downloadPath + monthFolder);
        if (!folder.exists()) {
			folder.mkdirs();
        }
        
        return monthFolder + fileName;
	}
	
	private String getFileName(String type, String extension) {
		//?????? ?????? ???????????? ??????
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedDate = now.format(dateFormatter);
        String fileName = type + formatedDate + extension;
        
        return fileName;
	}
	
	private String writeSpace(String data, int byteLength) throws UnsupportedEncodingException {
		int spaceLength = byteLength - data.getBytes("EUC-KR").length;
		String result = "";
		if (data.getBytes("EUC-KR").length < byteLength) {
			for (int i=0; i<spaceLength; i++) {
				result += " ";
			} 
		}
		return result;
	}
	
	private String writeZero(String data, int byteLength) throws UnsupportedEncodingException {
		int spaceLength = byteLength - data.getBytes("EUC-KR").length;
		String result = "";
		if (data.getBytes().length < byteLength) {
			for (int i=0; i<spaceLength; i++) {
				result += "0";
			} 
		}
		return result;
	}
	
	@Override
	public String downloadTxt() throws IOException {
		//?????? ?????? ??? ?????? ?????? ??????
		String monthAndFileName = getMonthAndFileName("D", ".txt");
        
        //?????? ?????? ??? writer ??????
        FileWriter fw = new FileWriter(new File(downloadPath + monthAndFileName));
        PrintWriter writer = new PrintWriter(fw);
        
        List<DownloadData> downloadList = updownMapper.getDownloadDatas();
        
        //?????? ??????
		for(DownloadData data : downloadList) {
			writer.print(data.getImport_year());
			writer.print(data.getHscode_01());
			writer.print(data.getHscode_02());
			writer.print(writeZero(data.getHscode_03(), 4));
			writer.print(data.getHscode_03());
			writer.print(data.getArea_name());
			writer.print(writeSpace(data.getArea_name(), 12));
			writer.print(data.getNation_name());
			writer.print(writeSpace(data.getNation_name(), 22));
			writer.print(String.format("%013d", data.getImport_qty_01()));
			writer.print(String.format("%013d", data.getImport_amt_01()));
			writer.print(String.format("%013d", data.getImport_qty_02()));
			writer.print(String.format("%013d", data.getImport_amt_02()));
			writer.print(String.format("%013d", data.getImport_qty_03()));
			writer.print(String.format("%013d", data.getImport_amt_03()));
			writer.print(String.format("%013d", data.getImport_qty_04()));
			writer.print(String.format("%013d", data.getImport_amt_04()));
			writer.print(String.format("%013d", data.getImport_qty_05()));
			writer.print(String.format("%013d", data.getImport_amt_05()));
			writer.print(String.format("%013d", data.getImport_qty_06()));
			writer.print(String.format("%013d", data.getImport_amt_06()));
			writer.print(String.format("%013d", data.getImport_qty_07()));
			writer.print(String.format("%013d", data.getImport_amt_07()));
			writer.print(String.format("%013d", data.getImport_qty_08()));
			writer.print(String.format("%013d", data.getImport_amt_08()));
			writer.print(String.format("%013d", data.getImport_qty_09()));
			writer.print(String.format("%013d", data.getImport_amt_09()));
			writer.print(String.format("%013d", data.getImport_qty_10()));
			writer.print(String.format("%013d", data.getImport_amt_10()));
			writer.print(String.format("%013d", data.getImport_qty_11()));
			writer.print(String.format("%013d", data.getImport_amt_11()));
			writer.print(String.format("%013d", data.getImport_qty_12()));
			writer.println(String.format("%013d", data.getImport_amt_12()));
		}
		writer.close();
		
		return monthAndFileName;
	}
	
	@Override
	public String downloadExcel() {
		//?????? ?????? ??? ?????? ?????? ??????
		String monthAndFileName = getMonthAndFileName("D", ".xlsx");
        
		 // ??? Workbook ??????
        XSSFWorkbook workbook = new XSSFWorkbook();

        // ??? Sheet??? ??????
        XSSFSheet sheet = workbook.createSheet("?????? ??????");
        sheet.setColumnWidth(5, 4000);
        
        // Sheet??? ????????? ?????? ??????????????? Map??? ??????
        List<DownloadData> downloadList = updownMapper.getDownloadDatas();
        Map<Integer, Object[]> dataMap = new TreeMap<>();
        
        dataMap.put(1, new Object[]{"????????????", "HSCODE1", "HSCODE2", "HSCODE3", "?????????", "?????????", "????????????1", "????????????1", "????????????2", "????????????2", 
        		"????????????3", "????????????3", "????????????4", "????????????4", "????????????5", "????????????5", "????????????6", "????????????6", "????????????7", "????????????7",
        		"????????????8", "????????????8", "????????????9", "????????????9", "????????????10", "????????????10", "????????????11", "????????????11", "????????????12", "????????????12"});
        for (int i=0; i<downloadList.size(); i++) {
        	DownloadData data = downloadList.get(i);
        	dataMap.put(i+2, new Object[]{data.getImport_year(), data.getHscode_01(), data.getHscode_02(), data.getHscode_03(), data.getArea_name(),
        			data.getNation_name(), data.getImport_qty_01(), data.getImport_amt_01(), data.getImport_qty_02(), data.getImport_amt_02(),
        			data.getImport_qty_03(), data.getImport_amt_03(), data.getImport_qty_04(), data.getImport_amt_04(),
        			data.getImport_qty_05(), data.getImport_amt_05(), data.getImport_qty_06(), data.getImport_amt_06(),
        			data.getImport_qty_07(), data.getImport_amt_07(), data.getImport_qty_08(), data.getImport_amt_08(),
        			data.getImport_qty_09(), data.getImport_amt_09(), data.getImport_qty_10(), data.getImport_amt_10(),
        			data.getImport_qty_11(), data.getImport_amt_11(), data.getImport_qty_12(), data.getImport_amt_12()});
		}
        

        // dataMap?????? keySet??? ????????????. ??? Set ????????? ??????????????? ??????????????? sheet??? ????????????.
        Set<Integer> keyset = dataMap.keySet();
        int rownum = 0;

        // ???????????? ???, TreeMap??? ?????? ????????? keySet??? for??? ?????????, ????????? ?????????????????? ????????????.
        for (int key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = dataMap.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cell.setCellValue((String)obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer)obj);
                } else if (obj instanceof Long) {
                	cell.setCellValue((Long)obj);
				}
            }
        }
        
        try {
        	FileOutputStream out = new FileOutputStream(new File(downloadPath + monthAndFileName));
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return monthAndFileName;
	}
	
	private String intToString(int num) {
		return Integer.toString(num);
	}
	
	private String longToString(Long num) {
		return Long.toString(num);
	}

	@Override
	public String downloadXml() {
		//?????? ?????? ??? ?????? ?????? ??????
		String monthAndFileName = getMonthAndFileName("D", ".xml");
	    
		try {
			// Document ??????(?????? ??????)
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			// standalone="no" ??????
			document.setXmlStandalone(true);  
			
			// Document??? products ?????? ??????
			Element root = document.createElement("Root");
			document.appendChild(root);
			
			List<DownloadData> downloadList = updownMapper.getDownloadDatas();
			
			for(DownloadData data  : downloadList) {
				// ?????? ??????
				Element text = document.createElement("text");
				Element exportYear = document.createElement("export_year");
				exportYear.setTextContent(intToString(data.getImport_year()));
				Element hscode01 = document.createElement("hscode_01");
				hscode01.setTextContent(data.getHscode_01());
				Element hscode02 = document.createElement("hscode_02");
				hscode02.setTextContent(data.getHscode_02());
				Element hscode03 = document.createElement("hscode_03");
				hscode03.setTextContent(data.getHscode_03());
				Element areaName = document.createElement("area_name");
				areaName.setTextContent(data.getArea_name());
				Element nationName = document.createElement("nation_name");
				nationName.setTextContent(data.getNation_name());
				Element exportQty01 = document.createElement("export_qty_01");
				exportQty01.setTextContent(longToString(data.getImport_qty_01()));
				Element exportAmt01 = document.createElement("export_amt_01");
				exportAmt01.setTextContent(longToString(data.getImport_amt_01()));
				Element exportQty02 = document.createElement("export_qty_02");
				exportQty02.setTextContent(longToString(data.getImport_qty_02()));
				Element exportAmt02 = document.createElement("export_amt_02");
				exportAmt02.setTextContent(longToString(data.getImport_amt_02()));
				Element exportQty03 = document.createElement("export_qty_03");
				exportQty03.setTextContent(longToString(data.getImport_qty_03()));
				Element exportAmt03 = document.createElement("export_amt_03");
				exportAmt03.setTextContent(longToString(data.getImport_amt_03()));
				Element exportQty04 = document.createElement("export_qty_04");
				exportQty04.setTextContent(longToString(data.getImport_qty_04()));
				Element exportAmt04 = document.createElement("export_amt_04");
				exportAmt04.setTextContent(longToString(data.getImport_amt_04()));
				Element exportQty05 = document.createElement("export_qty_05");
				exportQty05.setTextContent(longToString(data.getImport_qty_05()));
				Element exportAmt05 = document.createElement("export_amt_05");
				exportAmt05.setTextContent(longToString(data.getImport_amt_05()));
				Element exportQty06 = document.createElement("export_qty_06");
				exportQty06.setTextContent(longToString(data.getImport_qty_06()));
				Element exportAmt06 = document.createElement("export_amt_06");
				exportAmt06.setTextContent(longToString(data.getImport_amt_06()));
				Element exportQty07 = document.createElement("export_qty_07");
				exportQty07.setTextContent(longToString(data.getImport_qty_07()));
				Element exportAmt07 = document.createElement("export_amt_07");
				exportAmt07.setTextContent(longToString(data.getImport_amt_07()));
				Element exportQty08 = document.createElement("export_qty_08");
				exportQty08.setTextContent(longToString(data.getImport_qty_08()));
				Element exportAmt08 = document.createElement("export_amt_08");
				exportAmt08.setTextContent(longToString(data.getImport_amt_08()));
				Element exportQty09 = document.createElement("export_qty_09");
				exportQty09.setTextContent(longToString(data.getImport_qty_09()));
				Element exportAmt09 = document.createElement("export_amt_09");
				exportAmt09.setTextContent(longToString(data.getImport_amt_09()));
				Element exportQty10 = document.createElement("export_qty_10");
				exportQty10.setTextContent(longToString(data.getImport_qty_10()));
				Element exportAmt10 = document.createElement("export_amt_10");
				exportAmt10.setTextContent(longToString(data.getImport_amt_10()));
				Element exportQty11 = document.createElement("export_qty_11");
				exportQty11.setTextContent(longToString(data.getImport_qty_11()));
				Element exportAmt11 = document.createElement("export_amt_11");
				exportAmt11.setTextContent(longToString(data.getImport_amt_11()));
				Element exportQty12 = document.createElement("export_qty_12");
				exportQty12.setTextContent(longToString(data.getImport_qty_12()));
				Element exportAmt12 = document.createElement("export_amt_12");
				exportAmt12.setTextContent(longToString(data.getImport_amt_12()));
				
				// ?????? ??????
				root.appendChild(text);
				text.appendChild(exportYear);
				text.appendChild(hscode01);
				text.appendChild(hscode02);
				text.appendChild(hscode03);
				text.appendChild(areaName);
				text.appendChild(nationName);
				text.appendChild(exportQty01);
				text.appendChild(exportAmt01);
				text.appendChild(exportQty02);
				text.appendChild(exportAmt02);
				text.appendChild(exportQty03);
				text.appendChild(exportAmt03);
				text.appendChild(exportQty04);
				text.appendChild(exportAmt04);
				text.appendChild(exportQty05);
				text.appendChild(exportAmt05);
				text.appendChild(exportQty06);
				text.appendChild(exportAmt06);
				text.appendChild(exportQty07);
				text.appendChild(exportAmt07);
				text.appendChild(exportQty08);
				text.appendChild(exportAmt08);
				text.appendChild(exportQty09);
				text.appendChild(exportAmt09);
				text.appendChild(exportQty10);
				text.appendChild(exportAmt10);
				text.appendChild(exportQty11);
				text.appendChild(exportAmt11);
				text.appendChild(exportQty12);
				text.appendChild(exportAmt12);
			}
			
			// XML ??????
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// setOutputProperty  : ???????????? ?????? ??? ??????
			transformer.setOutputProperty("encoding", "UTF-8");  
			// ???????????? ??????
			transformer.setOutputProperty("indent", "yes");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			// document.setXmlStandalone(true); ?????? ????????? ??? ?????? ????????? ??????
			transformer.setOutputProperty("doctype-public", "yes");
			
			Source source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(downloadPath + monthAndFileName));
			
			transformer.transform(source, result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return monthAndFileName;
	}

	@Override
	public String uploadTxt() {
		String fileName = getFileName("U", ".txt");
		String monthAndFileName = getMonthAndFileName("U", ".txt");
		
		try{
			// ?????? ?????? ????????? ??????
			File file = new File(storedPath + fileName);
			File folder = new File(uploadPath + monthAndFileName.substring(0, 7));
			if (!folder.exists()) {
				folder.mkdirs();
			}	
			
			File copyFile = new File(uploadPath + monthAndFileName);
			Files.copy(file.toPath(), copyFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
		 
	        String data;
	        Long upload_seq = 1L;
	        while ((data = reader.readLine()) != null) {
	        	UploadData upload = new UploadData();
	        	String changeSpace = data.replaceAll("\\s+", " ");
	        	String[] dataList = changeSpace.split(" ");
	        	upload.setExec_way('M');
	        	upload.setFile_kind("TXT");
	        	upload.setUpload_seq(upload_seq);
	        	upload_seq++;
	        	upload.setExport_year(Integer.parseInt(dataList[0].substring(0, 4)));
	        	upload.setHscode_01(dataList[0].substring(4, 8));
	        	upload.setHscode_02(dataList[0].substring(8, 10));
	        	upload.setHscode_03(dataList[0].substring(10, 14));
	        	upload.setArea_name(dataList[0].substring(14));
	        	upload.setNation_name(dataList[1]);
	        	upload.setExport_qty_01(Long.parseLong(dataList[2].substring(0, 13)));
	        	upload.setExport_amt_01(Long.parseLong(dataList[2].substring(13, 26)));
	        	upload.setExport_qty_02(Long.parseLong(dataList[2].substring(26, 39)));
	        	upload.setExport_amt_02(Long.parseLong(dataList[2].substring(39, 52)));
	        	upload.setExport_qty_03(Long.parseLong(dataList[2].substring(52, 65)));
	        	upload.setExport_amt_03(Long.parseLong(dataList[2].substring(65, 78)));
	        	upload.setExport_qty_04(Long.parseLong(dataList[2].substring(78, 91)));
	        	upload.setExport_amt_04(Long.parseLong(dataList[2].substring(91, 104)));
	        	upload.setExport_qty_05(Long.parseLong(dataList[2].substring(104, 117)));
	        	upload.setExport_amt_05(Long.parseLong(dataList[2].substring(117, 130)));
	        	upload.setExport_qty_06(Long.parseLong(dataList[2].substring(130, 143)));
	        	upload.setExport_amt_06(Long.parseLong(dataList[2].substring(143, 156)));
	        	upload.setExport_qty_07(Long.parseLong(dataList[2].substring(156, 169)));
	        	upload.setExport_amt_07(Long.parseLong(dataList[2].substring(169, 182)));
	        	upload.setExport_qty_08(Long.parseLong(dataList[2].substring(182, 195)));
	        	upload.setExport_amt_08(Long.parseLong(dataList[2].substring(195, 208)));
	        	upload.setExport_qty_09(Long.parseLong(dataList[2].substring(208, 221)));
	        	upload.setExport_amt_09(Long.parseLong(dataList[2].substring(221, 234)));
	        	upload.setExport_qty_10(Long.parseLong(dataList[2].substring(234, 247)));
	        	upload.setExport_amt_10(Long.parseLong(dataList[2].substring(247, 260)));
	        	upload.setExport_qty_11(Long.parseLong(dataList[2].substring(260, 273)));
	        	upload.setExport_amt_11(Long.parseLong(dataList[2].substring(273, 286)));
	        	upload.setExport_qty_12(Long.parseLong(dataList[2].substring(286, 299)));
	        	upload.setExport_amt_12(Long.parseLong(dataList[2].substring(299, 312)));
	        	
	        	int result = updownMapper.postUploadData(upload);
	        }
	        reader.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "TEXT ?????? UPLOAD ??????";
	}

	@Override
	public String uploadExcel() {
		String fileName = getFileName("U", ".xlsx");
		String monthAndFileName = getMonthAndFileName("U", ".xlsx");
		File file = new File(storedPath + fileName);
		
		if (!file.exists()) {
			fileName = getFileName("U", ".xls");
			monthAndFileName = getMonthAndFileName("U", ".xls");
			file = new File(storedPath + fileName);
		}
		
		try{
			// ?????? ?????? ????????? ??????
			
			File folder = new File(uploadPath + monthAndFileName.substring(0, 7));
			if (!folder.exists()) {
				folder.mkdirs();
			}	

			File copyFile = new File(uploadPath + monthAndFileName);
			Files.copy(file.toPath(), copyFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
			
			//?????? ??????
			FileInputStream fileStream = new FileInputStream(file);
            
			Sheet sheet = null;
            // workbook??? sheet??? ????????????.
            if(fileName.endsWith(".xlsx")) {
            	try (XSSFWorkbook workbook = new XSSFWorkbook(fileStream)) {
					sheet = workbook.getSheet("????????????");
					log.info("xlsx ??????");				
				}
            }else if (fileName.endsWith(".xls")) {
				try (HSSFWorkbook workbook = new HSSFWorkbook(fileStream)) {
					sheet = workbook.getSheet("????????????");
					log.info("xls ??????");		
				}
			}
            
            // ?????? ???(row)?????? ????????????.
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i=1; i<=rows; i++) {
				Row row = sheet.getRow(i);
				
				if(row != null) {
					UploadData data = new UploadData();
					data.setExec_way('M');
					data.setFile_kind("EXL");
					data.setUpload_seq((long) i);
					
					int cells = row.getPhysicalNumberOfCells();
					for (int j=0; j<cells; j++) {
						Cell cell = row.getCell(j);
						switch (j) {
						case 0:
							data.setExport_year((int)cell.getNumericCellValue());
							break;
						case 1:
							if(cell.getCellType() == CellType.STRING) {
								data.setHscode_01(cell.getStringCellValue());
							}else {
								data.setHscode_01(Integer.toString((int)cell.getNumericCellValue()));
							}
								
							break;
						case 2:
							if(cell.getCellType() == CellType.STRING) {
								data.setHscode_02(cell.getStringCellValue());
							}else {
								data.setHscode_02(Integer.toString((int)cell.getNumericCellValue()));
							}
							break;
						case 3:
							if(cell.getCellType() == CellType.STRING) {
								data.setHscode_03(cell.getStringCellValue());
							}else {
								data.setHscode_03(Integer.toString((int)cell.getNumericCellValue()));
							}
							
							break;
						case 4:
							data.setArea_name(cell.getStringCellValue());
							break;
						case 5:
							data.setNation_name(cell.getStringCellValue());
							break;
						case 6:
							data.setExport_qty_01((long) cell.getNumericCellValue());
							break;
						case 7:
							data.setExport_amt_01((long) cell.getNumericCellValue());
							break;
						case 8:
							data.setExport_qty_02((long) cell.getNumericCellValue());
							break;
						case 9:
							data.setExport_amt_02((long) cell.getNumericCellValue());
							break;
						case 10:
							data.setExport_qty_03((long) cell.getNumericCellValue());
							break;
						case 11:
							data.setExport_amt_03((long) cell.getNumericCellValue());
							break;
						case 12:
							data.setExport_qty_04((long) cell.getNumericCellValue());
							break;
						case 13:
							data.setExport_amt_04((long) cell.getNumericCellValue());
							break;
						case 14:
							data.setExport_qty_05((long) cell.getNumericCellValue());
							break;
						case 15:
							data.setExport_amt_05((long) cell.getNumericCellValue());
							break;
						case 16:
							data.setExport_qty_06((long) cell.getNumericCellValue());
							break;
						case 17:
							data.setExport_amt_06((long) cell.getNumericCellValue());
							break;
						case 18:
							data.setExport_qty_07((long) cell.getNumericCellValue());
							break;
						case 19:
							data.setExport_amt_07((long) cell.getNumericCellValue());
							break;
						case 20:
							data.setExport_qty_08((long) cell.getNumericCellValue());
							break;
						case 21:
							data.setExport_amt_08((long) cell.getNumericCellValue());
							break;
						case 22:
							data.setExport_qty_09((long) cell.getNumericCellValue());
							break;
						case 23:
							data.setExport_amt_09((long) cell.getNumericCellValue());
							break;
						case 24:
							data.setExport_qty_10((long) cell.getNumericCellValue());
							break;
						case 25:
							data.setExport_amt_10((long) cell.getNumericCellValue());
							break;
						case 26:
							data.setExport_qty_11((long) cell.getNumericCellValue());
							break;
						case 27:
							data.setExport_amt_11((long) cell.getNumericCellValue());
							break;
						case 28:
							data.setExport_qty_12((long) cell.getNumericCellValue());
							break;
						case 29:
							data.setExport_amt_12((long) cell.getNumericCellValue());
							break;
						}
					}
					
					int result = updownMapper.postUploadData(data);
//					log.info("upload result= {}", result);
				}
			}
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		return "Excel ?????? Upload ??????";
	}

	@Override
	public String uploadXml() {
		String fileName = getFileName("U", ".xml");
		String monthAndFileName = getMonthAndFileName("U", ".xml");
		try {
			// ?????? ?????? ????????? ??????
			File file = new File(storedPath + fileName);
			File folder = new File(uploadPath + monthAndFileName.substring(0, 7));
			if (!folder.exists()) {
				folder.mkdirs();
			}	
			File copyFile = new File(uploadPath + monthAndFileName);
			Files.copy(file.toPath(), copyFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
			//?????? ??????
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file); // product.xml ??????(??????)
			
			// ????????? ??????(root)
			Element root = document.getDocumentElement(); 
			
			// ????????? ????????? ?????? ?????????
			NodeList nodeList = root.getChildNodes(); // ??????????????? ????????????
			Long seq_num = 1L;
			
			for(int i=0; i<nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) { // ELEMENT_NODE : Element???????????? ???????????? ??? (?????????(#text) ????????????)
					NodeList childNodeList = node.getChildNodes();
					
					UploadData data = new UploadData();
					data.setExec_way('M');
					data.setFile_kind("XML");
					data.setUpload_seq(seq_num);
					seq_num++;
					
					for(int j=0; j<childNodeList.getLength(); j++) {
						Node childNode = childNodeList.item(j);
						if(childNode.getNodeType() == Node.ELEMENT_NODE) {
							switch(childNode.getNodeName()) {
							case "export_year" : 
								data.setExport_year(Integer.parseInt(childNode.getTextContent())); 
								break;
							case "hscode_01" : 
								data.setHscode_01(childNode.getTextContent()); 
								break;
							case "hscode_02" : 
								data.setHscode_02(childNode.getTextContent()); 
								break;
							case "hscode_03" : 
								data.setHscode_03(childNode.getTextContent()); 
								break;
							case "area_name" : 
								data.setArea_name(childNode.getTextContent()); 
								break;
							case "nation_name" : 
								data.setNation_name(childNode.getTextContent()); 
								break;
							case "export_qty_01" : 
								data.setExport_qty_01(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_amt_01" : 
								data.setExport_amt_01(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_qty_02" : 
								data.setExport_qty_02(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_amt_02" : 
								data.setExport_amt_02(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_qty_03" : 
								data.setExport_qty_03(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_amt_03" : 
								data.setExport_amt_03(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_qty_04" : 
								data.setExport_qty_04(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_amt_04" : 
								data.setExport_amt_04(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_qty_05" : 
								data.setExport_qty_05(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_amt_05" : 
								data.setExport_amt_05(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_qty_06" : 
								data.setExport_qty_06(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_amt_06" : 
								data.setExport_amt_06(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_qty_07" : 
								data.setExport_qty_07(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_amt_07" : 
								data.setExport_amt_07(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_qty_08" : 
								data.setExport_qty_08(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_amt_08" : 
								data.setExport_amt_08(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_qty_09" : 
								data.setExport_qty_09(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_amt_09" : 
								data.setExport_amt_09(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_qty_10" : 
								data.setExport_qty_10(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_amt_10" : 
								data.setExport_amt_10(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_qty_11" : 
								data.setExport_qty_11(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_amt_11" : 
								data.setExport_amt_11(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_qty_12" : 
								data.setExport_qty_12(Long.parseLong(childNode.getTextContent())); 
								break;
							case "export_amt_12" : 
								data.setExport_amt_12(Long.parseLong(childNode.getTextContent())); 
								break;
							}
						}
							
					}
					int result = updownMapper.postUploadData(data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "XML ?????? Upload ??????";
	}
	
	

}
