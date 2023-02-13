package heyoom.second.updown.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class DownloadData {
	
	private Long download_seq;
	private int import_year;
	private String hscode_01;
	private String hscode_02;
	private String hscode_03;
	private String area_name;
	private String nation_name;
	private Long import_qty_01;
	private Long import_amt_01;
	private Long import_qty_02;
	private Long import_amt_02;
	private Long import_qty_03;
	private Long import_amt_03;
	private Long import_qty_04;
	private Long import_amt_04;
	private Long import_qty_05;
	private Long import_amt_05;
	private Long import_qty_06;
	private Long import_amt_06;
	private Long import_qty_07;
	private Long import_amt_07;
	private Long import_qty_08;
	private Long import_amt_08;
	private Long import_qty_09;
	private Long import_amt_09;
	private Long import_qty_10;
	private Long import_amt_10;
	private Long import_qty_11;
	private Long import_amt_11;
	private Long import_qty_12;
	private Long import_amt_12;
	

	@Builder
	public DownloadData (Long download_seq,
						int import_year,
						String hscode_01,
						String hscode_02,
						String hscode_03,
						String area_name,
						String nation_name,
						Long import_qty_01,
						Long import_amt_01,
						Long import_qty_02,
						Long import_amt_02,
						Long import_qty_03,
						Long import_amt_03,
						Long import_qty_04,
						Long import_amt_04,
						Long import_qty_05,
						Long import_amt_05,
						Long import_qty_06,
						Long import_amt_06,
						Long import_qty_07,
						Long import_amt_07,
						Long import_qty_08,
						Long import_amt_08,
						Long import_qty_09,
						Long import_amt_09,
						Long import_qty_10,
						Long import_amt_10,
						Long import_qty_11,
						Long import_amt_11,
						Long import_qty_12,
						Long import_amt_12
						) {
		
				this.download_seq = download_seq;
				this.import_year = import_year;
				this.hscode_01 = hscode_01;
				this.hscode_02 = hscode_02;
				this.hscode_03 = hscode_03;
				this.area_name = area_name;
				this.nation_name = nation_name;
				this.import_qty_01 = import_qty_01;
				this.import_amt_01 = import_amt_01;
				this.import_qty_02 = import_qty_02;
				this.import_amt_02 = import_amt_02;
				this.import_qty_03 = import_qty_03;
				this.import_amt_03 = import_amt_03;
				this.import_qty_04 = import_qty_04;
				this.import_amt_04 = import_amt_04;
				this.import_qty_05 = import_qty_05;
				this.import_amt_05 = import_amt_05;
				this.import_qty_06 = import_qty_06;
				this.import_amt_06 = import_amt_06;
				this.import_qty_07 = import_qty_07;
				this.import_amt_07 = import_amt_07;
				this.import_qty_08 = import_qty_08;
				this.import_amt_08 = import_amt_08;
				this.import_qty_09 = import_qty_09;
				this.import_amt_09 = import_amt_09;
				this.import_qty_10 = import_qty_10;
				this.import_amt_10 = import_amt_10;
				this.import_qty_11 = import_qty_11;
				this.import_amt_11 = import_amt_11;
				this.import_qty_12 = import_qty_12;
				this.import_amt_12 = import_amt_12;
			}

}
