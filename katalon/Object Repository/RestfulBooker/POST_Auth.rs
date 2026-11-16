<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description>Login admin; respons berisi token.</description>
   <name>POST_Auth</name>
   <tag></tag>
   <elementGuidId>e31d8b91-9c06-51f3-be59-6ab8ed11358b</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>false</autoUpdateContent>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\n  \&quot;username\&quot;: \&quot;${username}\&quot;,\n  \&quot;password\&quot;: \&quot;${password}\&quot;\n}&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>f4b9c48e-5593-5996-a4f1-bcdb1f1ecbef</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Accept</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>0b5d47c9-c56a-53d5-82bd-d9337b008c12</webElementGuid>
   </httpHeaderProperties>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>${baseUrl}/auth</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>-1</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <variables>
      <defaultValue>GlobalVariable.RB_URL</defaultValue>
      <description>Alamat Restful-Booker (dari profil)</description>
      <id>8b96453d-0986-5086-add0-3fffae54f18c</id>
      <masked>false</masked>
      <name>baseUrl</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.RB_USER</defaultValue>
      <description>Username</description>
      <id>0a11609a-e759-566a-8a52-c7b808c76767</id>
      <masked>false</masked>
      <name>username</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.RB_PASSWORD</defaultValue>
      <description>Kata sandi</description>
      <id>1f5e6c8e-b1a4-55de-b554-d6608e3da05d</id>
      <masked>false</masked>
      <name>password</name>
   </variables>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
