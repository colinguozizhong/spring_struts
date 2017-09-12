package com.ustcsoft.framework.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONTreeNodeUtil implements Serializable, Cloneable {
  protected static final long serialVersionUID = 1L;
  protected String id;
  protected String text;
  protected String icon;
  protected String iconCls;
  protected boolean leaf;
  protected String href;
  protected String hrefTarget;
  protected Object checked = null;
  protected String menuUrl;
  protected String isPop;
  protected String busiCode;
  protected String purview;
  protected String isInit;
  protected boolean disabled;
  protected String type = "area";

  protected String typex = "";
  protected String secId;
	protected ArrayList<JSONTreeNodeUtil> children ;

	@Override
	public JSONTreeNodeUtil clone() throws CloneNotSupportedException {
		JSONTreeNodeUtil jsonTreeNodeUtil = (JSONTreeNodeUtil) super.clone();
		if (this.children != null && this.children.size() > 0) {
			ArrayList<JSONTreeNodeUtil> copyChildren = new ArrayList<JSONTreeNodeUtil>();
			copyChildren(this.children, copyChildren);
			jsonTreeNodeUtil.setChildren(copyChildren);
		}
		return jsonTreeNodeUtil;
	}

	private void copyChildren(List<JSONTreeNodeUtil> src,
			List<JSONTreeNodeUtil> desc) throws CloneNotSupportedException {
		for (int i = 0; i < src.size(); i++) {
			JSONTreeNodeUtil node = src.get(i);
			desc.add(node.clone());
		}
	}


	public ArrayList<JSONTreeNodeUtil> getChildren() {
		return children;
	}
  public static String getJsonNodeString(Map m, String id, String text, boolean leaf)
  {
    JSONTreeNodeUtil treeNode = new JSONTreeNodeUtil();
    treeNode.setId(m.get(id).toString());
    treeNode.setText(m.get(text).toString());
    treeNode.setLeaf(leaf);

    JSONObject JsonObject = JSONObject.fromObject(treeNode);

    return JsonObject.toString();
  }
	public void setChildren(ArrayList<JSONTreeNodeUtil> children) {
		this.children = children;
	}
  public static String getJsonNodeArray(Map m, String[] ids, String[] texts, boolean leaf)
  {
    JSONTreeNodeUtil treeNode = new JSONTreeNodeUtil();
    if ((ids != null) && (ids.length > 0)) {
      StringBuffer idStr = new StringBuffer();
      for (int i = 0; i < ids.length; i++) {
        if (i < ids.length - 1)
          idStr.append(m.get(ids[i]).toString())
            .append(",");
        else {
          idStr.append(m.get(ids[i]).toString());
        }
      }
      treeNode.setId(idStr.toString());
    }
    if ((texts != null) && (texts.length > 0)) {
      StringBuffer textStr = new StringBuffer();
      for (int j = 0; j < texts.length; j++) {
        if (j < texts.length - 1)
          textStr.append("(")
            .append(m.get(texts[j]).toString())
            .append(")");
        else {
          textStr.append(m.get(texts[j]).toString());
        }
      }
      treeNode.setText(textStr.toString());
    }
    treeNode.setLeaf(leaf);

    JSONObject JsonObject = JSONObject.fromObject(treeNode);

    return JsonObject.toString();
  }

  public static String getJsonNodeString(Map m, String id, String text, boolean leaf, String icon)
  {
    JSONTreeNodeUtil treeNode = new JSONTreeNodeUtil();
    treeNode.setId(m.get(id).toString());
    treeNode.setText(m.get(text).toString());
    treeNode.setLeaf(leaf);
    treeNode.setIcon(icon);
    JSONObject JsonObject = JSONObject.fromObject(treeNode);

    return JsonObject.toString();
  }

  public static String getJsonNodeString(List<Map> list, String id, String text, boolean leaf)
  {
    List nodeList = new ArrayList();
    for (int i = 0; i < list.size(); i++)
    {
      JSONTreeNodeUtil treeNode = new JSONTreeNodeUtil();
      treeNode.setId(((Map)list.get(i)).get(id).toString());
      treeNode.setText(((Map)list.get(i)).get(text).toString());
      treeNode.setLeaf(leaf);
      nodeList.add(treeNode);
    }
    JSONArray JsonArray = JSONArray.fromObject(nodeList);

    return JsonArray.toString();
  }

  public static String getJsonNodeString(List<Map> list, String id, String text, boolean leaf, String icon)
  {
    List nodeList = new ArrayList();
    for (int i = 0; i < list.size(); i++)
    {
      JSONTreeNodeUtil treeNode = new JSONTreeNodeUtil();
      treeNode.setId(((Map)list.get(i)).get(id).toString());
      treeNode.setText(((Map)list.get(i)).get(text).toString());
      treeNode.setLeaf(leaf);
      treeNode.setIcon(icon);
      nodeList.add(treeNode);
    }
    JSONArray JsonArray = JSONArray.fromObject(nodeList);

    return JsonArray.toString();
  }

  public static String getJsonNodeString(List<Map> list, String id, String text)
  {
    List nodeList = new ArrayList();
    for (int i = 0; i < list.size(); i++)
    {
      JSONTreeNodeUtil treeNode = new JSONTreeNodeUtil();
      treeNode.setId(((Map)list.get(i)).get(id).toString());
      treeNode.setText(((Map)list.get(i)).get(text).toString());
      nodeList.add(treeNode);
    }
    JSONArray JsonArray = JSONArray.fromObject(nodeList);

    return JsonArray.toString();
  }

  public static String getJsonNodeString(boolean checked, List<Map> list, String id, String text)
  {
    List nodeList = new ArrayList();
    for (int i = 0; i < list.size(); i++)
    {
      JSONTreeNodeUtil treeNode = new JSONTreeNodeUtil();
      treeNode.setId(((Map)list.get(i)).get(id).toString());
      treeNode.setText(((Map)list.get(i)).get(text).toString());
      treeNode.setChecked(Boolean.valueOf(checked));
      nodeList.add(treeNode);
    }
    JSONArray JsonArray = JSONArray.fromObject(nodeList);

    return JsonArray.toString();
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

public String getText()
  {
    return this.text;
  }

  public void setText(String text)
  {
    this.text = text;
  }

  public String getIcon()
  {
    return this.icon;
  }

  public void setIcon(String icon)
  {
    this.icon = icon;
  }

  public String getIconCls()
  {
    return this.iconCls;
  }

  public void setIconCls(String iconCls)
  {
    this.iconCls = iconCls;
  }

  public boolean isLeaf()
  {
    return this.leaf;
  }

  public void setLeaf(boolean leaf)
  {
    this.leaf = leaf;
  }

  public String getHref()
  {
    return this.href;
  }

  public void setHref(String href)
  {
    this.href = href;
  }

  public String getHrefTarget()
  {
    return this.hrefTarget;
  }

  public void setHrefTarget(String hrefTarget)
  {
    this.hrefTarget = hrefTarget;
  }

  public Object getChecked()
  {
    return this.checked;
  }

  public void setChecked(Object checked)
  {
    this.checked = checked;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getTypex()
  {
    return this.typex;
  }

  public void setTypex(String typex)
  {
    this.typex = typex;
  }

  public String getMenuUrl()
  {
    return this.menuUrl;
  }

  public void setMenuUrl(String menuUrl)
  {
    this.menuUrl = menuUrl;
  }

  public String getBusiCode()
  {
    return this.busiCode;
  }

  public void setBusiCode(String busiCode)
  {
    this.busiCode = busiCode;
  }

  public boolean isDisabled()
  {
    return this.disabled;
  }

  public void setDisabled(boolean disabled)
  {
    this.disabled = disabled;
  }

  public String getPurview()
  {
    return this.purview;
  }

  public void setPurview(String purview)
  {
    this.purview = purview;
  }

  public String getIsPop()
  {
    return this.isPop;
  }

  public void setIsPop(String isPop)
  {
    this.isPop = isPop;
  }

  public String getIsInit()
  {
    return this.isInit;
  }

  public void setIsInit(String isInit)
  {
    this.isInit = isInit;
  }

  public String getSecId() {
    return this.secId;
  }

  public void setSecId(String secId) {
    this.secId = secId;
  }
}