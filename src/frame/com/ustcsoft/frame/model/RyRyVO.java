package com.ustcsoft.frame.model;

import java.util.Date;

/**
 * nullVO
 * @author WangHao
 */
public class RyRyVO{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** 执法人员编号 */
	private String zfrybh;
	
	private String userName;

	/** 姓名 */
	private String xm;

	/** 性别 */
	private String xbbh;

	/** 身份证号 */
	private String sfzh;

	/** 出生日期 */
	private String csrq;

	/** 民族 */
	private String mzbh;

	/** 学历 */
	private String xl;

	/** 工作日期 */
	private String cjgzrq;

	/** 从事执法日期 */
	private String cszfrq;

	/** 岗位 */
	private String gwbh;

	/** 职务 */
	private String zwbh;

	/** 执法门类 */
	private String zfmlbh;

	/** 所属单位 */
	private String deptid;
 	
	/** 执法证号 */
	private String zfzh;

	/** 发证日期 */
	private String fzrq;

	/** 登记人 */
	private String djr;

	/** 登记日期 */
	private String djrq;

	/** 备注 */
	private String bz;

	/** 创建日期 */
	private Date createDate;

	/** 照片文件 */
	private String rypic;

	/** 审批状态 */
	private String fzzt;

	/** current sp deptid */
	private String deptsp;

	/** 执法区域 */
	private String zfqy;

	/** 毕业学校 */
	private String byxx;

	/** 毕业专业 */
	private String byzy;

	/** 毕业证书编号 */
	private String byzsbh;

	/** 分配渠道 */
	private String fpqd;

	/** 省内执法证号 */
	private String snzfzh;

	/** 现有部级执法证号 */
	private String xybjzfzh;

	/** 证件状态 */
	private String zjzt;

	/** 岗位培训证书编号 */
	private String gwzsbh;

	/** 档案文件，原系统表结构没有，新增 */
	private String dawj;

	/** 删除标示，原系统表结构没有，新增 */
	private String scbh;

	/**
	 * 机构编号
	 */
	private String bmbh;
	/**
	 *  性别
	 */
	private String xingBie;
	/**
	 * 执法门类
	 * @return
	 */
	private String zhiFaMenLei;
	/**
	 * 单位名称
	 * @return
	 */
	private String danWeiMingChen;
	/**
	 * 证件状态
	 * @return
	 */
	private String zhengJian;
	/**
	 * 民族
	 * @return
	 */
	private String mingZu;
	/**
	 * 学历
	 * @return
	 */
	private String xueLi;
	/**
	 * 岗位
	 * @return
	 */
	private String gangWei;
	/**
	 * 职务
	 * @return
	 */
	private String zhiWu;
	/**
	 * 审批状态
	 * @return
	 */
	private String shenPi;
	/**
	 * 分配渠道
	 * @return
	 */
	private String fenPeiQuDao;
	/**
	 * 当前数据是处于省或市或县（[1、2]：省  [3]：市   [4、5、6]：县）
	 */
	private String parJb;

	/**
	 * 年份（统计）
	 * @return
	 */
	private String nianFen;
	/**
	 * 统计类型（统计）
	 * @return
	 */
	private String tongJiType;
	/**
	 * 2013-06-18
	 */
//	private String deptId;
	private String deptName;
	
	/**
	 * fszt 发送状态，1表示成功，0表示不成功
	 */
	private String fszt;
	
	private String picSend;
	
	private String id;
	
/*	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}*/
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	// 课程列表-选择执法人员-分配人员需要的字段 start
	/**
	 * 组织id
	 */
	private String orgId;
	/**
	 * 查询范围
	 */
	private String chaXunFanWei;
	
	/**
	 * 是否委托
	 */
	private String isWeiTouDanWei;
	/**
	 * 组织名称
	 */
	private String orgName;
	/**
	 * 组织名称
	 */
	private String danWeiKeShi;
	public String getDanWeiKeShi() {
		return danWeiKeShi;
	}
	public void setDanWeiKeShi(String danWeiKeShi) {
		this.danWeiKeShi = danWeiKeShi;
	}

