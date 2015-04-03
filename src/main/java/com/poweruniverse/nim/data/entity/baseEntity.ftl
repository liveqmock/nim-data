package ${packageName}.base;
import java.io.Serializable;
import java.util.List;
import com.poweruniverse.nim.data.entity.Version;
import com.poweruniverse.nim.data.entity.sys.base.EntityI;
/*
* 实体类：${stl.shiTiLeiMC}
*/
@Version("${stl.shiTiLeiBB}")
public abstract class Base${className}  implements Serializable,Comparable<Object> ${entityInterface} {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	// constructors
	public Base${className} () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public Base${className} (java.lang.Integer id) {
		this.set${stl.zhuJianLie?cap_first}(id);
		initialize();
	}

	protected abstract void initialize ();
	
	<#list stl.zds as zd>
		<#if zd.ziDuanDH =  stl.zhuJianLie>
	// 主键：${zd.ziDuanDH}
	private java.lang.Integer ${zd.ziDuanDH} = null;
	public java.lang.Integer get${zd.ziDuanDH?cap_first}(){return this.${zd.ziDuanDH} ;}
	public void set${zd.ziDuanDH?cap_first}(java.lang.Integer ${zd.ziDuanDH}){this.${zd.ziDuanDH} = ${zd.ziDuanDH};}

		<#elseif zd.ziDuanLX.ziDuanLXDH = 'set' || zd.ziDuanLX.ziDuanLXDH = 'fileset'>
	// 集合：${zd.ziDuanBT} （${zd.ziDuanDH}）
	<#if zd.guanLianSTL?? && zd.guanLianFLZD??>  
	private java.util.Set<${zd.guanLianSTL.shiTiLeiClassName!'null'}> ${zd.ziDuanDH} = new java.util.TreeSet<${zd.guanLianSTL.shiTiLeiClassName!'null'}>();
	public java.util.Set<${zd.guanLianSTL.shiTiLeiClassName!'null'}> get${zd.ziDuanDH?cap_first}(){return this.${zd.ziDuanDH} ;}
	public void set${zd.ziDuanDH?cap_first}(java.util.Set<${zd.guanLianSTL.shiTiLeiClassName!'null'}> ${zd.ziDuanDH}){this.${zd.ziDuanDH} = ${zd.ziDuanDH};}
	public void addTo${zd.ziDuanDH}(Object parent,Object detail){
		${stl.shiTiLeiClassName} mainObj = (${stl.shiTiLeiClassName})parent;
		${zd.guanLianSTL.shiTiLeiClassName!'null'} subObj = (${zd.guanLianSTL.shiTiLeiClassName!'null'})detail;
		subObj.set${zd.guanLianFLZD.ziDuanDH?cap_first}(mainObj);
		mainObj.get${zd.ziDuanDH?cap_first}().add(subObj);
	}
	public void removeFrom${zd.ziDuanDH}(Object parent,Object detail){
		${stl.shiTiLeiClassName} mainObj = (${stl.shiTiLeiClassName})parent;
		${zd.guanLianSTL.shiTiLeiClassName!'null'} subObj = (${zd.guanLianSTL.shiTiLeiClassName!'null'})detail;
		subObj.set${zd.guanLianFLZD.ziDuanDH?cap_first}(null);
		mainObj.get${zd.ziDuanDH?cap_first}().remove(subObj);
	}
	public Object get${zd.ziDuanDH}ById(Object id){
		java.util.Iterator<${zd.guanLianSTL.shiTiLeiClassName!'null'}> ds = this.get${zd.ziDuanDH?cap_first}().iterator();
		${zd.guanLianSTL.shiTiLeiClassName!'null'} d = null;
		while(ds.hasNext()){
			d = ds.next();
			if(d.get${zd.guanLianSTL.zhuJianLie?cap_first}()!=null && d.get${zd.guanLianSTL.zhuJianLie?cap_first}().equals(id)){
				return d;
			}
		}
		return null;
	}
	public ${zd.guanLianSTL.shiTiLeiClassName!'null'} new${zd.ziDuanDH}ByParent(${packageName}.${className} parent) throws Exception{
		${zd.guanLianSTL.shiTiLeiClassName!'null'} subObj = new ${zd.guanLianSTL.shiTiLeiClassName!'null'}();
		//
		subObj.set${zd.guanLianFLZD.ziDuanDH?cap_first}(parent);
		//
		return subObj;
	}
	
	<#else>
		guanLianSTL为空或guanLianFLZD为空！！！请修改字段定义后 重新生成
	</#if>  
		<#elseif zd.ziDuanLX.ziDuanLXDH = 'object' || zd.ziDuanLX.ziDuanLXDH = 'file'>
	// 对象：${zd.ziDuanBT} （${zd.ziDuanDH}）
	<#if zd.guanLianSTL??> 
	<#if zd.guanLianSTL.shiTiLeiClassName = 'com.poweruniverse.oim.server.entity.sys.BiZhong'>
	private ${zd.guanLianSTL.shiTiLeiClassName!'null'} ${zd.ziDuanDH} = new com.poweruniverse.oim.server.entity.sys.BiZhong(1,"人民币",1d);
	<#else>
	private ${zd.guanLianSTL.shiTiLeiClassName!'null'} ${zd.ziDuanDH};
	</#if>  
	public ${zd.guanLianSTL.shiTiLeiClassName!'null'} get${zd.ziDuanDH?cap_first}(){return this.${zd.ziDuanDH} ;}
	public void set${zd.ziDuanDH?cap_first}(${zd.guanLianSTL.shiTiLeiClassName!'null'} ${zd.ziDuanDH}){this.${zd.ziDuanDH} = ${zd.ziDuanDH};}
	<#else>
		guanLianSTL为空！！！请修改字段定义后 重新生成
	</#if>  

