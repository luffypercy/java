<!-- ${codeMapModel.desc}远程服务 -->
<bean name="/${codeMapModel.lmodelName}Service"
	class="org.springframework.remoting.caucho.HessianServiceExporter">
	<property name="service" ref="${codeMapModel.lmodelName}Service" />
	<property name="serviceInterface" value="${codeMapModel.serviceNameSpace}.I${codeMapModel.modelName}Service" />
</bean>
