package com.poweruniverse.nim.data.pageParser;

import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Element;

public class TabpageElParser {

	/**
	 * 集合类型数据源的解析
	 */
	public static String parseTabpageEl(Element tabEl,JSONObject params,Map<String, Object> root,Integer yongHuDM,boolean isIndependent, String pageName) throws Exception{
		String dataScriptContent = "";
		if("tabSelector".equals(tabEl.attributeValue("component"))){
			//检查 renderto参数
			String name = tabEl.attributeValue("name");
			String renderto = tabEl.attributeValue("renderto");
			String onRender = tabEl.attributeValue("onRender");
			String onSelect = tabEl.attributeValue("onSelect");
			String activeClass = tabEl.attributeValue("activeClass");
//			String hoverClass = tabEl.attributeValue("hoverClass");
			if(renderto!=null && renderto.length()>0 ){
				dataScriptContent+="\n//标签页"+name+"的显示\n";
				dataScriptContent+="$('"+renderto+" li').each(function(index,optionEl){\n" +
						"	$(optionEl).removeAttr('onclick').click(function(){\n" +
						"		var lastIndex = -1;\n" +
						"		$(optionEl).siblings().each(function(aIndex,aEl){\n" +
						"			if($(aEl).hasClass('"+activeClass+"')){\n" +
						"				lastIndex = aIndex;\n" +
						"				$(aEl).removeClass('"+activeClass+"');\n\n" +
						"				var divSelector = $(aEl).attr('href');\n" +
						"				$(divSelector).css('display','none') ;\n"+
						"			}\n"+
						"		});"+
						"		$(optionEl).addClass('"+activeClass+"');\n\n" +
						"		var divSelector = $(optionEl).attr('href');\n"+
						"		$(divSelector).css('display','block');\n\n " +
						(onSelect!=null && onSelect.length() >0?"		"+onSelect+".apply(optionEl,[index,lastIndex]);\n":"\n") +
						"	});\n" +
						"});\n" +
						(onRender!=null && onRender.length() >0?""+onRender+".apply(this,['"+name+"','"+renderto+"']);\n":"\n");
			}
		}else if("tabGenerator".equals(tabEl.attributeValue("component"))){
			
		}

		//注册
//		if(isIndependent){
//			treeScriptContent += "LUI.Page.instance.register('tree',"+treeVarName+");\n";
//		}else{
//			treeScriptContent += "LUI.Subpage.getInstance('"+pageName+"').register('tree',"+treeVarName+");\n";
//		}

		return dataScriptContent;
	}
	

}
