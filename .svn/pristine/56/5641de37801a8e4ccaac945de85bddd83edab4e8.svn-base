/**
 *    Copyright ${license.git.copyrightYears} the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package test.com.wtwd.wtpet.service;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.godoing.rose.lang.SystemException;
//import com.wtwd.common.config.ServiceBean;
//import org.mybatis.jpetstore.domain.Account;
//import org.mybatis.jpetstore.mapper.AccountMapper;
import com.wtwd.sys.innerw.wappusers.dao.WappUsersDao;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacadeImpl;

/**
 * @author yong hu
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class WappUsersServiceTest {

  @Mock
  private WappUsersDao wappusersMapper;
  
  @InjectMocks
  private WappUsersFacadeImpl wappusersService;

  @Test
  public void shouldCallTheMapperToInsert() {
    //given
	  WappUsers wappuser = new WappUsers();

    //when
	  try {
		wappusersService.insertWappUsers(wappuser);
	} catch (SystemException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    //then
    verify(wappusersMapper).insertWappUsers(Matchers.eq(wappuser));

  }

}