	/**
	 * 课程id
	 */
	private String keChengId;
	/**
	 * 执法人员id数组
	 */
	private String[] zfrybhArray;
	// 课程列表-选择执法人员-分配人员需要的字段 end
	
	// 调查问卷-选择被调查人-分配人员需要的字段 start
	/**
	 * 问卷id
	 */
	private String wenJuanId;
	// 调查问卷-选择被调查人-分配人员需要的字段 end
		
	//导出
	/** 序号 */
	private String rowNo;
	/** 名称 */
	private String name;
	/** 总数 */
	private String tolCount;
	/** 男总数 */
	private String nanCount;
	/** 女总数 */
	private String nvCount;
	/** 百分比*/
	private String biLi;
	
	//人员审批
	//意见
	private String spxx;
	//负责人签字
	private String sprybh;
	//日期
	private Date spsj;
	//审批状态
	private String spzt;
	//事件ID
	private String zjsjls;
	
	private String userOrgId;
	
	//资格审查
	//执法岗位代码
	private String zhiFaGangWei;
	
	//执法人员共同页面
	//证号
	private String zhengHao;
	//单位
	private String unit;
	// 检索条件：是否强制为本机构内检索
	private String forceOrg;

	public String getWenJuanId() {
		return wenJuanId;
	}
	public void setWenJuanId(String wenJuanId) {
		this.wenJuanId = wenJuanId;
	}

	/**
	 * WebService新增移动执法事件
	 * @return
	 */
	private String xingZhengQuHuaMingCheng;
	private String xingZhengQuHuaBianHao;
	private String xingZhengQuHuaId;
	private String simpleName;
	private String zhiFaJiGuan;
	private String userId;
	
	/**
	 * 选择执法评议 项目ID
	 */
	private String xiangMuId;
	
	private String isHaiShi;
	
	public String getBmbh() {
		return bmbh;
	}
	public void setBmbh(String bmbh) {
		this.bmbh = bmbh;
	}
	public String getXiangMuId() {
		return xiangMuId;
	}
	public void setXiangMuId(String xiangMuId) {
		this.xiangMuId = xiangMuId;
	}
	public String getZhengHao() {
		return zhengHao;
	}
	public void setZhengHao(String zhengHao) {
		this.zhengHao = zhengHao;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getZhiFaGangWei() {
		return zhiFaGangWei;
	}
	public void setZhiFaGangWei(String zhiFaGangWei) {
		this.zhiFaGangWei = zhiFaGangWei;
	}
	public String getParJb() {
		return parJb;
	}
	public void setParJb(String parJb) {
		this.parJb = parJb;
	}
	public String getZjsjls() {
		return zjsjls;
	}
	public void setZjsjls(String zjsjls) {
		this.zjsjls = zjsjls;
	}
	public String getSpxx() {
		return spxx;
	}
	public void setSpxx(String spxx) {
		this.spxx = spxx;
	}
	public String getSprybh() {
		return sprybh;
	}
	public void setSprybh(String sprybh) {
		this.sprybh = sprybh;
	}
	public Date getSpsj() {
		return spsj;
	}
	public void setSpsj(Date spsj) {
		this.spsj = spsj;
	}
	public String getSpzt() {
		return spzt;
	}
	public void setSpzt(String spzt) {
		this.spzt = spzt;
	}
	public String getUserOrgId() {
		return userOrgId;
	}
	public void setUserOrgId(String userOrgId) {
		this.userOrgId = userOrgId;
	}
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTolCount() {
		return tolCount;
	}
	public void setTolCount(String tolCount) {
		this.tolCount = tolCount;
	}
	public String getNanCount() {
		return nanCount;
	}
	public void setNanCount(String nanCount) {
		this.nanCount = nanCount;
	}
	public String getNvCount() {
		return nvCount;
	}
	public void setNvCount(String nvCount) {
		this.nvCount = nvCount;
	}
	public String getBiLi() {
		return biLi;
	}
	public void setBiLi(String biLi) {
		this.biLi = biLi;
	}
	public String getNianFen() {
		return nianFen;
	}
	public void setNianFen(String nianFen) {
		this.nianFen = nianFen;
	}
	public String getTongJiType() {
		return tongJiType;
	}
	public void setTongJiType(String tongJiType) {
		this.tongJiType = tongJiType;
	}
	public String getXingBie() {
		return xingBie;
	}
	public void setXingBie(String xingBie) {
		this.xingBie = xingBie;
	}
	public String getZhiFaMenLei() {
		return zhiFaMenLei;
	}
	public void setZhiFaMenLei(String zhiFaMenLei) {
		this.zhiFaMenLei = zhiFaMenLei;
	}
	public String getDanWeiMingChen() {
		return danWeiMingChen;
	}
	public void setDanWeiMingChen(String danWeiMingChen) {
		this.danWeiMingChen = danWeiMingChen;
	}
	public String getZhengJian() {
		return zhengJian;
	}
	public void setZhengJian(String zhengJian) {
		this.zhengJian = zhengJian;
	}
	public String getMingZu() {
		return mingZu;
	}
	public void setMingZu(String mingZu) {
		this.mingZu = mingZu;
	}
	public String getXueLi() {
		return xueLi;
	}
	public void setXueLi(String xueLi) {
		this.xueLi = xueLi;
	}
	public String getGangWei() {
		return gangWei;
	}
	public void setGangWei(String gangWei) {
		this.gangWei = gangWei;
	}
	public String getZhiWu() {
		return zhiWu;
	}
	public void setZhiWu(String zhiWu) {
		this.zhiWu = zhiWu;
	}
	public String getShenPi() {
		return shenPi;
	}
	public void setShenPi(String shenPi) {
		this.shenPi = shenPi;
	}
	public String getFenPeiQuDao() {
		return fenPeiQuDao;
	}
	public void setFenPeiQuDao(String fenPeiQuDao) {
		this.fenPeiQuDao = fenPeiQuDao;
	}
	public String getZfrybh() {
		return zfrybh;
	}
	public void setZfrybh(String zfrybh) {
		this.zfrybh = zfrybh;
	}

	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getXbbh() {
		return xbbh;
	}
	public void setXbbh(String xbbh) {
		this.xbbh = xbbh;
	}

	public String getSfzh() {
		return sfzh;
	}
	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getCsrq() {
		return csrq;
	}
	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}

