package heyoom.second.updown.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import heyoom.second.updown.domain.DownloadData;
import heyoom.second.updown.domain.UploadData;

@Mapper
public interface UpdownMapper {

	List<DownloadData> getDownloadDatas();
	int postUploadData(UploadData data);
}
