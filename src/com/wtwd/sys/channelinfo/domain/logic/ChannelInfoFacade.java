package com.wtwd.sys.channelinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.channelinfo.domain.ChannelInfo;

public interface ChannelInfoFacade {
	
	public List<DataMap> getChannelInfo(ChannelInfo vo) throws SystemException;
	
	public DataList getChannelInfoListByVo(ChannelInfo vo) throws SystemException;
	
	public int getChannelInfoListCountByVo(ChannelInfo vo) throws SystemException;
	
	public int getChannelInfoMaxId(ChannelInfo vo) throws SystemException;
	
	public int insertChannelInfo(ChannelInfo vo) throws SystemException;

}
