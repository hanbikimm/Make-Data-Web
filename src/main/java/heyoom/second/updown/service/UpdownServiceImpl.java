package heyoom.second.updown.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import heyoom.second.updown.domain.DownloadData;
import heyoom.second.updown.mapper.UpdownMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdownServiceImpl implements UpdownService {
	
	private final UpdownMapper updownMapper;
	
	@Value("${dir.path}")
	private String path;
	
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
	
	private String changeEncoding(String data) throws UnsupportedEncodingException {
		return new String(data.getBytes("EUC-KR"), "EUC-KR");
	}
	
	@Override
	public String makeTxt() throws IOException {
		List<DownloadData> downloadList = updownMapper.getDownLoadDatas();
		
		//현재 시간 파일이름 설정
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);
        String fileName = "D" + formatedNow + ".txt";
        
        //파일 생성
        File file = new File(path + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        
        FileWriter fw = new FileWriter(file);
        PrintWriter writer = new PrintWriter(fw);
        
        log.info("euc-kr= {}", changeEncoding(downloadList.get(0).getNation_name()));
        log.info("euc-kr's byte length= {}", changeEncoding(downloadList.get(0).getNation_name()).getBytes().length);
        log.info("byte length= {}", downloadList.get(0).getNation_name().getBytes("EUC-KR").length);
        
        //파일 쓰기
		for(DownloadData data : downloadList ) {
			writer.print(data.getImport_year());
			writer.print(data.getHscode_01());
			writer.print(data.getHscode_02());
			writer.print(writeZero(data.getHscode_03(), 4));
			writer.print(data.getHscode_03());
			writer.print(changeEncoding(data.getArea_name()));
			writer.print(writeSpace(data.getArea_name(), 12));
			writer.print(changeEncoding(data.getNation_name()));
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
		
		return fileName;
	}

	@Override
	public String makeExcel() {
		//현재 시간 파일이름 설정
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);
        String fileName = "D" + formatedNow + ".xlsx";
        
		 // 빈 Workbook 생성
        XSSFWorkbook workbook = new XSSFWorkbook();

        // 빈 Sheet를 생성
        XSSFSheet sheet = workbook.createSheet("수출 자료");
        sheet.setColumnWidth(5, 4000);
        
        // Sheet를 채우기 위한 데이터들을 Map에 저장
        List<DownloadData> downloadList = updownMapper.getDownLoadDatas();
        Map<Integer, Object[]> dataMap = new TreeMap<>();
        
        dataMap.put(1, new Object[]{"수출년도", "HSCODE1", "HSCODE2", "HSCODE3", "지역명", "국가명", "수출수량1", "수출금액1", "수출수량2", "수출금액2", 
        		"수출수량3", "수출금액3", "수출수량4", "수출금액4", "수출수량5", "수출금액5", "수출수량6", "수출금액6", "수출수량7", "수출금액7",
        		"수출수량8", "수출금액8", "수출수량9", "수출금액9", "수출수량10", "수출금액10", "수출수량11", "수출금액11", "수출수량12", "수출금액12"});
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
        

        // dataMap에서 keySet를 가져온다. 이 Set 값들을 조회하면서 데이터들을 sheet에 입력한다.
        Set<Integer> keyset = dataMap.keySet();
        int rownum = 0;

        // 알아야할 점, TreeMap을 통해 생성된 keySet는 for를 조회시, 키값이 오름차순으로 조회된다.
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
        	FileOutputStream out = new FileOutputStream(new File(path, fileName));
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return fileName;
	}
	
	

}
