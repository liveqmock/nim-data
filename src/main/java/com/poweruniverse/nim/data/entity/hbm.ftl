<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-mapping PUBLIC 
	"-//Hibernate/Hibernate Mapping DTD 3.0//EN"
	"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<!-- version:${stl.shiTiLeiBB} -->
<hibernate-mapping auto-import="false">  
    <class name="${stl.shiTiLeiClassName}" table="${stl.biaoMing}" <#if stl.shiFouYWB >where="shanChuZT = 0"</#if>>
    	<!--主键-->
        <id name="${stl.zhuJianLie}" type="integer">
		<#list stl.zds as zd>
		<#if zd.ziDuanDH =  stl.zhuJianLie>
	        <column name="${zd.lieMing}" precision="12" scale="0" />
	        <generator class="increment"></generator>
        </#if>
        </#list>
        </id>
        
		<#list stl.zds as zd>
		<#if zd.ziDuanDH !=  stl.zhuJianLie>
		<!--${zd.ziDuanBT}-->
			<#if zd.ziDuanLX.ziDuanLXDH = 'set' || zd.ziDuanLX.ziDuanLXDH = 'fileset'>
		<#if zd.guanLianSTL?? && zd.guanLianFLZD??> 
        <set name="${zd.ziDuanDH}" inverse="true"  lazy="true" cascade="all-delete-orphan" order-by="${zd.guanLianSTL.paiXuLie}" <#if zd.guanLianSTL.shiFouYWB >where="shanChuZT = 0"</#if>>
        	<key>
        		<column name="${(zd.guanLianFLZD.lieMing)!'XXX'}" precision="12" scale="0" not-null="true" />
        	</key>
        	<one-to-many class="${zd.guanLianSTL.shiTiLeiClassName}" />
        </set>
        <#else>
			guanLianSTL为空或guanLianFLZD为空！！！请修改字段定义后 重新生成
		</#if> 
			<#elseif zd.ziDuanLX.ziDuanLXDH = 'object' || zd.ziDuanLX.ziDuanLXDH = 'file'>
		<#if zd.guanLianSTL?? > 
        <many-to-one name="${zd.ziDuanDH}" class="${zd.guanLianSTL.shiTiLeiClassName}" fetch="select">
            <column name="${zd.lieMing}" precision="12" scale="0" not-null="false" />
        </many-to-one>
        <#else>
		guanLianSTL为空！！！请修改字段定义后 重新生成
		</#if>  
        
			<#else>
				<#if zd.ziDuanLX.ziDuanLXDH = 'string'>
					<#assign columnType = "java.lang.String"/>
				<#elseif zd.ziDuanLX.ziDuanLXDH = 'text'>
					<#assign columnType = "java.lang.String"/>
				<#elseif zd.ziDuanLX.ziDuanLXDH = 'dictionary'>
					<#assign columnType = "java.lang.String"/>
				<#elseif zd.ziDuanLX.ziDuanLXDH = 'date'>
					<#assign columnType = "java.util.Date"/>
				<#elseif zd.ziDuanLX.ziDuanLXDH = 'month'>
					<#assign columnType = "java.util.Date"/>
				<#elseif zd.ziDuanLX.ziDuanLXDH = 'int'>
					<#assign columnType = "java.lang.Integer"/>
				<#elseif zd.ziDuanLX.ziDuanLXDH = 'double'>
					<#assign columnType = "java.lang.Double"/>
				<#elseif zd.ziDuanLX.ziDuanLXDH = 'tenthousand'>
					<#assign columnType = "java.lang.Double"/>
				<#elseif zd.ziDuanLX.ziDuanLXDH = 'boolean'>
					<#assign columnType = "java.lang.Boolean"/>
				<#else>
					未定义处理方式的字段类型：${zd.ziDuanLX.ziDuanLXDH}
				</#if>
        <property name="${zd.ziDuanDH}" type="${columnType!}" lazy="false">
        	<column name="${zd.lieMing}" precision="${zd.ziDuanCD?c}" scale="${zd.ziDuanJD!0}" not-null="false" />
        </property>
			</#if>
        </#if>
        </#list>
     </class>
</hibernate-mapping>
