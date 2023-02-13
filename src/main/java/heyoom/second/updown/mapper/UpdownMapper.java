package heyoom.second.updown.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import heyoom.second.updown.domain.DownloadData;

@Mapper
public interface UpdownMapper {

	List<DownloadData> getDownLoadDatas();
}
