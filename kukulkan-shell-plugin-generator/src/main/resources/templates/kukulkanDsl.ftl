<#list domainModelGroup as dmg>
	<#list dmg.entities as entity>
entity ${entity.name} (${entity.tableName}) <#if entity.properties?has_content>{
	<#list entity.properties as property>
		<#assign constraint = property.constraint>
	<#if !property.name?ends_with('ContentType')>
	<#if entity.displayField == property>-> </#if>${property.name} : <#if property.type == "boolean">Boolean<#elseif property.type == "byte[]">Blob<#else>${property.type}</#if><#if !constraint.nullable> required</#if><#if constraint.min?has_content> min(${constraint.min})</#if><#if constraint.max?has_content && (property.literal || property.number || property.blob)> max(${constraint.max})</#if><#if property?index < entity.properties?size - 1 || entity.ownerAssociations?has_content>,</#if>
	</#if>
	</#list>
	<#list entity.ownerAssociations as association>
	<#if association.type == "ONE_TO_ONE">
		<#assign associationType = "OneToOne">
	<#elseif association.type = "ONE_TO_MANY">
		<#assign associationType = "OneToMany">
	<#elseif association.type = "MANY_TO_ONE">
		<#assign associationType = "ManyToOne">
	<#elseif association.type = "MANY_TO_MANY">
		<#assign associationType = "ManyToMany">
	</#if>
	${associationType} <#if association.bidirectional>(${association.toSourcePropertyName}) </#if>${association.toTargetPropertyName} : ${association.target.name}<#if association?index < entity.ownerAssociations?size - 1>,</#if>
	</#list>
}</#if>
	</#list>
</#list>