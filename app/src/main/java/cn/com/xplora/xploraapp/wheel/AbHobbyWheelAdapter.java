/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.com.xplora.xploraapp.wheel;

// TODO: Auto-generated Javadoc

import android.content.Context;

import java.util.List;

import cn.com.xplora.xploraapp.model.HobbyModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;

/**
 * © 2012 amsoft.cn
 * 名称：AbObjectWheelAdapter.java 
 * 描述：轮子适配器（对象）
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2013-05-17 下午6:46:29
 */
public class AbHobbyWheelAdapter implements AbWheelAdapter {

	/** The default items length. */
	public static final int DEFAULT_LENGTH = -1;

	// items
	/** The items. */
	private List<HobbyModel> hobbyList;
	// length
	/** The length. */
	private int length;

	private Context context;
	/**
	 * Constructor.
	 *
	 * @param length the max items length
	 */
	public AbHobbyWheelAdapter(Context context,List<HobbyModel> hobbyList, int length) {
		this.context = context;
		this.hobbyList = hobbyList;
		this.length = length;
	}

	/**
	 * Contructor.
	 *
	 */
	public AbHobbyWheelAdapter(Context context,List<HobbyModel> hobbyList) {
		this(context,hobbyList, DEFAULT_LENGTH);
	}

	/**
	 * 描述：TODO.
	 *
	 * @version v1.0
	 * @param index the index
	 * @return the item
	 * @author: amsoft.cn
	 * @date：2013-6-17 上午9:04:48
	 */
	@Override
	public String getItem(int index) {
		if (index >= 0 && index < hobbyList.size()) {
			String lang = CommonUtil.getLang(context);
			if("CHN".equalsIgnoreCase(lang)){
				return hobbyList.get(index).getHobbyName();
			}else{
				return hobbyList.get(index).getHobbyNameEn();
			}

		}
		return null;
	}

	/**
	 * 描述：TODO.
	 *
	 * @version v1.0
	 * @return the items count
	 * @author: amsoft.cn
	 * @date：2013-6-17 上午9:04:48
	 */
	@Override
	public int getItemsCount() {
		return hobbyList.size();
	}

	/**
	 * 描述：TODO.
	 *
	 * @version v1.0
	 * @return the maximum length
	 * @author: amsoft.cn
	 * @date：2013-6-17 上午9:04:48
	 */
	@Override
	public int getMaximumLength() {
		return length;
	}

}
