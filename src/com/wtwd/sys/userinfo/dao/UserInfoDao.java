package com.wtwd.sys.userinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.wtwd.sys.userinfo.domain.UserInfo;


/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public interface UserInfoDao{

	public String getUserInfoPK()throws DataAccessException;

	public Integer getUserInfoCount(UserInfo vo)throws DataAccessException;

	public Integer getUserInfoCount(DataParamMap dmap)throws DataAccessException;

	public List<DataMap> getUserInfo(UserInfo vo)throws DataAccessException;

	public List<DataMap> getUserInfoListByMap(DataParamMap dmap)throws DataAccessException;

	public List<DataMap> getUserInfoListByVo(UserInfo vo)throws DataAccessException;

	public int updateUserInfo(UserInfo vo)throws DataAccessException;

	public int insertUserInfo(UserInfo vo)throws DataAccessException;

	public int deleteUserInfo(UserInfo vo)throws DataAccessException;
}