	public String getMzbh() {
		return mzbh;
	}
	public void setMzbh(String mzbh) {
		this.mzbh = mzbh;
	}

	public String getXl() {
		return xl;
	}
	public void setXl(String xl) {
		this.xl = xl;
	}

	public String getCjgzrq() {
		return cjgzrq;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public void setCjgzrq(String cjgzrq) {
		this.cjgzrq = cjgzrq;
	}

	public String getCszfrq() {
		return cszfrq;
	}
	public void setCszfrq(String cszfrq) {
		this.cszfrq = cszfrq;
	}

	public String getGwbh() {
		return gwbh;
	}
	public void setGwbh(String gwbh) {
		this.gwbh = gwbh;
	}

	public String getZwbh() {
		return zwbh;
	}
	public void setZwbh(String zwbh) {
		this.zwbh = zwbh;
	}

	public String getZfmlbh() {
		return zfmlbh;
	}
	public void setZfmlbh(String zfmlbh) {
		this.zfmlbh = zfmlbh;
	}

	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getZfzh() {
		return zfzh;
	}
	public void setZfzh(String zfzh) {
		this.zfzh = zfzh;
	}

	public String getFzrq() {
		return fzrq;
	}
	public void setFzrq(String fzrq) {
		this.fzrq = fzrq;
	}

	public String getDjr() {
		return djr;
	}
	public void setDjr(String djr) {
		this.djr = djr;
	}

	public String getDjrq() {
		return djrq;
	}
	public void setDjrq(String djrq) {
		this.djrq = djrq;
	}

	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRypic() {
		return rypic;
	}
	public void setRypic(String rypic) {
		this.rypic = rypic;
	}

	public String getFzzt() {
		return fzzt;
	}
	public void setFzzt(String fzzt) {
		this.fzzt = fzzt;
	}

	public String getDeptsp() {
		return deptsp;
	}
	public void setDeptsp(String deptsp) {
		this.deptsp = deptsp;
	}

	public String getZfqy() {
		return zfqy;
	}
	public void setZfqy(String zfqy) {
		this.zfqy = zfqy;
	}

	public String getByxx() {
		return byxx;
	}
	public void setByxx(String byxx) {
		this.byxx = byxx;
	}

	public String getByzy() {
		return byzy;
	}
	public void setByzy(String byzy) {
		this.byzy = byzy;
	}

	public String getByzsbh() {
		return byzsbh;
	}
	public void setByzsbh(String byzsbh) {
		this.byzsbh = byzsbh;
	}

	public String getFpqd() {
		return fpqd;
	}
	public void setFpqd(String fpqd) {
		this.fpqd = fpqd;
	}

	public String getSnzfzh() {
		return snzfzh;
	}
	public void setSnzfzh(String snzfzh) {
		this.snzfzh = snzfzh;
	}

	public String getXybjzfzh() {
		return xybjzfzh;
	}
	public void setXybjzfzh(String xybjzfzh) {
		this.xybjzfzh = xybjzfzh;
	}

	public String getZjzt() {
		return zjzt;
	}
	public void setZjzt(String zjzt) {
		this.zjzt = zjzt;
	}

	public String getGwzsbh() {
		return gwzsbh;
	}
	public void setGwzsbh(String gwzsbh) {
		this.gwzsbh = gwzsbh;
	}

	public String getDawj() {
		return dawj;
	}
	public void setDawj(String dawj) {
		this.dawj = dawj;
	}

	public String getScbh() {
		return scbh;
	}
	public void setScbh(String scbh) {
		this.scbh = scbh;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getKeChengId() {
		return keChengId;
	}
	public void setKeChengId(String keChengId) {
		this.keChengId = keChengId;
	}
	public String[] getZfrybhArray() {
		return zfrybhArray;
	}
	public void setZfrybhArray(String[] zfrybhArray) {
		this.zfrybhArray = zfrybhArray;
	}
	public String getXingZhengQuHuaMingCheng() {
		return xingZhengQuHuaMingCheng;
	}
	public void setXingZhengQuHuaMingCheng(String xingZhengQuHuaMingCheng) {
		this.xingZhengQuHuaMingCheng = xingZhengQuHuaMingCheng;
	}
	public String getXingZhengQuHuaBianHao() {
		return xingZhengQuHuaBianHao;
	}
	public void setXingZhengQuHuaBianHao(String xingZhengQuHuaBianHao) {
		this.xingZhengQuHuaBianHao = xingZhengQuHuaBianHao;
	}
	public String getXingZhengQuHuaId() {
		return xingZhengQuHuaId;
	}
	public void setXingZhengQuHuaId(String xingZhengQuHuaId) {
		this.xingZhengQuHuaId = xingZhengQuHuaId;
	}
	public String getForceOrg() {
		return forceOrg;
	}
	public void setForceOrg(String forceOrg) {
		this.forceOrg = forceOrg;
	}
	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	public String getZhiFaJiGuan() {
		return zhiFaJiGuan;
	}
	public void setZhiFaJiGuan(String zhiFaJiGuan) {
		this.zhiFaJiGuan = zhiFaJiGuan;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIsHaiShi() {
		return isHaiShi;
	}
	public void setIsHaiShi(String isHaiShi) {
		this.isHaiShi = isHaiShi;
	}
	/**
	 * @return chaXunFanWei
	 */
	public String getChaXunFanWei() {
		return chaXunFanWei;
	}
	/**
	 * @param chaXunFanWei 要设置的 chaXunFanWei
	 */
	public void setChaXunFanWei(String chaXunFanWei) {
		this.chaXunFanWei = chaXunFanWei;
	}
	/**
	 * @return isWeiTouDanWei
	 */
	public String getIsWeiTouDanWei() {
		return isWeiTouDanWei;
	}
	/**
	 * @param isWeiTouDanWei 要设置的 isWeiTouDanWei
	 */
	public void setIsWeiTouDanWei(String isWeiTouDanWei) {
		this.isWeiTouDanWei = isWeiTouDanWei;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFszt() {
		return fszt;
	}
	public void setFszt(String fszt) {
		this.fszt = fszt;
	}
	public String getPicSend() {
		return picSend;
	}
	public void setPicSend(String picSend) {
		this.picSend = picSend;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
}