		<#else>
			
			<#if zd.ziDuanLX.ziDuanLXDH = 'string'>
				<#assign columnType = "java.lang.String"/>
				<#assign defaultValue = "null"/>
			<#elseif zd.ziDuanLX.ziDuanLXDH = 'text'>
				<#assign columnType = "java.lang.String"/>
				<#assign defaultValue = "null"/>
			<#elseif zd.ziDuanLX.ziDuanLXDH = 'dictionary'>
				<#assign columnType = "java.lang.String"/>
				<#assign defaultValue = "null"/>
			<#elseif zd.ziDuanLX.ziDuanLXDH = 'date'>
				<#assign columnType = "java.util.Date"/>
				<#assign defaultValue = "null"/>
			<#elseif zd.ziDuanLX.ziDuanLXDH = 'month'>
				<#assign columnType = "java.util.Date"/>
				<#assign defaultValue = "null"/>
			<#elseif zd.ziDuanLX.ziDuanLXDH = 'int'>
				<#assign columnType = "java.lang.Integer"/>
				<#assign defaultValue = "new java.lang.Integer(0)"/>
			<#elseif zd.ziDuanLX.ziDuanLXDH = 'double'>
				<#assign columnType = "java.lang.Double"/>
				<#assign defaultValue = "new java.lang.Double(0)"/>
			<#elseif zd.ziDuanLX.ziDuanLXDH = 'tenthousand'>
				<#assign columnType = "java.lang.Double"/>
				<#assign defaultValue = "new java.lang.Double(0)"/>
			<#elseif zd.ziDuanLX.ziDuanLXDH = 'boolean'>
				<#assign columnType = "java.lang.Boolean"/>
				<#assign defaultValue = "new java.lang.Boolean(false)"/>
			</#if>
			<#if zd.ziDuanDH = 'huiLv'>
			<#assign defaultValue = "1d"/>
			</#if>
	// 属性：${zd.ziDuanBT} （${zd.ziDuanDH}）
	private ${columnType} ${zd.ziDuanDH} = ${defaultValue};
	public ${columnType} get${zd.ziDuanDH?cap_first}(){return this.${zd.ziDuanDH} ;}
	public void set${zd.ziDuanDH?cap_first}(${columnType} ${zd.ziDuanDH}){this.${zd.ziDuanDH} = ${zd.ziDuanDH};}
	
		</#if>
	</#list>
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof ${packageName}.${className})) return false;
		else {
			${packageName}.${className} entity = (${packageName}.${className}) obj;
			if (null == this.get${stl.zhuJianLie?cap_first}() || null == entity.get${stl.zhuJianLie?cap_first}()) return false;
			else return (this.get${stl.zhuJianLie?cap_first}().equals(entity.get${stl.zhuJianLie?cap_first}()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.get${stl.zhuJianLie?cap_first}()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.get${stl.zhuJianLie?cap_first}().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
	public String toString() {
		return this.${stl.xianShiLie}+"";
	}

	public Integer pkValue() {
		return this.${stl.zhuJianLie};
	}

	public String pkName() {
		return "${stl.zhuJianLie}";
	}

	public void pkNull() {
		this.${stl.zhuJianLie} = null;;
	}
	
	public int compareTo(Object o) {
		${packageName}.${className} obj = (${packageName}.${className})o;
		if(this.get${stl.zhuJianLie?cap_first}()==null){
			return 1;
		}
		return this.get${stl.zhuJianLie?cap_first}().compareTo(obj.get${stl.zhuJianLie?cap_first}());
	}
	
	public ${packageName}.${className} clone(){
		${packageName}.${className} ${className?uncap_first} = new ${packageName}.${className}();
		
		<#list stl.zds as zd>
		<#if zd.guanLianSTL?? && zd.guanLianFLZD??>
		for(${zd.guanLianSTL.shiTiLeiClassName!'null'} subObj:this.get${zd.ziDuanDH?cap_first}()){
			${className?uncap_first}.addTo${zd.ziDuanDH}(${className?uncap_first}, subObj.clone());
		}
		<#elseif zd.ziDuanDH != stl.zhuJianLie>
		${className?uncap_first}.set${zd.ziDuanDH?cap_first}(${zd.ziDuanDH});
		</#if>
		</#list>
		
		return ${className?uncap_first};
	}
	
	
	<#if stl.shiFouYWB>
	public void setRelaShanChuZT(boolean sczt){
		this.setShanChuZT(sczt);
		<#list stl.zds as zd>
		<#if zd.ziDuanLX.ziDuanLXDH = 'set' && zd.guanLianSTL.shiFouYWB>
		//级联修改${zd.ziDuanBT}中子对象的状态
		for(${zd.guanLianSTL.shiTiLeiClassName} subObj:this.get${zd.ziDuanDH?cap_first}()){
			subObj.setRelaShanChuZT(sczt);
		}
		</#if>
		</#list>
	}
	</#if>
	
	
	
	
}